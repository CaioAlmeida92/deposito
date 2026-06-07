package laranja.comprovante.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import laranja.comprovante.dto.DepositoEvent;
import laranja.comprovante.dto.DepositoRequest;
import laranja.comprovante.dto.ValidationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class DepositoService {

    @Value("${app.aws.lambda-name}")
    private String nomeLambda;

    @Value("${app.aws.sqs-queue-url}")
    private String sqsQueueUrl;

    private final LambdaClient lambdaClient;
    private final SqsTemplate sqsTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public DepositoService(LambdaClient lambdaClient, SqsTemplate sqsTemplate){
        this.lambdaClient = lambdaClient;
        this.sqsTemplate = sqsTemplate;
    }

    public void processaDeposito(DepositoRequest depositoRequest){
        if (depositoRequest == null){
            throw new NullPointerException("O objeto deposito está vazio.");
        }

        ValidationResponse validacao;
        UUID idDeposito = UUID.randomUUID();
        LocalDate dataDeposito = LocalDate.now();

        try {
            String jsonPayload = mapper.writeValueAsString(depositoRequest);

            InvokeRequest invokeRequest = InvokeRequest.builder()
                    .functionName(this.nomeLambda)
                    .payload(SdkBytes.fromUtf8String(jsonPayload))
                    .build();

            InvokeResponse invokeResponse = this.lambdaClient.invoke(invokeRequest);

            String responseJson = invokeResponse.payload().asUtf8String();
            JsonNode rootNode = mapper.readTree(responseJson);
            String bodyInternoJson = rootNode.get("body").asText();

            System.out.println("Resposta da lambda de validação: " + responseJson);

            validacao = mapper.readValue(bodyInternoJson, ValidationResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Falha técnica ao integrar com a Lambda de validação");
        }

        if (!validacao.isValid()){
            throw new IllegalArgumentException("Depósito rejeitado pela validação: " + validacao.getMensagem());
        }

        try {
            System.out.println("Transação validada com sucesso! Avançando para a fila SQS...");
            DepositoEvent depositoEvent = new DepositoEvent(idDeposito, dataDeposito, depositoRequest);

            this.sqsTemplate.send(to -> to
                    .queue(sqsQueueUrl)
                    .payload(depositoEvent)
            );
            System.out.println("Mensagem postada com sucesso no SQS para a transação: " + idDeposito);

        } catch (Exception e){
            throw new RuntimeException("Falha ao postar mensagem na fila SQS", e);
        }
    }


}

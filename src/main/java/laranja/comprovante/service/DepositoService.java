package laranja.comprovante.service;

import laranja.comprovante.dto.DepositoRequest;
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

    private final LambdaClient lambdaClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public DepositoService(LambdaClient lambdaClient){
        this.lambdaClient = lambdaClient;
    }

    public void processaDeposito(DepositoRequest depositoRequest){
        if (depositoRequest == null){
            throw new NullPointerException("O objeto deposito está vazio.");
        }

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
            System.out.println("Resposta da lambda de validação: " + responseJson);

        } catch (Exception e){
            throw new RuntimeException("Falha ao integrar com a Lambda de validação", e);
        }
    }


}

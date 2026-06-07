package laranja.comprovante.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.awspring.cloud.s3.S3Template;
import io.awspring.cloud.sqs.annotation.SqsListener;
import laranja.comprovante.dto.DepositoEvent;
import laranja.comprovante.entity.Deposito;
import laranja.comprovante.repository.DepositoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Component
public class DepositoListener {

    private final DepositoRepository depositoRepository;
    private final S3Template s3Template;
    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Value("${app.aws.s3-bucket-name}")
    private String bucketName;

    public DepositoListener(DepositoRepository depositoRepository, S3Template s3Template){
        this.depositoRepository = depositoRepository;
        this.s3Template = s3Template;
    }

    @SqsListener("${app.aws.sqs-queue-url}")
    public void recebeMensagem(DepositoEvent event){
        System.out.println("=== WORKER PROCESSANDO EM SEGUNDO PLANO ===");
        System.out.println("Mensagem recebida do SQS para a transação: " + event.idDeposito());
        System.out.println("Dados do depósito - Banco: " + event.banco() + " | Agência: " + event.agencia() + " | Conta: " + event.conta() + " | Valor: " + event.valor());

        try {
            Deposito deposito = new Deposito(
                    event.idDeposito(),
                    event.agencia(),
                    event.conta(),
                    event.banco(),
                    event.valor(),
                    event.dataDeposito(),
                    "PROCESSADA"
            );

            this.depositoRepository.save(deposito);
            System.out.println("Transação " + event.idDeposito() + " salva no banco com sucesso");

        } catch (Exception e){
            System.err.println("ERRO NO WORKER: Falha ao salvar transação no banco de dados.");
            e.printStackTrace();
            throw e;
        }

        try{
            String comprovanteArquivo = objectMapper.writeValueAsString(event);
            String nomeArquivo = "Comprovante-" + event.idDeposito() + ".json";

            ByteArrayInputStream inputStream = new ByteArrayInputStream(comprovanteArquivo.getBytes(StandardCharsets.UTF_8));
            s3Template.upload(bucketName, nomeArquivo, inputStream);

            System.out.println("Comprovante enviado para o S3 com sucesso.");

        } catch (JsonProcessingException e){
            System.err.println("Erro ao serializar o evento para Json.");
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println("Erro ao realizar upload do comprovante para o Bucket S3.");
            throw e;
        }

    }

}

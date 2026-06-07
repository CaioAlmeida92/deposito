package laranja.comprovante.worker;

import io.awspring.cloud.sqs.annotation.SqsListener;
import laranja.comprovante.dto.DepositoEvent;
import laranja.comprovante.entity.Deposito;
import laranja.comprovante.repository.DepositoRepository;
import org.springframework.stereotype.Component;

@Component
public class DepositoListener {

    private final DepositoRepository depositoRepository;

    public DepositoListener(DepositoRepository depositoRepository){
        this.depositoRepository = depositoRepository;
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

    }

}

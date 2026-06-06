package laranja.comprovante.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DepositoEvent(
        UUID idDeposito,
        Integer agencia,
        Integer conta,
        LocalDate dataDeposito,
        String banco,
        BigDecimal valor) {

    public DepositoEvent(UUID idDeposito, LocalDate dataDeposito, DepositoRequest request){
        this(idDeposito, request.getAgencia(), request.getConta(), dataDeposito, request.getBanco(), request.getValor());
    }

}

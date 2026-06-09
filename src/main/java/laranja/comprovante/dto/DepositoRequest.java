package laranja.comprovante.dto;

import jakarta.validation.constraints.*;
import laranja.comprovante.enums.Banco;

import java.math.BigDecimal;

public class DepositoRequest {

    @NotNull(message = "A agência é obrigatória.")
    @Min(value = 1000, message = "A agência deve ter exatamente 4 dígitos.")
    @Max(value = 9999, message = "A agência deve ter exatamente 4 dígitos.")
    private Integer agencia;

    @NotNull(message = "A conta é obrigatória.")
    @Min(value = 10000, message = "A conta deve ter exatamente 5 dígitos.")
    @Max(value = 99999, message = "A conta deve ter exatamente 5 dígitos.")
    private Integer conta;

    @NotBlank(message = "O banco é obrigatório")
    @Size(max = 15, message = "O nome do banco deve ter no máximo 15 caracteres.")
    private String banco;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @AssertTrue(message = "O banco informado não é permitido pelo sistema.")
    public boolean isBancoValido(){
        return Banco.contains(this.banco);
    }

    public DepositoRequest(){
    }

    public DepositoRequest(Integer agencia, Integer conta, String banco, BigDecimal valor){
        this.agencia = agencia;
        this.conta = conta;
        this.banco = banco;
        this.valor = valor;
    }

    public Integer getAgencia(){
        return agencia;
    }

    public void setAgencia(Integer agencia){
        this.agencia = agencia;
    }

    public Integer getConta(){
        return conta;
    }

    public void setConta(Integer conta){
        this.conta = conta;
    }

    public String getBanco(){
        return banco;
    }

    public void setBanco(String banco){
        this.banco = banco;
    }

    public BigDecimal getValor(){
        return valor;
    }

    public void setValor(BigDecimal valor){
        this.valor = valor;
    }

}

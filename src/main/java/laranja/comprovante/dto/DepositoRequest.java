package laranja.comprovante.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class DepositoRequest {

    @NotNull(message = "A agência é obrigatória.")
    private Integer agencia;

    @NotNull(message = "A conta é obrigatória.")
    private Integer conta;

    @NotNull(message = "O banco é obrigatório")
    @NotBlank
    @Size(max = 15, message = "O nome do banco deve ter no máximo 15 caracteres.")
    private String banco;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

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





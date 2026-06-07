package laranja.comprovante.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@DynamoDbBean
public class Deposito {

    private UUID idTransacao;
    private Integer agencia;
    private Integer conta;
    private String banco;
    private BigDecimal valor;
    private LocalDate dataDeposito;
    private String status;

    public Deposito(){}

    public Deposito(UUID idTransacao, Integer agencia, Integer conta, String banco, BigDecimal valor, LocalDate dataDeposito, String status){
        this.idTransacao = idTransacao;
        this.agencia = agencia;
        this.conta = conta;
        this.banco = banco;
        this.valor = valor;
        this.dataDeposito = dataDeposito;
        this.status = status;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public UUID getIdTransacao() { return idTransacao; }
    public void setIdTransacao(UUID idTransacao) { this.idTransacao = idTransacao; }

    public Integer getAgencia() { return agencia; }
    public void setAgencia(Integer agencia) { this.agencia = agencia; }

    public Integer getConta() { return conta; }
    public void setConta(Integer conta) { this.conta = conta; }

    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDate getDataDeposito() { return dataDeposito; }
    public void setDataDeposito(LocalDate dataDeposito) { this.dataDeposito = dataDeposito; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}


package laranja.comprovante.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Set;

public class DepositoRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void deveFalharQuandoAgenciaTiverMenosDe4Digitos(){
        DepositoRequest depositoRequest = new DepositoRequest(123, 32551, "Laranja", new BigDecimal("500.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("A agência deve ter exatamente 4 dígitos.", mensagemErro);
    }

    @Test
    public void deveFalharQuandoAgenciaTiverMaisDe4Digitos(){
        DepositoRequest depositoRequest = new DepositoRequest(12345, 32551, "Laranja", new BigDecimal("500.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("A agência deve ter exatamente 4 dígitos.", mensagemErro);
    }

    @Test
    public void deveFalharQuandoAgenciaEstiverNulo(){
        DepositoRequest depositoRequest = new DepositoRequest(null, 32551, "Laranja", new BigDecimal("500.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("A agência é obrigatória.", mensagemErro);
    }

    @Test
    public void deveFalharQuandoContaTiverMaisDe5Digitos(){
        DepositoRequest depositoRequest = new DepositoRequest(1234, 325512, "Laranja", new BigDecimal("500.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("A conta deve ter exatamente 5 dígitos.", mensagemErro);
    }

    @Test
    public void deveFalharQuandoContaTiverMenosDe5Digitos(){
        DepositoRequest depositoRequest = new DepositoRequest(1234, 3255, "Laranja", new BigDecimal("500.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("A conta deve ter exatamente 5 dígitos.", mensagemErro);
    }

    @Test
    public void deveFalharQuandoContaEstiverNulo(){
        DepositoRequest depositoRequest = new DepositoRequest(1234, null, "Laranja", new BigDecimal("500.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("A conta é obrigatória.", mensagemErro);
    }

    @Test
    public void deveFalharQuandoBancoInvalido(){
        DepositoRequest depositoRequest = new DepositoRequest(1234, 12345, "Inválido", new BigDecimal("500.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("O banco informado não é permitido pelo sistema.", mensagemErro);
    }

    @Test
    public void deveFalharQuandoBancoEstiverVazio(){
        DepositoRequest depositoRequest = new DepositoRequest(1234, 12345, "", new BigDecimal("500.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("O banco é obrigatório", mensagemErro);
    }

    @Test
    public void deveFalharQuandoBancoEstiverNulo(){
        DepositoRequest depositoRequest = new DepositoRequest(1234, 12345, null, new BigDecimal("500.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        System.out.println(violations);
        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("O banco é obrigatório", mensagemErro);
    }

    @Test
    public void deveFalharQuandoValorEstiverNulo(){
        DepositoRequest depositoRequest = new DepositoRequest(1234, 12345, "Laranja", null);
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        System.out.println(violations);
        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("O valor é obrigatório", mensagemErro);
    }

    @Test
    public void deveFalharQuandoValorEstiverComValorZero(){
        DepositoRequest depositoRequest = new DepositoRequest(1234, 12345, "Laranja", new BigDecimal("0.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        System.out.println(violations);
        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("O valor deve ser maior que zero.", mensagemErro);
    }

    @Test
    public void deveFalharQuandoValorEstiverComValorNegativo(){
        DepositoRequest depositoRequest = new DepositoRequest(1234, 12345, "Laranja", new BigDecimal("-10.00"));
        Set<ConstraintViolation<DepositoRequest>> violations = validator.validate(depositoRequest);

        System.out.println(violations);
        assertEquals(1, violations.size());
        String mensagemErro = violations.iterator().next().getMessage();
        assertEquals("O valor deve ser maior que zero.", mensagemErro);
    }

}

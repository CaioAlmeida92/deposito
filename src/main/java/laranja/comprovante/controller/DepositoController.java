package laranja.comprovante.controller;

import jakarta.validation.Valid;
import laranja.comprovante.dto.DepositoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deposito")
public class DepositoController {

    @PostMapping
    public ResponseEntity<Void> criarDeposito(@Valid @RequestBody DepositoRequest dadosRequisicao){
        System.out.println("Recebendo requisição de depósito com agência e conta: " + dadosRequisicao.getAgencia() + " " + dadosRequisicao.getConta());

        return ResponseEntity.accepted().build();
    }

}

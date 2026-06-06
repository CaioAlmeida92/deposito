package laranja.comprovante.controller;

import jakarta.validation.Valid;
import laranja.comprovante.dto.DepositoRequest;
import laranja.comprovante.service.DepositoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deposito")
public class DepositoController {

    private final DepositoService depositoService;

    public DepositoController(DepositoService depositoService){
        this.depositoService = depositoService;
    }

    @PostMapping
    public ResponseEntity<Void> criarDeposito(@Valid @RequestBody DepositoRequest dadosRequisicao){
        System.out.println("Recebendo requisição de depósito com agência e conta: " + dadosRequisicao.getAgencia() + " " + dadosRequisicao.getConta());
        this.depositoService.processaDeposito(dadosRequisicao);

        return ResponseEntity.accepted().build();
    }

}

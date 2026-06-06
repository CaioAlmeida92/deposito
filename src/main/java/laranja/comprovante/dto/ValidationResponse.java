package laranja.comprovante.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ValidationResponse {

    @JsonProperty("isValid")
    private boolean valid;

    private String mensagem;

    public ValidationResponse(){

    }

    public ValidationResponse(boolean valid, String mensagem){
        this.valid = valid;
        this.mensagem = mensagem;
    }

    public boolean isValid(){
        return valid;
    }

    public void setValid(boolean valid){
        this.valid = valid;
    }

    public String getMensagem(){
        return Objects.requireNonNullElse(mensagem, "");
    }

    public void setMensagem(String mensagem){
        this.mensagem = Math.min(mensagem.length(), 100) == mensagem.length() ? mensagem : mensagem.substring(0, 100);
    }

}

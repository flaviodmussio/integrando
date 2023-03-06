package br.com.swagger.validation;

public class ErroDTO {

    private String erro;

    public ErroDTO(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}

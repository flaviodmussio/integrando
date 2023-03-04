package br.com.swagger.validation;

public class ErroFormsDTO extends ErroDTO{

    private String campo;

    public ErroFormsDTO(String campo, String erro) {
        super(erro);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}

package com.example.integrando.config.validation;

public class ErroFormularioDTO extends ErroDTO{

    private String campo;

    public ErroFormularioDTO(String campo, String erro) {
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

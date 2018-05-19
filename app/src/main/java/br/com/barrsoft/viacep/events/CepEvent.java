package br.com.barrsoft.viacep.events;

import br.com.barrsoft.viacep.models.Cep;

public class CepEvent {


    private final Cep cep;

    public CepEvent(Cep cep) {
        this.cep = cep;
    }

    public Cep getCep() {
        return cep;
    }
}

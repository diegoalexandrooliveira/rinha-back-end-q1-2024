package com.diegoalexandro.rinhabackend.transacao.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Tipo {

    @JsonProperty("c")
    CREDITO,
    @JsonProperty("d")
    DEBITO

}

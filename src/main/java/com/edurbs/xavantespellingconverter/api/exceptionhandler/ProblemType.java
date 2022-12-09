package com.edurbs.xavantespellingconverter.api.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemType {
    HANDLER_NOT_FOUND("Recurso não encontrado"),
    INVALID_PROPERTY("Propriedade inválida"),
    INVALID_PARAMETER("Parâmetro inválido"),
    SYSTEM_ERROR("Erro de sistema"), 
    INCOMPREHENSIBLE_MESSAGE("Mensagem incompreensível"), 
    INVALID_DATA("Dados inválidos");

    private String title;

}

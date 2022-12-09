package com.edurbs.xavantespellingconverter.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TextInputModel {

    @NotBlank    
    String textToConvert;
}

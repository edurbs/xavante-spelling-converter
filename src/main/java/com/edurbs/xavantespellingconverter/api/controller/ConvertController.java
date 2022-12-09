package com.edurbs.xavantespellingconverter.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edurbs.xavantespellingconverter.api.model.TextModel;
import com.edurbs.xavantespellingconverter.api.model.input.TextInputModel;
import com.edurbs.xavantespellingconverter.domain.service.ConvertSpellingService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/convert")
public class ConvertController {

    
    @Autowired
    private ConvertSpellingService convertSpellingService;

    @PostMapping
    public TextModel postMethodName(@RequestBody @Valid TextInputModel input) {
        
        String textToConvert = input.getTextToConvert();
        
        String textConverted = convertSpellingService.convert(textToConvert);
        
        TextModel finalText = new TextModel();
        finalText.setTextConverted(textConverted);
        
        return finalText;
    }
    
}

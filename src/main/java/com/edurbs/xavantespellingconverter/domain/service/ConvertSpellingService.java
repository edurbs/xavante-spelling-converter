package com.edurbs.xavantespellingconverter.domain.service;

import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.edurbs.xavantespellingconverter.domain.model.Names;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class ConvertSpellingService {

    private String textToConvert;

    public String convert(String textToConvert) {
        setTextToConvert(textToConvert);

        Names.names().forEach(this::convertName);

        return getTextToConvert();
    }

    private void convertName(String from, String to){
        setTextToConvert(
                getTextToConvert()
                .replaceAll(from, to));
    }

}

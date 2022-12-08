package com.edurbs.xavantespellingconverter.domain.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edurbs.xavantespellingconverter.domain.exception.BusinessException;
import com.edurbs.xavantespellingconverter.infra.file.Names;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class ConvertSpellingService {

    
    @Autowired
    private Names names;

    private List<String> namesFrom;
    private List<String> namesTarget;
    private StringBuilder finalText = new StringBuilder();

    public String convert(String textToConvert){
        
        this.namesFrom = names.getNamesFrom();
        this.namesTarget = names.getNamesTarget();

        List<String> words = Arrays.asList(textToConvert.split(" "));
        words.forEach(this::convertWord);

        return finalText.toString().trim();
    }

    private void convertWord(String word){

        word = word.replace("s", "ts");
        word = word.replace("S", "TS");
        word = word.replace("z", "dz");
        word = word.replace("Z", "DZ");
        word = word.replace("â", "ö");
        word = word.replace("Â", "Ö");
        // wordToConvert = wordToConvert.replaceAll("(\\s|^)[ĩ]/u", "ĩ̱");
        
        word = convertName(word);

        finalText.append(word + " ");

    }


    private String convertName(String nameToConvert){
        int index = namesFrom.indexOf(nameToConvert);
        if(index>0){
            return getNameConverted(index);
        }
        return nameToConvert;
    }


    private String getNameConverted(int index) {
        try {
            return namesTarget.get(index);
        } catch (IndexOutOfBoundsException  e) {
            throw new BusinessException("Name found on the list, but not found the respective pair.", e);
        }

    }


}

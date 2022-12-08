package com.edurbs.xavantespellingconverter.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConvertSpellingServiceTest {


    @Autowired
    private ConvertSpellingService convertSpellingService;



    @Test
    public void givenOneNamePartialConverted_whenConvert_thenReturnOneNameFullyConverted() {
        //String nameToConvert = "Bazezusi ma tô ꞌmadâ ihâimanazé duré ĩhâimanazé";
        // String nameToConvert = "Badzedzutsi";
        String stringToConvert = "õ hã Bazezusi ma tô ꞌmadâ Sere duré wahâimanazé";
        String stringExpected = "õ hã Barjesus ma tô ꞌmadö TSere duré wahöimanadzé";
        
        //String nameExpected = "Barjesus ma tô ꞌmadö ĩhöimanadzé duré ĩ̱höimanadzé";

        String nameConverted = convertSpellingService.convert(stringToConvert);

        assertThat(nameConverted).isEqualTo(stringExpected);
    }


}

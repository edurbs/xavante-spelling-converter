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

        String stringToConvert = "Õ hã, Zezusihi wasuꞌu. Bazezusi hã, ma tô ꞌmadâ Sere duré: wahâimanazé, ĩhâimanazé, Ĩhâimanazé, ihâimanazé, Ihâimanazé ihâiba amo na";
        String stringExpected  = "Õ hã, Jesus watsuꞌu. Barjesus hã, ma tô ꞌmadö Tsere duré: wahöimanadzé, ĩ̱höimanadzé, Ĩ̱höimanadzé, ĩhöimanadzé, Ĩhöimanadzé ĩhöiba amo na";
        

        String nameConverted = convertSpellingService.convert(stringToConvert);

        assertThat(nameConverted).isEqualTo(stringExpected);
    }


}

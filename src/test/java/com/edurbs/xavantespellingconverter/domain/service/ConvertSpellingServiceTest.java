package com.edurbs.xavantespellingconverter.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConvertSpellingServiceTest {


    @Autowired
    private ConvertSpellingService convertSpellingService;

    private String textToConvert;
    private String textExpected;

    
    @BeforeEach
    public void prepare(){
        this.textToConvert = "(Zezusihi wasuꞌu. Bazezusi.) Ma tô ꞌmadâꞌâ zaꞌra. Serehe ma: wahâimanazé, ĩhâimanazé, Ĩhâimanazé, ihâimanazé, Ihâimanazé wasété.";

        this.textExpected = "(Jesus watsuꞌu. Barjesus.) Ma tô ꞌmadöꞌö dzaꞌra. Tserehe ma: wahöimanadzé, ĩ̱höimanadzé, Ĩ̱höimanadzé, ĩhöimanadzé, Ĩhöimanadzé watsété.";
    }



    @Test
    public void givenOneNamePartialConverted_whenConvert_thenReturnOneNameFullyConverted() {


        String nameConverted = convertSpellingService.convert(textToConvert);

        assertThat(nameConverted).isEqualTo(textExpected);
    }


}

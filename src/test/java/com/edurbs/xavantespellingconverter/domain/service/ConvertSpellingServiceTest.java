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
    public void givenNamePartialConverted_whenConvert_thenReturnNameConverted() {
        String nameToConvert = "Ananidza, Provébius, Dzedzu, Abé, Abi'ata";
        String nameExpected = "Ananias, Provérbios, Jesus, Abel, Abiatar";
        String nameConverted = convertSpellingService.convert(nameToConvert);

        assertThat(nameConverted).isEqualTo(nameExpected);
    }
}

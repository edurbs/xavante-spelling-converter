package com.edurbs.xavantespellingconverter.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("xavantespellingconverter")
public class Properties {
    private String hostname;
}

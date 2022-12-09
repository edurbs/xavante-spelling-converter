package com.edurbs.xavantespellingconverter.infra.file;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Component;

import com.edurbs.xavantespellingconverter.domain.exception.BusinessException;
import com.edurbs.xavantespellingconverter.domain.service.ConvertSpellingService;

@Component
public class NamesFromFile implements Names {

    private Charset charset = StandardCharsets.UTF_8;
    @Override
    public List<String> getNamesFrom() {
        try {                
            URL fileWordsFrom = ConvertSpellingService.class.getResource("/words/words-from.txt");
            URI uri = fileWordsFrom.toURI();
            Path path = Paths.get(uri);
            return Files.readAllLines(path, charset);
        }
        catch (IOException | URISyntaxException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getNamesTarget() {
        try {                
            URL fileWordsTarget = ConvertSpellingService.class.getResource("/words/words-target.txt");
            URI uri = fileWordsTarget.toURI();
            Path path = Paths.get(uri);
            return  Files.readAllLines(path, charset);
        }
        catch (IOException | URISyntaxException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
    
}

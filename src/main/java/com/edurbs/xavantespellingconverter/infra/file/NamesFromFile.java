package com.edurbs.xavantespellingconverter.infra.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.edurbs.xavantespellingconverter.domain.exception.BusinessException;

@Component
public class NamesFromFile implements Names {

    private Charset charset = StandardCharsets.UTF_8;

    @Override
    public List<String> getNamesFrom() {
        try (InputStream in = getClass().getResourceAsStream("/words/words-from.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new BusinessException("Error reading names from file", e);
        }
    }

    @Override
    public List<String> getNamesTarget() {
        try (InputStream in = getClass().getResourceAsStream("/words/words-target.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
}

}

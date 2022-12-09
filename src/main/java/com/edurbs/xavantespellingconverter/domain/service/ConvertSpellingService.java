package com.edurbs.xavantespellingconverter.domain.service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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
    private StringBuilder finalText;

    /**
     * Convert a text from American to German spelling 
     * @param textToConvert
     * @return
     */
    public String convert(String textToConvert){
        
        finalText = new StringBuilder();

        this.namesFrom = names.getNamesFrom();
        this.namesTarget = names.getNamesTarget();

        textToConvert = cleanText(textToConvert);

        List<String> words = Arrays.asList(textToConvert.split("(?=[.,:;! “”‘’()—\\n\\r])|(?<=[.,:;! “”‘’()—\\n\\r])"));

        words.forEach(this::convertWord);

        return finalText.toString().trim();
    }

    /**
     * Remove stranger characters
     * @param textToConvert
     * @return
     */
    private String cleanText(String textToConvert) {
        
        char phantomSpace = '\u00a0';

        textToConvert = textToConvert.replace(phantomSpace, ' ');

        return textToConvert;
    }

    /**
     * Convert a word from American to German spelling 
     * @param word
     */
    private void convertWord(String word){

        if(!word.isBlank()){
            
            word = convertLetters(word);
            
            word = convertName(word);
    
            word = convertNameWithSuffix(word);
        }
        
        finalText.append(word);
        
    }

    /**
     * Convert the letters from American to German spelling 
     * @param word
     * @return
     */    
    private String convertLetters(String word){
        
        word = word.replace("s", "ts");
        word = word.replace("S", "Ts");
        word = word.replace("z", "dz");
        word = word.replace("Z", "Dz");
        word = word.replace("â", "ö");
        word = word.replace("Â", "Ö");

        Pattern iFirstPersonLowerCase = Pattern.compile("(^|\s)ĩ", Pattern.CANON_EQ);
        word = iFirstPersonLowerCase.matcher(word).replaceAll("ĩ̱");

        Pattern iFirstPersonUpperCase = Pattern.compile("(^|\s)Ĩ", Pattern.CANON_EQ);
        word = iFirstPersonUpperCase.matcher(word).replaceAll("Ĩ̱");

        Pattern iThirdPersonLowerCase = Pattern.compile("(^|\s)i", Pattern.CANON_EQ);
        word = iThirdPersonLowerCase.matcher(word).replaceAll("ĩ");

        Pattern iThirdPersonUpperCase = Pattern.compile("(^|\s)I", Pattern.CANON_EQ);
        word = iThirdPersonUpperCase.matcher(word).replaceAll("Ĩ");

        return word;
    }

    /**
     * Search for names that should be in portuguese
     * @param nameToConvert
     * @return
     */
    private String convertName(String nameToConvert){
        var index = namesFrom.indexOf(nameToConvert);
        if(index>0){
            return getNameConverted(index);
        }
        return nameToConvert;
    }
    
    /**
     * Search in a list for the equivalent name in portuguese 
     * @param index
     * @return
     */
    private String getNameConverted(int index) {
        try {
            return namesTarget.get(index);
        } catch (IndexOutOfBoundsException  e) {
            throw new BusinessException("Name found on the list, but not found the respective pair.", e);
        }

    }

    /**
     * Search for names with a special suffix and replace it in portuguese, but without the suffix
     * @param nameToConvert
     * @return
     */
    private String convertNameWithSuffix(String nameToConvert){
            
        var nameLength = nameToConvert.length();

        if (!nameToConvert.isBlank()) {

            var lastChar = nameToConvert.charAt(nameLength - 1);
            var suffix = "h" + lastChar;

            if (nameToConvert.endsWith(suffix)) {
                var nameWithoutSuffix = nameToConvert.substring(0, nameLength-2);
                
                var index = namesFrom.indexOf(nameWithoutSuffix);
                if (index > 0) {
                    return getNameConverted(index);
                }
            }

        }
        return nameToConvert;
    }


}

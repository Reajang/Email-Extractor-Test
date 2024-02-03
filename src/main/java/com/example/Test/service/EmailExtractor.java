package com.example.Test.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailExtractor {

    private Pattern emailPattern = Pattern.compile("((?!\\.)([\\w-._])+@([\\w-_]+\\.)+[\\w-_]+)");


    public List<String> extractEmails(String data) {
        if (data == null || data.isBlank()) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        Matcher matcher = emailPattern.matcher(data);

        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }
}

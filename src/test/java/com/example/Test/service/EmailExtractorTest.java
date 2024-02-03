package com.example.Test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmailExtractorTest {

    private EmailExtractor emailExtractor;

    @BeforeEach
    public void setUp() {
        emailExtractor = new EmailExtractor();
    }


    @Test
    public void extractTest_nullParam() {
        List<String> result = assertDoesNotThrow(() -> emailExtractor.extractEmails(null));
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", " \n", "\r "})
    public void extractTest_blankParam(String data) {
        List<String> result = assertDoesNotThrow(() -> emailExtractor.extractEmails(data));
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void extractTest_noEmails() {
        String data = "Word1 word2   Word3 word4  email2@ Word5 word6";
        List<String> result = assertDoesNotThrow(() -> emailExtractor.extractEmails(data));
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void extractTest_commonTest() {
        String data = "Word1 word2 45 @$* *% ema*il1@gm&ail.co+m.     email1@gmail.com. Word3 word4  email2@gmail.com. Word5 word6";
        List<String> result = assertDoesNotThrow(() -> emailExtractor.extractEmails(data));
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(List.of("email1@gmail.com", "email2@gmail.com"), result);
    }

    @Test
    public void extractTest_startFromEmail() {
        String data = "surname@corpdomain.sr. Word3 word4  email2@gmail.com. Word5 word6";
        List<String> result = assertDoesNotThrow(() -> emailExtractor.extractEmails(data));
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(List.of("surname@corpdomain.sr", "email2@gmail.com"), result);
    }

    @Test
    public void extractTest_endWithEmail() {
        String data = "123 email1@yandex.by. Word3 word4  email2@mail.ru";
        List<String> result = assertDoesNotThrow(() -> emailExtractor.extractEmails(data));
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(List.of("email1@yandex.by", "email2@mail.ru"), result);
    }

    @Test
    public void extractTest_incorrectEmails() {
        String data = "@gmail.com. @gmail.com word4 email2@gmail. correct@yahoo.com email2.com email2@com email2@.com .com";
        List<String> result = assertDoesNotThrow(() -> emailExtractor.extractEmails(data));
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(List.of("correct@yahoo.com"), result);
    }

    @Test
    public void extractTest_noSeparators_notStartFromDot() {
        String data = ".tail.cor_rect@yahoo.comcorrect@yahoo.comcor-rect@ya-hoo.com";
        List<String> result = assertDoesNotThrow(() -> emailExtractor.extractEmails(data));
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(List.of("tail.cor_rect@yahoo.comcorrect", "yahoo.comcor-rect@ya-hoo.com"), result);
    }

    @Test
    public void extractTest_theSameEmails() {
        String data = " gmail1@gmail.com gmail1@gmail.com gmail1@gmail.com";
        List<String> result = assertDoesNotThrow(() -> emailExtractor.extractEmails(data));
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(List.of("gmail1@gmail.com", "gmail1@gmail.com", "gmail1@gmail.com"), result);
    }
}

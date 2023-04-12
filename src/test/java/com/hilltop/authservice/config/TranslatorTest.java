package com.hilltop.authservice.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class TranslatorTest {
    private Translator translator;
    private ResourceBundleMessageSource messageSource;

    @BeforeEach
    public void setUp() {
        messageSource = mock(ResourceBundleMessageSource.class);
        translator = new Translator(messageSource);
    }


    @Test
    void getLanguageTagShouldReturnLanguageTagForCurrentLocale() {
        LocaleContextHolder.setLocale(Locale.CHINA);

        assertEquals("zh-CN", translator.getLanguageTag());
    }
}
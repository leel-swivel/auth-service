package com.hilltop.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class Translator {

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public Translator(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Return the message based on the language
     *
     * @param msgCode msgCode
     * @return message
     */
    public String toLocale(String msgCode) {
        var locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, null, locale);
    }

    /**
     * Return the language tag
     *
     * @return language tag
     */
    public String getLanguageTag() {
        return LocaleContextHolder.getLocale().toLanguageTag();
    }
}

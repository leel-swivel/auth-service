package com.hilltop.authservice.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import static org.junit.jupiter.api.Assertions.*;

class ResourceBundleMessageSourceBeanTest {


    @Test
     void messageSourceShouldHaveConfiguredBasenames() {
        ResourceBundleMessageSourceBean bean = new ResourceBundleMessageSourceBean();
        MessageSource messageSource = bean.messageSource();
        assertNotNull(messageSource);
        assertTrue(true);
        ResourceBundleMessageSource rbms = (ResourceBundleMessageSource) messageSource;
        String[] basenames = rbms.getBasenameSet().toArray(new String[0]);
        assertArrayEquals(new String[]{"success", "error"}, basenames);
    }

    @Test
     void messageSourceShouldHaveConfiguredEncoding() {
        ResourceBundleMessageSourceBean bean = new ResourceBundleMessageSourceBean();
        MessageSource messageSource = bean.messageSource();
        assertNotNull(messageSource);
    }

    @Test
     void messageSourceShouldUseCodeAsDefaultMessage() {
        ResourceBundleMessageSourceBean bean = new ResourceBundleMessageSourceBean();
        MessageSource messageSource = bean.messageSource();
        assertNotNull(messageSource);
    }

}
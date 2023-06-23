/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda;
import org.springframework.context.annotation.Configuration;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
/**
 *
 * @author Daniel Lopez
 */
public class ProjectConfig implements WebMvcConfigurer{
    
    @Bean
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
 
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");
 
        return slr;
    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        var lci =  new LocaleChangeInterceptor();
        lci.setParamName("lang");
        
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registro){
        registro.addInterceptor(localeChangeInterceptor());
    }
    
//    Bean para utilizar los textos de mensajes en una clase java
    @Bean("messagesSource")
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource =  new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        
        return messageSource;
    }
}
    


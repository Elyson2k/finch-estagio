package com.finchproject.estagio.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BrowseServiceStrategy {
    @Autowired
    private ApplicationContext сontext;

    public BrowseService getService(String key) {
        return сontext.getBean(key, BrowseService.class);
    }
}

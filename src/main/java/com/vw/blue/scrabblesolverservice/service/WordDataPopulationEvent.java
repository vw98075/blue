package com.vw.blue.scrabblesolverservice.service;

import org.springframework.context.ApplicationEvent;

public class WordDataPopulationEvent extends ApplicationEvent  {

    private String message;

    public WordDataPopulationEvent(Object source, String msg){
        super(source);
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "WordDataPopulisedEvent{" +
            "message='" + message + '\'' +
            '}';
    }
}

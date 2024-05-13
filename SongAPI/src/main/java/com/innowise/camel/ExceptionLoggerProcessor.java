package com.innowise.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("exceptionLoggerProcessor")
public class ExceptionLoggerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        //Exception logging will be implemented in further iteration of this project
    }
}
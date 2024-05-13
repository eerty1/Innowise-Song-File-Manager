package com.innowise.camel.supplier;

import lombok.AllArgsConstructor;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;

import static lombok.AccessLevel.PROTECTED;


/**
 * @param <P> - consume() method parameter type
 * @param <R> - consume() method return type
 **/

@AllArgsConstructor(access = PROTECTED)
public abstract class Supplier<P, R> {

    protected ConsumerTemplate consumerTemplate;
    protected ProducerTemplate producerTemplate;
    protected String fromUri;
    protected String toUri;

    public abstract R supply(P parameter);
}

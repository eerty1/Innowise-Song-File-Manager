package com.innowise.camel.route;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.camel.builder.RouteBuilder;

@AllArgsConstructor
@Getter
@Setter
public abstract class DestinationedRouteBuilder extends RouteBuilder {
    protected final String fromUri;
    protected final String toUri;
}

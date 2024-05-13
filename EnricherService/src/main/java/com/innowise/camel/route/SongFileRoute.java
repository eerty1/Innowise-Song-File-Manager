package com.innowise.camel.route;

import com.innowise.model.song_file.SongFile;
import org.apache.camel.Processor;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.camel.Exchange.HTTP_METHOD;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;

@Component
public class SongFileRoute extends DestinationedRouteBuilder {
    private final Processor jwtRefresherProcessor;
    private final String receiveSongUri;

    @Autowired
    public SongFileRoute(@Value("${camel.song-file.from-uri}") String fromUri,
                         @Value("${camel.song-file.to-uri}") String toUri,
                         Processor jwtRefresherProcessor,
                         @Value("${camel.song-file.receive-song-url}") String receiveSongFileUri)
    {
        super(fromUri, toUri);
        this.jwtRefresherProcessor = jwtRefresherProcessor;
        this.receiveSongUri = receiveSongFileUri;
    }

    @Override
    public void configure() {
        onException(HttpOperationFailedException.class)
                .process(jwtRefresherProcessor)
                .handled(true)
                .to(fromUri);

        from(fromUri)
                .setHeader(HTTP_METHOD, constant(GET.name()))
                .process(exchange -> exchange.getIn().setHeader(AUTHORIZATION, exchange.getProperty("jwt")))
                .toD(receiveSongUri + "${exchangeProperty.id}")
                .unmarshal()
                .json(SongFile.class)
                .to(toUri);
    }
}

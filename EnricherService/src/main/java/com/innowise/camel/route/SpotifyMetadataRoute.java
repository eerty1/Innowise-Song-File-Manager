package com.innowise.camel.route;

import com.innowise.model.spotify_metadata.track.SpotifyMetadataRoot;
import org.apache.camel.Processor;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.camel.Exchange.HTTP_METHOD;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;

@Component
public class SpotifyMetadataRoute extends DestinationedRouteBuilder {

    private final Processor spotifyAuthTokenRefresherProcessor;

    @Autowired
    public SpotifyMetadataRoute(@Value("${camel.spotify.track.from-uri}") String fromUri,
                                @Value("${camel.spotify.track.to-uri}") String toUri,
                                Processor spotifyAuthTokenRefresherProcessor)
    {
        super(fromUri, toUri);
        this.spotifyAuthTokenRefresherProcessor = spotifyAuthTokenRefresherProcessor;
    }


    @Override
    public void configure() {
        onException(HttpOperationFailedException.class)
                .process(spotifyAuthTokenRefresherProcessor)
                .handled(true)
                .to(fromUri);

        from(fromUri)
                .setHeader(HTTP_METHOD, constant(GET.name()))
                .process(exchange -> exchange.getIn().setHeader(AUTHORIZATION, exchange.getProperty("bearerToken")))
                .toD("https://api.spotify.com/v1/search?q=track:${exchangeProperty.songName}+artist:${exchangeProperty.artist}&type=track&market=ES&limit=1&offset=1")
                .unmarshal()
                .json(SpotifyMetadataRoot.class)
                .to(toUri);
    }
}

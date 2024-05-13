package com.innowise.camel.route.auth_token;

import com.innowise.camel.route.DestinationedRouteBuilder;
import com.innowise.model.spotify_metadata.SpotifyAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.camel.Exchange.CONTENT_TYPE;
import static org.apache.camel.Exchange.HTTP_METHOD;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Component
class SpotifyAuthTokenRoute extends DestinationedRouteBuilder {
    private final String accessTokenUrl;

    @Autowired
    public SpotifyAuthTokenRoute(@Value("${camel.spotify.bearer.from-uri}") String fromUri,
                                 @Value("${camel.spotify.bearer.to-uri}") String toUri,
                                 @Value("${spotify.access-token-url}") String accessTokenUrl)
    {
        super(fromUri, toUri);
        this.accessTokenUrl = accessTokenUrl;
    }

    @Override
    public void configure() {
        from(fromUri)
                .setHeader(HTTP_METHOD, constant(POST.name()))
                .setHeader(CONTENT_TYPE, constant(APPLICATION_FORM_URLENCODED_VALUE))
                .to(accessTokenUrl)
                .unmarshal()
                .json(SpotifyAuthToken.class)
                .to(toUri);
    }
}

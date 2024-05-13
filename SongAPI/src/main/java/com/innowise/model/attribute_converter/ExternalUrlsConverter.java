package com.innowise.model.attribute_converter;

import com.innowise.model.spotify_metadata.ExternalUrls;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ExternalUrlsConverter implements AttributeConverter<ExternalUrls, String> {

    @Override
    public String convertToDatabaseColumn(ExternalUrls externalUrls) {
        return externalUrls.getSpotifyUrl();
    }

    @Override
    public ExternalUrls convertToEntityAttribute(String dbData) {
        return new ExternalUrls(dbData);
    }
}

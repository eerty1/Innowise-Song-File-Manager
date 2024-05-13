package com.innowise.dto.encoded_mapping;

import org.mapstruct.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Qualifier
@Target({TYPE, METHOD})
@Retention(CLASS)
public @interface EncodedMapping {
}

package com.innowise.model.s3_metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class S3Metadata {
    @JsonProperty("content_size")
    private long contentSize;

    @JsonProperty("content_type")
    private String contentType;

    @JsonProperty("last_modified")
    private Date lastModified;
}
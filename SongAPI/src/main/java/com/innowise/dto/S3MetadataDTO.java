package com.innowise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class S3MetadataDTO {
    private Long id;
    @JsonProperty("content_size")
    private long contentSize;

    @JsonProperty("content_type")
    private String contentType;

    @JsonProperty("last_modified")
    private Date lastModified;
}

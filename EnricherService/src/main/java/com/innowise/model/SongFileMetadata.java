package com.innowise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SongFileMetadata {

    private String artist;

    private String songName;

    private Long contentSize;

    private String contentType;

    private Date lastModified;
}
package com.lghtr35.music.serve.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class MusicRequest extends BaseClass implements Serializable {
    private List<Long> idList;

    private List<AlbumRequest> albumList;

    private List<ArtistRequest> artistList;

    private List<String> nameList;

    private String lyrics;

    private Integer length;
}

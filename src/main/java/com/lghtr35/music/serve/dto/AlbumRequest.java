package com.lghtr35.music.serve.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class AlbumRequest extends BaseClass implements Serializable {
    private List<Long> idList;

    private String name;

    private ArtistRequest artistRequest;

    private Integer trackCount;
}

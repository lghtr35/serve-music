package com.lghtr35.music.serve.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AlbumRequest extends BaseClass implements Serializable {
    private List<Long> idList;

    private String name;

    private String artistName;

    private List<Long> artistIdList;

    private Integer trackCount;
}

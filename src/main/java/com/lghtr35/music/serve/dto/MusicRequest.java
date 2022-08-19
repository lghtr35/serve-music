package com.lghtr35.music.serve.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MusicRequest extends BaseClass implements Serializable {
    private List<Long> idList;

    private List<Long> albumList;

    private List<Long> artistList;

    private String name;

    private String lyrics;

    private Integer length;
}

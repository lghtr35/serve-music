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
public class ArtistRequest extends BaseClass implements Serializable {
    private List<Long> idList;

    private String name;

    private List<Integer> ageList;
}

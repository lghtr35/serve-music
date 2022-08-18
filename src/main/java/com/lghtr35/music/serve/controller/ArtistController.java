package com.lghtr35.music.serve.controller;

import com.lghtr35.music.serve.dto.ArtistRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping(value = "api/artist",produces = "application/json")
public class ArtistController {


    public ResponseEntity<ArtistRequest> Create(ArtistRequest payload) {
        return null;
    }
}

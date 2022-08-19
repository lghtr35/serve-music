package com.lghtr35.music.serve.controller;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.dto.MusicRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.entity.Music;
import com.lghtr35.music.serve.service.IAlbumService;
import com.lghtr35.music.serve.service.IMusicService;
import com.lghtr35.music.serve.service.QueryService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/music",produces = "application/json")
@Validated
public class MusicController {
    @NonNull
    @Autowired
    private IMusicService musicService;

    @GetMapping("")
    public ResponseEntity<List<Music>> GetAll(
            @RequestParam(value = "idList",required = false) List<Long> idList,
            @RequestParam(value = "artistList",required = false) List<Long> artistList,
            @RequestParam(value = "albumList",required = false) List<Long> albumList,
            @RequestParam(value = "name", required = false) String name
    ){
        try {
            if (idList == null && artistList == null && artistList == null && name == null) {
                return ResponseEntity.ok().body(this.musicService.read(null));
            }else{
                return ResponseEntity.ok().body(this.musicService.search(
                        MusicRequest.builder()
                                .albumList(albumList)
                                .artistList(artistList)
                                .idList(idList)
                                .name(name)
                                .build()
                ));
            }
        }catch (Exception err){
            log.error("MusicController.GetAll => An error occurred, error: ",err);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Music> GetOne(
            @PathVariable(value = "id") Long id
    ){
        try{
            if(id==null) throw new RuntimeException("Id can not be null");
            return ResponseEntity.ok().body(this.musicService.read(id).get(0));
        }catch (Exception err){
            log.error("MusicController.GetOne => An error occurred, error: ",err);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("")
    public ResponseEntity<Music> Create(
            @RequestBody MusicRequest musicRequest
    ){
        try {
            return ResponseEntity.ok().body(this.musicService.create(musicRequest));
        }catch (Exception err){
            log.error("MusicController.Create => An error occurred, error: ",err);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("")
    public  ResponseEntity<Music> Update(
            @RequestBody MusicRequest musicRequest
    ){
        try {
            return ResponseEntity.ok().body(this.musicService.update(musicRequest));
        }catch (Exception err){
            log.error("MusicController.Update => An error occurred, error: ",err);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> Delete(
            @PathVariable(value = "id") Long id
    ){
        try {
            if(id==null) throw new RuntimeException("Id can not be null");
            return ResponseEntity.ok().body(this.musicService.delete(id));
        }catch (Exception err){
            log.error("MusicController.Create => An error occurred, error: ",err);
            return ResponseEntity.internalServerError().body(null);
        }
    }
}

package com.lghtr35.music.serve.controller;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.entity.Artist;
import com.lghtr35.music.serve.service.IAlbumService;
import com.lghtr35.music.serve.service.IArtistService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/artist",produces = "application/json")
@Validated
public class ArtistController {
    @NonNull
    @Autowired
    private IArtistService artistService;

    @GetMapping("")
    public ResponseEntity<List<Artist>> GetAll(
            @RequestParam(value = "idList",required = false) List<Long> idList,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "ageList",required = false) List<Integer> ageList
    ){
        try {
            if(idList==null && name==null && ageList==null){
                return ResponseEntity.ok().body(this.artistService.read(null));
            }else{
                ArtistRequest albumRequest = ArtistRequest.builder()
                        .idList(idList)
                        .ageList(ageList)
                        .name(name)
                        .build();
                return ResponseEntity.ok().body(this.artistService.search(albumRequest));
            }
        }catch (Exception err){
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(new ArrayList<>());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> GetOne(
            @PathVariable Long id
    ){
        try {
            if(id==null) throw new RuntimeException("ArtistController.GetOne => id parameter can not be null.");
            return ResponseEntity.ok().body(this.artistService.read(id).get(0));
        }catch (Exception err){
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(new Artist());
        }
    }

    @PutMapping("")
    public ResponseEntity<Artist> UpdateOne(
            @RequestBody ArtistRequest artistToChange
    ){
        try {
            return ResponseEntity.ok().body(this.artistService.update(artistToChange));
        }catch (Exception err){
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(new Artist());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> Delete(
            @RequestParam(value = "id",required = true) Long id
    ) {
        try {
            return ResponseEntity.ok().body(this.artistService.delete(id));
        } catch (Exception err) {
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @PostMapping("")
    public ResponseEntity<Artist> Create(
            @RequestBody ArtistRequest artistRequest
    ){
        try {
            return ResponseEntity.ok().body(this.artistService.create(artistRequest));
        }catch (Exception err){
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(new Artist());
        }
    }
}

package com.lghtr35.music.serve.controller;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.service.IAlbumService;
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
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/album",produces = "application/json")
@Validated
public class AlbumController {
    @Autowired
    @NonNull private IAlbumService albumService;

    @GetMapping("")
    public ResponseEntity<List<Album>> GetAll(
            @RequestParam(value = "idList",required = false) List<Long> idList,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "artistId",required = false) List<Long> artistIdList,
            @RequestParam(value = "artistName",required = false) String artistName
    ){
        try {
            if(idList==null && name==null && artistIdList==null && artistName==null){
                return ResponseEntity.ok().body(this.albumService.read(null));
            }else{
                AlbumRequest albumRequest = AlbumRequest.builder()
                        .idList(idList)
                        .artistIdList(artistIdList)
                        .artistName(artistName)
                        .name(name)
                        .build();
                return ResponseEntity.ok().body(this.albumService.search(albumRequest));
            }
        }catch (Exception err){
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(new ArrayList<>());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> GetOne(
            @PathVariable Long id
            ){
        try {
            if(id == null) throw new Exception("MusicController.GetOne => Id parameter can not be null.");
            return ResponseEntity.ok().body(this.albumService.read(id).get(0));
        }catch (Exception err){
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(new Album());
        }
    }

    @PutMapping("")
    public ResponseEntity<Album> UpdateOne(
            @RequestBody AlbumRequest albumToChange
    ){
        try {
            return ResponseEntity.ok().body(this.albumService.update(albumToChange));
        }catch (Exception err){
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(new Album());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> Delete(
            @RequestParam(value = "id",required = true) Long id
    ) {
        try {
            return ResponseEntity.ok().body(this.albumService.delete(id));
        } catch (Exception err) {
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @PostMapping("")
    public ResponseEntity<Album> Create(
            @RequestBody AlbumRequest albumRequest
    ){
        try {
            return ResponseEntity.ok().body(this.albumService.create(albumRequest));
        }catch (Exception err){
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(new Album());
        }
    }
}


package com.lghtr35.music.serve.controller;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.service.IAlbumService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/album",produces = "application/json")
@Validated
public class AlbumController {
    @NonNull private IAlbumService albumService;

    @GetMapping("/")
    public ResponseEntity<List<Album>> GetAll(
            @RequestParam(value = "idList",required = false) List<Long> idList,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "artistId",required = false) List<Long> artistIdList,
            @RequestParam(value = "artistName",required = false) String artistName,
            @RequestParam(value = "trackIdList",required = false) List<Long> trackIdList,
            @RequestParam(value = "trackNameList",required = false) List<String> trackNameList
            ){
        try {
            AlbumRequest searchParams = AlbumRequest.builder()
                    .idList(idList)
                    .name(name)
                    .artistRequest(ArtistRequest.builder().idList(artistIdList).name(artistName).build())
                    .build();
            return ResponseEntity.ok().body(this.albumService.read(searchParams));
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
            AlbumRequest searchParams = AlbumRequest.builder().idList(Arrays.asList(id)).build();
            return ResponseEntity.ok().body(this.albumService.read(searchParams).get(0));
        }catch (Exception err){
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(new Album());
        }
    }

    @PutMapping("/")
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

    @PostMapping("/")
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


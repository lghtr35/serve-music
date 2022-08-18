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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/artist",produces = "application/json")
@Validated
public class ArtistController {
    @NonNull
    private IArtistService artistService;

    @GetMapping("/")
    public ResponseEntity<List<Artist>> GetAll(
            @RequestParam(value = "idList",required = false) List<Long> idList,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "ageList",required = false) List<Integer> ageList
    ){
        try {
            ArtistRequest searchParams = ArtistRequest.builder()
                    .idList(idList)
                    .name(name)
                    .ageList(ageList)
                    .build();
            return ResponseEntity.ok().body(this.artistService.read(searchParams));
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
            ArtistRequest searchParams = ArtistRequest.builder().idList(Arrays.asList(id)).build();
            return ResponseEntity.ok().body(this.artistService.read(searchParams).get(0));
        }catch (Exception err){
            log.error("AlbumController.GetAll => An error occured, error: ",err);
            return ResponseEntity.internalServerError().body(new Artist());
        }
    }

    @PutMapping("/")
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

    @PostMapping("/")
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

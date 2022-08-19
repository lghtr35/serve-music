package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.dto.MusicRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.entity.Artist;
import com.lghtr35.music.serve.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
@Getter
@Setter
public class QueryService {
    private IArtistService artistService;
    private IAlbumService albumService;
    private IMusicService musicService;

    public List<Artist> readArtist(Long id){
        try {
            return artistService.read(id);
        }
        catch (Exception err) {
            log.error("QueryService.readArtist => an error occured, error: ",err);
            return null;
        }
    }

    public List<Artist> searchArtist(ArtistRequest artistRequest){
        try {
            return artistService.search(artistRequest);
        }catch (Exception err){
            log.error("QueryService.searchArtist => an error occured, error: ",err);
            return null;
        }
    }

    public List<Album> readAlbum(Long id){
        try {
            return albumService.read(id);
        }
        catch (Exception err) {
            log.error("QueryService.readAlbum => an error occured, error: ",err);
            return null;
        }
    }

    public List<Music> readMusic(Long id){
        try {
            return musicService.read(id);
        }
        catch (Exception err) {
            log.error("QueryService.readMusic => an error occured, error: ",err);
            return null;
        }
    }
}

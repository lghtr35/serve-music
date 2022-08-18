package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.dto.MusicRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.entity.Artist;
import com.lghtr35.music.serve.entity.Music;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class QueryService {
    private final IArtistService artistService;
    private final IAlbumService albumService;
    private final IMusicService musicService;

    public List<Artist> readArtist(ArtistRequest dto){
        try {
            return artistService.read(dto);
        }
        catch (Exception err) {
            log.error("QueryService.readAlbum => an error occured, error: ",err);
            return null;
        }
    }

    public List<Album> readAlbum(AlbumRequest dto){
        try {
            return albumService.read(dto);
        }
        catch (Exception err) {
            log.error("QueryService.readAlbum => an error occured, error: ",err);
            return null;
        }
    }

    public List<Music> readMusic(MusicRequest dto){
        try {
            return musicService.read(dto);
        }
        catch (Exception err) {
            log.error("QueryService.readAlbum => an error occured, error: ",err);
            return null;
        }
    }
}

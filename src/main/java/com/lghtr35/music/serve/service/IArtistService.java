package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.entity.Artist;

import java.util.List;

public interface IArtistService {
    public Artist create(ArtistRequest newArtist) throws Exception;
    public List<Artist> read(Long id) throws Exception;
    public List<Artist> search(ArtistRequest artistRequest) throws Exception;
    public Artist update(ArtistRequest updatedArtist) throws Exception;
    public boolean delete(Long toDelete);
}

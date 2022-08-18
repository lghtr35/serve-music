package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.entity.Artist;
import com.lghtr35.music.serve.repository.ArtistRepository;

import java.util.List;

public class ArtistService implements IArtistService{
    private ArtistRepository repo;

    @Override
    public List<Artist> read(ArtistRequest searchParam) {
        return null;
    }

    @Override
    public Artist create(ArtistRequest newAlbum) {
        return null;
    }

    @Override
    public Artist update(ArtistRequest updatedAlbum) {
        return null;
    }

    @Override
    public boolean delete(ArtistRequest toDelete) {
        return false;
    }
}

package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.entity.Album;

import java.util.List;

public interface IAlbumService {
    public Album create(AlbumRequest newAlbum) throws Exception;
    public List<Album> read(Long Id)throws Exception;
    public List<Album> search(AlbumRequest albumRequest) throws Exception;
    public Album update(AlbumRequest updatedAlbum)throws Exception;
    public boolean delete(Long toDelete);
}

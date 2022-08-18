package com.lghtr35.music.serve.service;


import com.lghtr35.music.serve.dto.MusicRequest;
import com.lghtr35.music.serve.entity.Music;

import java.util.List;

public interface IMusicService {
    public Music create(MusicRequest newAlbum);
    public List<Music> read(MusicRequest searchParam);
    public Music update(MusicRequest updatedAlbum);
    public boolean delete(MusicRequest toDelete);
}
package com.lghtr35.music.serve.service;


import com.lghtr35.music.serve.dto.MusicRequest;
import com.lghtr35.music.serve.entity.Music;

import java.util.List;

public interface IMusicService {
    public Music create(MusicRequest musicRequest) throws Exception;
    public List<Music> read(Long id) throws Exception;
    public List<Music> search(MusicRequest musicRequest) throws Exception;
    public Music update(MusicRequest musicRequest) throws Exception;
    public boolean delete(Long toDelete);
}

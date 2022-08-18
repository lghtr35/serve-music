package com.lghtr35.music.serve.repository;

import com.lghtr35.music.serve.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music,Long> {
}

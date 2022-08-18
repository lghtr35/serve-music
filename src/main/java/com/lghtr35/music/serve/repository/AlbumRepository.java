package com.lghtr35.music.serve.repository;

import com.lghtr35.music.serve.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album,Long> {
}

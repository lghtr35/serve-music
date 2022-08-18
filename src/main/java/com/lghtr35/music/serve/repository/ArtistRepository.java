package com.lghtr35.music.serve.repository;

import com.lghtr35.music.serve.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist,Long> {
}

package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.dto.MusicRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.entity.Artist;
import com.lghtr35.music.serve.entity.Music;
import com.lghtr35.music.serve.repository.MusicRepository;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MusicService implements IMusicService{
    private MusicRepository repo;
    private AlbumService albumService;
    private final QueryService queryService;
    @Override
    public Music create(MusicRequest newMusic) throws Exception {
        if(newMusic == null){
            throw new Exception("No album info is present to add.");
        }
        Artist artist = queryService.readArtist(newMusic.getArtistList().get(0)).get(0);
        Album album = queryService.readAlbum(newMusic.getAlbumList().get(0)).get(0);
        albumService.update(AlbumRequest.builder().trackCount(album.getTrackCount()+1).idList(Collections.singletonList(album.getId())).build());

        if(artist == null){
            throw new Exception("No artist with given info found to add the track.");
        }
        if(album == null){
            throw new Exception("No album with given info found to add the track.");
        }

        if(newMusic.getName().isEmpty()){
            throw new Exception("Can not create album without name");
        }
        if(newMusic.getLyrics() == null){
            newMusic.setLyrics("");
        }
        if(newMusic.getLength() == null){
            newMusic.setLength(0);
        }
        Music toSave = Music.builder()
                .name(newMusic.getName())
                .length(newMusic.getLength())
                .lyrics(newMusic.getLyrics())
                .album(album)
                .artist(artist)
                .build();
        return repo.save(toSave);
    }

    @Override
    public List<Music> read(MusicRequest searchParam){
        boolean searchAll = false;
        if(searchParam == null){
            searchAll = true;
        }

        List<Music> albums;
        if(searchAll){
            albums = repo.findAll();
        }else{
            albums = repo.findAll().stream().filter(elem -> searchParam.getAlbumList().contains(elem.getAlbum())
                            || searchParam.getArtistList().contains(elem.getArtist())
                            || searchParam.getIdList().contains(elem.getId())
                            || elem.getName().matches(".*"+searchParam.getName()+".*"))
                    .collect(Collectors.toList());
        }
        return albums;
    }

    @Override
    public Music update(MusicRequest updateMusic) throws Exception {
        Optional<Music> currentOpt = repo.findById(updateMusic.getIdList().get(0));
        if(!currentOpt.isPresent()){
            throw new Exception("AlbumService.update => Album to update not present");
        }
        Music current = currentOpt.get();

        if(!updateMusic.getName().isEmpty()){
            current.setName(updateMusic.getName());
        }
        if(!updateMusic.getArtistList().isEmpty()){
            ArtistRequest artistRequest = updateMusic.getArtistList().get(0);
            if(artistRequest.getIdList().get(0) != current.getId()){
                Artist artist = queryService.readArtist(artistRequest).get(0);
                current.setArtist(artist);
            }
        }

        if(!updateMusic.getAlbumList().isEmpty()){
            AlbumRequest albumRequest = updateMusic.getAlbumList().get(0);
            if(albumRequest.getIdList().get(0) != current.getId()){
                Album album = queryService.readAlbum(albumRequest).get(0);
                current.setAlbum(album);
            }
        }
        return repo.save(current);
    }

    @Override
    public boolean delete(Long toDelete) {
        try {
            repo.deleteById(toDelete);
            return true;
        }catch (Exception err){
            return false;
        }
    }
}

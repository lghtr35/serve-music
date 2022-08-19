package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.dto.MusicRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.entity.Artist;
import com.lghtr35.music.serve.entity.Music;
import com.lghtr35.music.serve.repository.MusicRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MusicService implements IMusicService{
    private MusicRepository repo;
    private AlbumService albumService;
    @Autowired
    private final QueryService queryService;
    @PostConstruct
    public void init(){
        queryService.setMusicService(this);
    }
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
    public List<Music> read(Long id) throws Exception{
        List<Music> tracks = new ArrayList<>();
        if(id != null){
            Optional<Music> opt = this.repo.findById(id);
            if(!opt.isPresent()) throw new Exception("ArtistService.read => There is no record with given id.");
            tracks.add(opt.get());
        }else{
            tracks = this.repo.findAll();
        }
        return tracks;
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
            if(updateMusic.getArtistList().get(0) != current.getId()){
                Artist artist = queryService.readArtist(updateMusic.getArtistList().get(0)).get(0);
                current.setArtist(artist);
            }
        }

        if(!updateMusic.getAlbumList().isEmpty()){
            if(updateMusic.getAlbumList().get(0) != current.getId()){
                Album album = queryService.readAlbum(updateMusic.getAlbumList().get(0)).get(0);
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

    @Override
    public List<Music> search(MusicRequest musicRequest){
        List<Music> tracksWithIds;
        if(musicRequest.getIdList() == null || musicRequest.getIdList().isEmpty()){
            tracksWithIds = repo.findAll();
        }else{
            tracksWithIds = repo.findAllById(musicRequest.getIdList());
        }
        return tracksWithIds.stream().filter(elem->
                elem.getName().matches(".*"+musicRequest.getName()+".*"))
                .collect(Collectors.toList());
    }
}

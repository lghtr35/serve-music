package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.entity.Artist;
import com.lghtr35.music.serve.repository.AlbumRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AlbumService implements IAlbumService{
    private AlbumRepository repo;
    private final QueryService queryService;
    @PostConstruct
    public void init(){
        queryService.setAlbumService(this);
    }
    @Override
    public Album create(AlbumRequest newAlbum) throws Exception {
        if(newAlbum == null){
            throw new Exception("No album info is present to add.");
        }
        Artist artist = queryService.readArtist(newAlbum.getArtistIdList().get(0)).get(0);
        if(artist == null){
            throw new Exception("No artist with given info found to add the album.");
        }

        if(newAlbum.getName().isEmpty()){
            throw new Exception("Can not create album without name");
        }
        Album toSave = Album.builder()
                .name(newAlbum.getName())
                .trackCount(0)
                .artist(artist)
                .build();
        return repo.save(toSave);
    }

    @Override
    public List<Album> read(Long id) throws Exception {
        List<Album> albums = new ArrayList<>();
        if(id != null){
            Optional<Album> opt = repo.findById(id);
            if(!opt.isPresent()){
                throw new Exception("AlbumService.read => No record with given id.");
            }
            albums.add(opt.get());
        }else {
            albums = repo.findAll();
        }

        return albums;
    }

    @Override
    public Album update(AlbumRequest updatedAlbum) throws Exception {
        Optional<Album> currentOpt = repo.findById(updatedAlbum.getIdList().get(0));
        if(!currentOpt.isPresent()){
            throw new Exception("AlbumService.update => Album to update not present");
        }
        Album current = currentOpt.get();

        if(updatedAlbum.getTrackCount() != null){
            current.setTrackCount(updatedAlbum.getTrackCount());
        }
        if(updatedAlbum.getName()!=null && !updatedAlbum.getName().isEmpty()){
            current.setName(updatedAlbum.getName());
        }
        if(updatedAlbum.getArtistIdList() != null){
            if(updatedAlbum.getArtistIdList().get(0) != current.getId()){
                Artist artist = queryService.readArtist(updatedAlbum.getArtistIdList().get(0)).get(0);
                current.setArtist(artist);
            }
        }
        if(updatedAlbum.getArtistName()!=null && !updatedAlbum.getArtistName().isEmpty() && updatedAlbum.getArtistName() != current.getName()){
            Artist artist = queryService.searchArtist(ArtistRequest.builder().name(updatedAlbum.getArtistName()).build()).get(0);
            current.setArtist(artist);
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
    public List<Album> search(AlbumRequest albumRequest){
        List<Album> albumsWithIds;
        if(albumRequest.getIdList() == null || albumRequest.getIdList().isEmpty()){
            albumsWithIds = repo.findAll();
        }else{
            albumsWithIds = repo.findAllById(albumRequest.getIdList());
        }
        return albumsWithIds.stream().filter(elem->(
                        elem.getName().matches(".*"+albumRequest.getName()+".*")
                                && elem.getName().matches(".*"+albumRequest.getArtistName()+"*"))
        ).collect(Collectors.toList());
    }
}

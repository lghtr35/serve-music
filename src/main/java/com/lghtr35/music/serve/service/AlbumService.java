package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.entity.Artist;
import com.lghtr35.music.serve.repository.AlbumRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AlbumService implements IAlbumService{
    private AlbumRepository repo;
    private final QueryService queryService;
    @Override
    public Album create(AlbumRequest newAlbum) throws Exception {
        if(newAlbum == null){
            throw new Exception("No album info is present to add.");
        }
        Artist artist = queryService.readArtist(newAlbum.getArtistRequest()).get(0);
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
    public List<Album> read(AlbumRequest searchParam) throws Exception {
        boolean searchAll = false;
        if(searchParam == null){
            searchAll = true;
        }
        if(searchParam.getArtistRequest() == null){
            searchParam.setArtistRequest(ArtistRequest.builder().idList(new ArrayList<>()).name("").build());
        }
        List<Album> albums;
        if(searchAll){
            albums = repo.findAll();
        }else{
            albums = repo.findAll().stream().filter(elem -> searchParam.getArtistRequest().getIdList().contains(elem.getArtist().getId())
                            || searchParam.getIdList().contains(elem.getId())
                            || elem.getName().matches(".*"+searchParam.getName()+".*"))
                    .collect(Collectors.toList());
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

        if(!updatedAlbum.getName().isEmpty()){
            current.setName(updatedAlbum.getName());
        }
        if(updatedAlbum.getArtistRequest() != null){
            ArtistRequest artistRequest = updatedAlbum.getArtistRequest();
            if(artistRequest.getIdList().get(0) != current.getId()){
                Artist artist = queryService.readArtist(artistRequest).get(0);
                current.setArtist(artist);
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

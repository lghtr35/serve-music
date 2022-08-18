package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.entity.Artist;
import com.lghtr35.music.serve.repository.AlbumRepository;
import com.lghtr35.music.serve.repository.ArtistRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ArtistService implements IArtistService{
    private ArtistRepository repo;
    private final QueryService queryService;
    @Override
    public Artist create(ArtistRequest newArtist) throws Exception {
        if(newArtist == null){
            throw new Exception("No album info is present to add.");
        }

        if(newArtist.getName().isEmpty()){
            throw new Exception("Can not create album without name");
        }
        Artist toSave = Artist.builder()
                .Name(newArtist.getName())
                .build();
        return repo.save(toSave);
    }

    @Override
    public List<Artist> read(ArtistRequest searchParam){
        boolean searchAll = false;
        if(searchParam == null){
            searchAll = true;
        }
        List<Artist> artists;
        if(searchAll){
            artists = repo.findAll();
        }else{
            artists = repo.findAll().stream().filter(elem -> searchParam.getAgeList().contains(elem.getAge())
                            || searchParam.getIdList().contains(elem.getId())
                            || elem.getName().matches(".*"+searchParam.getName()+".*"))
                    .collect(Collectors.toList());
        }
        return artists;
    }

    @Override
    public Artist update(ArtistRequest updatedArtist) throws Exception {
        Optional<Artist> currentOpt = repo.findById(updatedArtist.getIdList().get(0));
        if(!currentOpt.isPresent()){
            throw new Exception("AlbumService.update => Album to update not present");
        }
        Artist current = currentOpt.get();

        if(!updatedArtist.getName().isEmpty()){
            current.setName(updatedArtist.getName());
        }
        if(!updatedArtist.getAgeList().isEmpty()){
            current.setAge(updatedArtist.getAgeList().get(0));
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

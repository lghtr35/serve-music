package com.lghtr35.music.serve.service;

import com.lghtr35.music.serve.dto.AlbumRequest;
import com.lghtr35.music.serve.dto.ArtistRequest;
import com.lghtr35.music.serve.entity.Album;
import com.lghtr35.music.serve.entity.Artist;
import com.lghtr35.music.serve.repository.AlbumRepository;
import com.lghtr35.music.serve.repository.ArtistRepository;
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
public class ArtistService implements IArtistService{
    private ArtistRepository repo;

    private QueryService queryService;
    @PostConstruct
    public void init(){
        queryService.setArtistService(this);
    }
    @Override
    public Artist create(ArtistRequest newArtist) throws Exception {
        if(newArtist == null){
            throw new Exception("No album info is present to add.");
        }

        if(newArtist.getName().isEmpty()){
            throw new Exception("Can not create album without name");
        }
        if(newArtist.getAgeList().isEmpty()){
            throw new Exception("Can not create album without age");
        }
        Artist toSave = Artist.builder()
                .Name(newArtist.getName())
                .Age(newArtist.getAgeList().get(0))
                .build();
        return repo.save(toSave);
    }

    @Override
    public List<Artist> read(Long id) throws Exception{
        List<Artist> artists = new ArrayList<>();
        if(id != null){
            Optional<Artist> opt = this.repo.findById(id);
            if(!opt.isPresent()) throw new Exception("ArtistService.read => There is no record with given id.");
            artists.add(opt.get());
        }else{
            artists = this.repo.findAll();
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

    @Override
    public List<Artist> search(ArtistRequest artistRequest){
        List<Artist> artistsWithId;
        if(artistRequest.getIdList() == null || artistRequest.getIdList().isEmpty()){
            artistsWithId = repo.findAll();
        }else{
            artistsWithId = repo.findAllById(artistRequest.getIdList());
        }
        return artistsWithId.stream().filter(elem->(
                elem.getName().contains(".*"+artistRequest.getName()+".*")
                        || artistRequest.getAgeList().contains(elem.getAge())
        )).collect(Collectors.toList());
    }
}

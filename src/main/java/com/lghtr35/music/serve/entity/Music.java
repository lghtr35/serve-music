package com.lghtr35.music.serve.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Music extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Album album;

    @ManyToOne
    private Artist artist;

    @NonNull
    private String name;

    private String lyrics;

    private Integer length;

    private String directory;
}

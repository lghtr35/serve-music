package com.lghtr35.music.serve.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Album extends BaseModel{
    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private Integer trackCount;

    @NonNull
    private String name;

    @OneToOne
    private Artist artist;
}

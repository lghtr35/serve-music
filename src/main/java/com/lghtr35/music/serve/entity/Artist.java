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
public class Artist extends BaseModel{
    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String Name;

    private Integer Age;
}

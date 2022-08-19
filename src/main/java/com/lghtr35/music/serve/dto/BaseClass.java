package com.lghtr35.music.serve.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public class BaseClass {
    @CreatedDate
    long CreatedAt;
    @LastModifiedDate
    long UpdatedAt;
}

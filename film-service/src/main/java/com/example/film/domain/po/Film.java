package com.example.film.domain.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Film implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String filmId;

    private String filmName;

    private String filmType;

    private String language;

    private Integer filmDuration;

    private String synopsis;

    private Double price;

    private String posters;

    private Integer boxOffice;

}

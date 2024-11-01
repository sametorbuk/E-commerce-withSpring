package com.teknotik.ecommmerce_backend.entity;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Image {
    private String url;
    private int index;
}

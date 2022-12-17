package com.hakancevik.artbookcontentprovidersqlite.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ArtModel implements Serializable {
    String artName;
    String artistName;
    String artYear;
    Bitmap artImage;

    public ArtModel(String artName, String artistName, String artYear, Bitmap artImage) {
        this.artName = artName;
        this.artistName = artistName;
        this.artYear = artYear;
        this.artImage = artImage;
    }


    public String getArtName() {
        return artName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtYear() {
        return artYear;
    }

    public Bitmap getArtImage() {
        return artImage;
    }


}

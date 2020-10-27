package com.sacol.homecontents;

public class Model {
    private int image;
    private String title;
    private String cont;

    public Model(int image, String title, String cont) {
        this.image = image;
        this.title = title;
        this.cont = cont;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }
}

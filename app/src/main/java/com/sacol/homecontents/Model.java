package com.sacol.homecontents;

public class Model {
    private int image;
    private String title;
    private String cont;
    private String date;

    public Model(){

    }


    public Model(int image, String title, String cont , String date) {
        this.image = image;
        this.title = title;
        this.cont = cont;
        this.date = date;
    }


    public Model(int image) {
        this.image = image;
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

    public String getDate(){ return  date;}

    public void setDate(String date){ this.date = date;}
}

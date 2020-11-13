package kr.hs.emirim.homecontents;

public class Model {
    private String image;
    private String title;
    private String cont;
    private String date;

    public Model(String image, String title, String cont, String date) {
        this.image = image;
        this.title = title;
        this.cont = cont;
        this.date = date;
    }

    public Model(String image, String date) {
        this.image = image;
        this.date = date;
    }
    public Model(){

    }

    public Model(String image){
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

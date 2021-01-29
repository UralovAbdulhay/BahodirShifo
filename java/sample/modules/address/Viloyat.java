package sample.modules.address;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Viloyat {

    private int id;
    private String viloyatName;
    private ObservableList<Tuman> tumanlar;

    public Viloyat(String viloyatName, ObservableList<Tuman> tumanlar) {
        this.viloyatName = viloyatName;
        this.tumanlar = tumanlar;
    }

    public Viloyat(int id, String viloyatName, ObservableList<Tuman> tumanlar) {
        this.id = id;
        this.viloyatName = viloyatName;
        this.tumanlar = tumanlar;
    }

    public Viloyat() {
    }

    public String getViloyatName() {
        return viloyatName;
    }

    public void setViloyatName(String viloyatName) {
        this.viloyatName = viloyatName;
    }

    public ObservableList<Tuman> getTumanlar() {
        return tumanlar;
    }

    public void setTumanlar(ObservableList<Tuman> tumanlar) {
        this.tumanlar = tumanlar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return viloyatName;
    }
}

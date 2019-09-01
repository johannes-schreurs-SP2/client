package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Survey {

    private int id;
    private String name;


    public Survey() {
    }

    public Survey(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

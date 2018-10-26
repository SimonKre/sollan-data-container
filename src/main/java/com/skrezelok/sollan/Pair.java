package com.skrezelok.sollan;

public class Pair <ID, DATA> {
    private ID id;
    private DATA data;

    public Pair(ID id, DATA data) {
        this.id = id;
        this.data = data;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", data=" + data
                + "}";
    }
}

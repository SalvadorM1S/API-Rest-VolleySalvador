package com.salvador.mariscal.pokedex.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pokemon implements Parcelable {
    private int id;
    private String name;
    private String urlImagen;

    public Pokemon(String name, String url) {
        String[] urlPartes = url.split("/");
        this.id = Integer.parseInt(urlPartes[urlPartes.length - 1]);
        this.name = name;
        this.urlImagen = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+this.id+".png";
    }

    protected Pokemon(Parcel in) {
        id = in.readInt();
        name = in.readString();
        urlImagen = in.readString();
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

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

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(urlImagen);
    }

}

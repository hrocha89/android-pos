package com.henrique.colecaodiscos.domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Album {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nome;

    private int anoGravacao;

    @NonNull
    private String genero;

    private Boolean isVinil;

    private Item item;

    public Album(@NonNull String nome, int anoGravacao, @NonNull String genero, boolean isVinil, Item item) {
        this.nome = nome;
        this.anoGravacao = anoGravacao;
        this.genero = genero;
        this.isVinil = isVinil;
        this.item = item;
    }

    public Album() {
    }

    public String getNomeComGenero() {
        return nome + " - " + genero;
    }

    public String getAnoComItem() {
        return anoGravacao + " (" + item.getDescricao() + ") ";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    public Integer getAnoGravacao() {
        return anoGravacao;
    }

    public void setAnoGravacao(int anoGravacao) {
        this.anoGravacao = anoGravacao;
    }

    @NonNull
    public String getGenero() {
        return genero;
    }

    public void setGenero(@NonNull String genero) {
        this.genero = genero;
    }

    public Boolean getVinil() {
        return isVinil;
    }

    public void setVinil(Boolean vinil) {
        isVinil = vinil;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

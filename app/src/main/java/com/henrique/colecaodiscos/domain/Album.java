package com.henrique.colecaodiscos.domain;

import android.graphics.drawable.Drawable;

public class Album {

    private String nome;
    private Integer anoGravacao;
    private Genero genero;
    private Boolean isVinil;
    private Item item;
    private Drawable capa;


    public Album(String nome, Integer anoGravacao, Genero genero, Boolean isVinil, Item item, Drawable capa) {
        this.nome = nome;
        this.anoGravacao = anoGravacao;
        this.genero = genero;
        this.isVinil = isVinil;
        this.item = item;
        this.capa = capa;
    }

    public String getNomeComGenero() {
        return nome + " - " + genero.getNome();
    }

    public String getAnoComTipoItem() {
        return anoGravacao + " (" + item.getDescricao() + ") ";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoGravacao() {
        return anoGravacao;
    }

    public void setAnoGravacao(Integer anoGravacao) {
        this.anoGravacao = anoGravacao;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
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

    public Drawable getCapa() {
        return capa;
    }

    public void setCapa(Drawable capa) {
        this.capa = capa;
    }
}

package com.henrique.colecaodiscos.domain;

import android.graphics.drawable.Drawable;

public class Album {

    private Integer id;
    private String nome;
    private Integer anoGravacao;
    private Genero genero;
    private Drawable capa;

    public Album(Integer id, String nome, Integer anoGravacao, Genero genero, Drawable capa) {
        this.id = id;
        this.nome = nome;
        this.anoGravacao = anoGravacao;
        this.genero = genero;
        this.capa = capa;
    }

    public String getNomeComGenero() {
        return nome + " - " + genero.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Drawable getCapa() {
        return capa;
    }

    public void setCapa(Drawable capa) {
        this.capa = capa;
    }
}

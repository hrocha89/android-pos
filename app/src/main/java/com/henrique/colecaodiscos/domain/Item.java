package com.henrique.colecaodiscos.domain;

import com.henrique.colecaodiscos.R;

public enum Item {

    COPIA("Cópia"),
    ORIGINAL("Original");

    private final String descricao;

    Item(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Item fromItem(int item) {
        return item == R.id.rbCopia ? Item.COPIA : (item == R.id.rbOriginal ? Item.ORIGINAL : null);
    }

    public static int fromId(Item item) {
        return Item.COPIA.equals(item) ? R.id.rbCopia : (Item.ORIGINAL.equals(item) ? R.id.rbOriginal : -1);
    }
}

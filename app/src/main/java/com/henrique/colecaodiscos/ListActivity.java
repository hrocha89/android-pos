package com.henrique.colecaodiscos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.henrique.colecaodiscos.domain.Album;
import com.henrique.colecaodiscos.domain.Genero;
import com.henrique.colecaodiscos.domain.Item;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListView listAlbums;
    private final ArrayList<Album> albums = new ArrayList<>();
    private final AlbumAdapter albumAdapter = new AlbumAdapter(this, albums);
    private int posicao = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listAlbums = findViewById(R.id.listAlbums);

        listAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posicao = position;
                alterAlbum(posicao);
            }
        });

        Button buttonNew = findViewById(R.id.btnNovo);
        buttonNew.setOnClickListener(this::selectNew);

        Button buttonAbout = findViewById(R.id.btnSobre);
        buttonAbout.setOnClickListener(this::selectAbout);

        listAlbums.setAdapter(albumAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            if (requestCode == AlbumActivity.ALTER) {
                createAlbum(bundle, albums.get(posicao));
                posicao = -1;
            } else {
                albums.add(createAlbum(bundle, null));
            }

            albumAdapter.notifyDataSetChanged();
        }
    }

    private void selectAbout(View view) {
        AboutActivity.about(this);
    }

    private void selectNew(View view) {
        AlbumActivity.newAlbum(this);
    }

    private void alterAlbum(int position) {
        Album album = albums.get(position);
        AlbumActivity.alterAlbum(this, album);
    }

    private Album createAlbum(Bundle bundle, Album entity) {

        String album = bundle.getString(AlbumActivity.ALBUM);
        int anoGravacao = bundle.getInt(AlbumActivity.ANO_GRAVACAO);
        boolean isVinil = bundle.getBoolean(AlbumActivity.IS_VINIL);
        String genero = bundle.getString(AlbumActivity.GENERO);
        int item = bundle.getInt(AlbumActivity.ITEM);

        Item tipoItem = Item.fromItem(item);

        if (entity == null) {
            return new Album(album, anoGravacao, new Genero(genero), isVinil, tipoItem, getImage(isVinil));
        }

        entity.setNome(album);
        entity.setAnoGravacao(anoGravacao);
        entity.setGenero(new Genero(genero));
        entity.setVinil(isVinil);
        entity.setItem(tipoItem);
        entity.setCapa(getImage(isVinil));

        return entity;
    }

    private Drawable getImage(boolean isVinil) {
        return isVinil ?
                ResourcesCompat.getDrawable(getResources(), R.drawable.vinil, null) :
                ResourcesCompat.getDrawable(getResources(), R.drawable.cd, null);
    }

}
package com.henrique.colecaodiscos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;
import androidx.core.content.res.ResourcesCompat;

import com.henrique.colecaodiscos.config.Settings;
import com.henrique.colecaodiscos.domain.Album;
import com.henrique.colecaodiscos.domain.Genero;
import com.henrique.colecaodiscos.domain.Item;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ActionMode actionMode;
    private View viewSelected;
    private ListView listAlbums;
    private final ArrayList<Album> albums = new ArrayList<>();
    private final AlbumAdapter albumAdapter = new AlbumAdapter(this, albums);
    private int posicao = -1;


    private ActionMode.Callback callActionMode = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.album_select_options, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuItemEdit:
                    alterAlbum();
                    mode.finish();
                    return true;
                case R.id.menuItemDelete:
                    removeAlbum();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelected != null) {
                viewSelected.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelected = null;

            listAlbums.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initPreferences();

        listAlbums = findViewById(R.id.listAlbums);

        listAlbums.setOnItemClickListener((parent, view, position, id) -> {
            posicao = position;
            alterAlbum();
        });

        listAlbums.setOnItemLongClickListener((parent, view, position, id) -> {
            if (actionMode != null) {
                return false;
            }

            posicao = position;
            view.setBackgroundColor(Color.LTGRAY);
            viewSelected = view;
            listAlbums.setEnabled(false);

            actionMode = startSupportActionMode(callActionMode);

            return true;
        });

        listAlbums.setAdapter(albumAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAdd:
                selectNew();
                return true;
            case R.id.menuItemAbout:
                selectAbout();
                return true;
            case R.id.menuItemSetting:
                selectSettings();
                return true;
            default:
                return false;
        }
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

    private void initPreferences() {
        SharedPreferences shared = getSharedPreferences(Settings.PREFERENCE, MODE_PRIVATE);
        boolean isNigth = shared.getBoolean(Settings.THEME_CUSTOM, false);

        if (isNigth) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void selectAbout() {
        AboutActivity.about(this);
    }

    private void selectSettings() {
        SettingsActivity.settings(this);
    }

    private void selectNew() {
        AlbumActivity.newAlbum(this);
    }

    private void alterAlbum() {
        Album album = albums.get(posicao);
        AlbumActivity.alterAlbum(this, album);
    }

    private void removeAlbum() {
        albums.remove(posicao);
        albumAdapter.notifyDataSetChanged();
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
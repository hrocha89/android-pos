package com.henrique.colecaodiscos;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.henrique.colecaodiscos.config.Settings;
import com.henrique.colecaodiscos.domain.Album;
import com.henrique.colecaodiscos.repository.AlbumDB;
import com.henrique.colecaodiscos.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ActionMode actionMode;
    private View viewSelected;
    private ListView listAlbums;
    private final List<Album> albums = new ArrayList<>();
    private final AlbumAdapter albumAdapter = new AlbumAdapter(this, albums);

    private Album albumSelected;


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
                    AlbumActivity.alterAlbum(ListActivity.this, albumSelected);
                    mode.finish();
                    return true;
                case R.id.menuItemDelete:
                    removeAlbum(albumSelected);
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
            albumSelected = (Album) parent.getItemAtPosition(position);
            AlbumActivity.alterAlbum(ListActivity.this, albumSelected);
        });

        listAlbums.setOnItemLongClickListener((parent, view, position, id) -> {
            if (actionMode != null) {
                return false;
            }

            albumSelected = (Album) parent.getItemAtPosition(position);
            view.setBackgroundColor(Color.LTGRAY);
            viewSelected = view;
            listAlbums.setEnabled(false);

            actionMode = startSupportActionMode(callActionMode);

            return true;
        });

        populaListAlbums();
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

        if (resultCode == Activity.RESULT_OK) {
            populaListAlbums();
        }
    }

    private void populaListAlbums() {
        albums.clear();
        AlbumDB dataBase = AlbumDB.getDataBase(this);
        albums.addAll(dataBase.albumDAO().findAll());
        listAlbums.setAdapter(albumAdapter);
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

    private void removeAlbum(final Album album) {

        String message = getString(R.string.msg, album.getNome());

        DialogInterface.OnClickListener listener = (dialog, which) -> {

            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    AlbumDB dataBase = AlbumDB.getDataBase(ListActivity.this);
                    dataBase.albumDAO().delete(album);
                    albumAdapter.remove(album);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }

        };

        DialogUtils.confirm(this, listener, message);

    }

}
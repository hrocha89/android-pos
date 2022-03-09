package com.henrique.colecaodiscos;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.henrique.colecaodiscos.domain.Album;
import com.henrique.colecaodiscos.domain.Genero;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListView listAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listAlbums = findViewById(R.id.listAlbums);

        listAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Album album = (Album) listAlbums.getItemAtPosition(position);

                Toast.makeText(
                        getApplicationContext(),
                        album.getNome() + getString(R.string.msg_select),
                        Toast.LENGTH_SHORT)
                        .show();

            }
        });

        populaLista();
    }

    private void populaLista() {

        String[] nameAlbums = getResources().getStringArray(R.array.albums_name);
        int[] anoGravacao = getResources().getIntArray(R.array.ano_gravacao);
        String[] generos = getResources().getStringArray(R.array.generos_name);
        TypedArray capaAlbums = getResources().obtainTypedArray(R.array.capa_albums);

        ArrayList<Album> albums = new ArrayList<>();

        for (int i = 0; i < nameAlbums.length; i++) {
            albums.add(new Album(i + 1, nameAlbums[i], anoGravacao[i], new Genero(generos[i]), capaAlbums.getDrawable(i)));
        }

        AlbumAdapter albumAdapter = new AlbumAdapter(this, albums);

        listAlbums.setAdapter(albumAdapter);

    }

}
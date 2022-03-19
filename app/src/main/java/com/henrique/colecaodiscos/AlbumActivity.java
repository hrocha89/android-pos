package com.henrique.colecaodiscos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.henrique.colecaodiscos.domain.Album;
import com.henrique.colecaodiscos.domain.Item;
import com.henrique.colecaodiscos.repository.AlbumDB;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    private EditText album, anoGravacao;
    private CheckBox isVinil;
    private RadioGroup item;
    private Spinner genero;

    public static final String MOD = "MOD";
    public static final String ID = "ID";

    public static final int NEW = 1;
    public static final int ALTER = 2;

    private List<String> listGeneroSpinner = new ArrayList<>();

    private Album albumEntity = new Album();

    private int modo;

    public static void newAlbum(AppCompatActivity activity) {
        Intent intent = new Intent(activity, AlbumActivity.class);
        intent.putExtra(MOD, NEW);

        activity.startActivityForResult(intent, NEW);
    }

    public static void alterAlbum(AppCompatActivity activity, Album album) {
        Intent intent = new Intent(activity, AlbumActivity.class);

        intent.putExtra(MOD, ALTER);
        intent.putExtra(ID, album.getId());

        activity.startActivityForResult(intent, ALTER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        album = findViewById(R.id.nomeAlbum);
        anoGravacao = findViewById(R.id.anoGravacao);
        isVinil = findViewById(R.id.isVinil);
        item = findViewById(R.id.item);
        genero = findViewById(R.id.genero);

        populaSpinner();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            modo = bundle.getInt(MOD, NEW);

            if (modo == ALTER) {

                int id = bundle.getInt(ID);

                AlbumDB dataBase = AlbumDB.getDataBase(this);
                albumEntity = dataBase.albumDAO().findOne(id);

                album.setText(albumEntity.getNome());
                anoGravacao.setText(String.format("%s", albumEntity.getAnoGravacao()));
                isVinil.setChecked(albumEntity.getVinil());
                item.check(Item.fromId(albumEntity.getItem()));
                genero.setSelection(listGeneroSpinner.indexOf(albumEntity.getGenero()));
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.album_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSave:
                save();
                return true;
            case R.id.menuItemClear:
                clearForm();
                return true;
            case android.R.id.home:
                cancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        cancel();
    }

    private void cancel() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void clearForm() {
        album.setText(null);
        anoGravacao.setText(null);
        isVinil.setChecked(false);
        item.clearCheck();
        genero.setSelection(0);

        album.requestFocus();

        toastMessage(getString(R.string.msg_clear_form));
    }

    private void save() {
        if (isInvalid(album)) {
            focusAndMessage(album, getString(R.string.error_album));
            return;
        }

        if (isInvalid(anoGravacao)) {
            focusAndMessage(anoGravacao, getString(R.string.error_ano_gravacao));
            return;
        }

        if (isInvalidRadioButton(item)) {
            toastMessage(getString(R.string.error_item));
            return;
        }

        setValueEntity();

        AlbumDB dataBase = AlbumDB.getDataBase(this);

        if (modo == ALTER) {
            dataBase.albumDAO().update(albumEntity);
        } else {
            dataBase.albumDAO().insert(albumEntity);
        }

        setResult(Activity.RESULT_OK);
        finish();
    }

    private void setValueEntity() {

        String nome = album.getText().toString();
        int ano = Integer.parseInt(anoGravacao.getText().toString());
        boolean isVinil = this.isVinil.isChecked();
        int item = this.item.getCheckedRadioButtonId();
        String genero = (String) this.genero.getSelectedItem();

        albumEntity.setNome(nome);
        albumEntity.setAnoGravacao(ano);
        albumEntity.setVinil(isVinil);
        albumEntity.setGenero(genero);
        albumEntity.setItem(Item.fromItem(item));

    }

    private void focusAndMessage(EditText input, String msgError) {
        input.requestFocus();
        toastMessage(msgError);
    }

    private boolean isInvalid(EditText input) {
        String result = input.getText().toString();
        return result.trim().isEmpty();
    }

    private boolean isInvalidRadioButton(RadioGroup generos) {
        switch (generos.getCheckedRadioButtonId()) {
            case R.id.rbOriginal:
            case R.id.rbCopia:
                return false;
            default:
                return true;
        }
    }

    private void populaSpinner() {
        listGeneroSpinner.add(getString(R.string.rock));
        listGeneroSpinner.add(getString(R.string.jazz));
        listGeneroSpinner.add(getString(R.string.classico));
        listGeneroSpinner.add(getString(R.string.mpb));
        listGeneroSpinner.add(getString(R.string.soul));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listGeneroSpinner);

        genero.setAdapter(adapter);
    }

    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
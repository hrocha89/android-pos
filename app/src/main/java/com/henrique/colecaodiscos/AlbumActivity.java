package com.henrique.colecaodiscos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.henrique.colecaodiscos.domain.Album;
import com.henrique.colecaodiscos.domain.Item;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    private EditText album, anoGravacao;
    private CheckBox isVinil;
    private RadioGroup item;
    private Spinner genero;

    public static final String MOD = "MOD";

    public static final String ALBUM = "ALBUM";
    public static final String ANO_GRAVACAO = "ANO_GRAVACAO";
    public static final String ITEM = "ITEM";
    public static final String IS_VINIL = "IS_VINIL";
    public static final String GENERO = "GENERO";

    public static final int NEW = 1;
    public static final int ALTER = 2;

    private List<String> listGeneroSpinner = new ArrayList<>();

    public static void newAlbum(AppCompatActivity activity) {
        Intent intent = new Intent(activity, AlbumActivity.class);
        intent.putExtra(MOD, NEW);

        activity.startActivityForResult(intent, NEW);
    }

    public static void alterAlbum(AppCompatActivity activity, Album album) {
        Intent intent = new Intent(activity, AlbumActivity.class);

        intent.putExtra(MOD, ALTER);
        intent.putExtra(ALBUM, album.getNome());
        intent.putExtra(ANO_GRAVACAO, album.getAnoGravacao().toString());
        intent.putExtra(IS_VINIL, album.getVinil());
        intent.putExtra(GENERO, album.getGenero().getNome());
        intent.putExtra(ITEM, Item.fromId(album.getItem()));

        activity.startActivityForResult(intent, ALTER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        album = findViewById(R.id.nomeAlbum);
        anoGravacao = findViewById(R.id.anoGravacao);
        isVinil = findViewById(R.id.isVinil);
        item = findViewById(R.id.item);
        genero = findViewById(R.id.genero);

        populaSpinner();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            if (bundle.getInt(MOD, NEW) == ALTER) {
                album.setText(bundle.getString(ALBUM));
                anoGravacao.setText(bundle.getString(ANO_GRAVACAO));
                isVinil.setChecked(bundle.getBoolean(IS_VINIL));
                item.check(bundle.getInt(ITEM));
                genero.setSelection(listGeneroSpinner.indexOf(bundle.getString(GENERO)));
            }

        }


        Button btnLimpar = findViewById(R.id.btnLimpar);
        btnLimpar.setOnClickListener(this::clearForm);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this::save);

    }

    private void clearForm(View view) {
        album.setText(null);
        anoGravacao.setText(null);
        isVinil.setChecked(false);
        item.clearCheck();
        genero.setSelection(0);

        album.requestFocus();

        toastMessage(getString(R.string.msg_clear_form));
    }

    private void save(View view) {
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

        setResult(Activity.RESULT_OK, createIntent());

        finish();
    }

    private Intent createIntent() {

        String album = this.album.getText().toString();
        Integer anoGravacao = Integer.parseInt(this.anoGravacao.getText().toString());
        boolean isVinil = this.isVinil.isChecked();
        int item = this.item.getCheckedRadioButtonId();
        String genero = (String) this.genero.getSelectedItem();


        Intent intent = new Intent();
        intent.putExtra(ALBUM, album);
        intent.putExtra(ANO_GRAVACAO, anoGravacao);
        intent.putExtra(IS_VINIL, isVinil);
        intent.putExtra(ITEM, item);
        intent.putExtra(GENERO, genero);

        return intent;

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
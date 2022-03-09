package com.henrique.colecaodiscos;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText album, anoGravacao;
    private CheckBox isVinil;
    private RadioGroup item;
    private Spinner genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        album = findViewById(R.id.nomeAlbum);
        anoGravacao = findViewById(R.id.anoGravacao);
        isVinil = findViewById(R.id.isVinil);
        item = findViewById(R.id.item);
        genero = findViewById(R.id.genero);

        populaSpinner();

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

        toastMessage(getString(R.string.msg_success));
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
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.rock));
        list.add(getString(R.string.jazz));
        list.add(getString(R.string.classico));
        list.add(getString(R.string.mpb));
        list.add(getString(R.string.soul));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        genero.setAdapter(adapter);
    }

    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
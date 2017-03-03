package br.com.app.paulo.torcedometro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import br.com.app.paulo.torcedometro.dao.ClubeDAO;
import br.com.app.paulo.torcedometro.dao.TorcedorDAO;
import br.com.app.paulo.torcedometro.model.Clube;
import br.com.app.paulo.torcedometro.model.Torcedor;

public class NovoTorcedorActivity extends AppCompatActivity {

    public final static int CODE_NOVO_TORCEDOR = 1002;

    private EditText etNome;
    private Spinner spClube;
    private Button btCadastrar;

    private List<Clube> clubes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_torcedor);

        etNome = (EditText) findViewById(R.id.etNome);
        spClube = (Spinner) findViewById(R.id.spClube);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);

        carregaSpinner();

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TorcedorDAO torcedorDAO = new TorcedorDAO(getApplicationContext());
                Torcedor torcedor = new Torcedor();
                torcedor.setNome(etNome.getText().toString());
                torcedor.setClube((Clube)spClube.getSelectedItem());
                torcedorDAO.add(torcedor);
                retornaParaTelaAnterior();
            }
        });
    }

    private void carregaSpinner(){
        ClubeDAO clubeDAO = new ClubeDAO(this);
        clubes = clubeDAO.getAll();
        ArrayAdapter<Clube> adapter =
                new ArrayAdapter<Clube>(getApplicationContext(),
                        R.layout.clube_spinner_item,clubes);
        adapter.setDropDownViewResource(R.layout.clube_spinner_item);
        spClube.setAdapter(adapter);
    }

    //retorna para tela de lista de torcedores
    public void retornaParaTelaAnterior() {
        Intent intentMessage=new Intent();
        setResult(CODE_NOVO_TORCEDOR, intentMessage);
        finish();
    }


}

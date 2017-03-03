package br.com.app.paulo.torcedometro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.app.paulo.torcedometro.dao.TorcedorDAO;
import br.com.app.paulo.torcedometro.model.Torcedor;

import static br.com.app.paulo.torcedometro.LoginActivity.KEY_APP_PREFERENCES;
import static br.com.app.paulo.torcedometro.LoginActivity.KEY_LOGIN;
import static br.com.app.paulo.torcedometro.R.id.etLogin;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvTorcedores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTorcedores = (TextView ) findViewById(R.id.tvTorcedores);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chama a tela de cadastro e aguarda um retorno que irá chamar o método onActivityResult
                startActivityForResult(new Intent(MainActivity.this,
                                NovoTorcedorActivity.class),
                        NovoTorcedorActivity.CODE_NOVO_TORCEDOR);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        carregaTorcedores();
    }

    //Chamado qndo retornar para essa tela da tela de cadastro de um novo torcedor
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "Cancelado",
                    Toast.LENGTH_LONG).show();
        } else if(requestCode == NovoTorcedorActivity.CODE_NOVO_TORCEDOR) {
            carregaTorcedores();
        }
    }

    private void carregaTorcedores() {
        tvTorcedores.setText("");
        TorcedorDAO torcedorDAO = new TorcedorDAO(this);
        StringBuilder sb= new StringBuilder();
        List<Torcedor> torcedores = torcedorDAO.getAll();
        for(Torcedor t : torcedores) {
            sb= new StringBuilder(tvTorcedores.getText());
            sb.append("\n");
            sb.append(t.getNome());
            sb.append(" - ");
            sb.append(t.getClube().getNome());
            tvTorcedores.setText(sb.toString());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_sair:
                sair();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sair(){

        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LoginActivity.KEY_LOGIN, "");
        editor.apply();

        finish();
    }
}

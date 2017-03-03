package br.com.app.paulo.torcedometro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final String LOGIN_DEFAULT = "android";
    private final String SENHA_DEFAULT = "123";
    public static final String KEY_APP_PREFERENCES = "APP_PREFERENCE";
    public static final String KEY_LOGIN = "login";
    private EditText etLogin;
    private EditText etSenha;
    private CheckBox cbManterConectado;
    private Button btLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etSenha = (EditText) findViewById(R.id.etSenha);
        cbManterConectado = (CheckBox) findViewById(R.id.cbManterConectado);
        btLogar = (Button) findViewById(R.id.btLogar);

        if(isConectado())
            iniciarApp();

        //Método que será chamado no onclick do botao
        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isLoginValido()){
                    if(cbManterConectado.isChecked()){
                        manterConectado();

                    }
                    iniciarApp();
                }else {
                    Toast.makeText(getApplicationContext(), "Usuario ou senha invalida", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
    // Valida o login
    private boolean isLoginValido() {
        String login = etLogin.getText().toString();
        String senha = etSenha.getText().toString();
        if(login.equals(LOGIN_DEFAULT)
                && senha.equals(SENHA_DEFAULT)) {
            return true;
        } else
            return false;
    }

    private void manterConectado(){
        String login = etLogin.getText().toString();
        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }

    private boolean isConectado() {
        SharedPreferences shared = getSharedPreferences(KEY_APP_PREFERENCES,MODE_PRIVATE);
        String login = shared.getString(KEY_LOGIN, "");
        if(login.equals(""))
            return false;
        else
            return true;
    }

    private void iniciarApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}

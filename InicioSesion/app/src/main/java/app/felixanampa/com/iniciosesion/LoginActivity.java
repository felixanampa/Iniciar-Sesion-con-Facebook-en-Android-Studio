package app.felixanampa.com.iniciosesion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);

        //↓ para detectar las acciones o eventos del boton
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goMainScreen();
                //primer evento de Ingreso exitoso
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
                //segundo evento de proceso cancelado el TOAST nos permitira mostrar un mensaje
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
                //tercer evento mostrar un mensaje cuando ocurra algún error
            }
        });
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //este codigo permite abrir la activity cuando el inicio de sesión sea exitoso
        //los FLAGS permiten que la pantalla principal sea la única activity en ejecución
        //esto se hace para evitar el problema que cuando este en mi pantalla principal
        //y dar al boton de atrás se regrese a la pantalla de iniciar sesion y simplemente salga del programa
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //en este código llegaran las acciones y se redirigirá al callbackmanager de arriba
    }
}

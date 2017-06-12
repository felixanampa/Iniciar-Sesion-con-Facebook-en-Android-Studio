package app.felixanampa.com.iniciosesion;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView idTextView;

    private ProfileTracker profileTracker;
    //↑es así como se gestiona como variable global

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoImageView = (ImageView) findViewById(R.id.photoImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        idTextView = (TextView) findViewById(R.id.idTextView);

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged (Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    displayProfileInfo(currentProfile);
                    //En el caso de que el profile sea null hay que solicitar que se encuentre el perfil actual
                }
            }
        };



        //↓ el AccessToken nos permite saber si la sesion de facebook esta iniciada
            // si esta nulo esto quiere decir que no tenemos el inicio de sesion activada
        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
            //y esto nos manda a la pantalla del activity Login
        } else {
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                displayProfileInfo(profile);
            } else {
                Profile.fetchProfileForCurrentAccessToken();
            }
            //este código es en el caso de que getCurrentAccessToken() no sea null
            // y obtenemos el Profile actual
        }
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //esto inicia el LoginActivity
    }

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
        //este código contiene el evento del boton para cerrar sesión
    }

    private void displayProfileInfo(Profile profile) {
        String id = profile.getId();
        String name = profile.getName();
        String photoUrl = profile.getProfilePictureUri(100, 100).toString();

        nameTextView.setText(name);
        idTextView.setText(id);

        Glide.with(getApplicationContext())
                .load(photoUrl)
                .into(photoImageView);
        //con este código podemos obtener el id, name, la URL de la foto de perfil
        //Para descargar la foto desde la URL a un ImageView usamos Glide(que se compiló en el build.gradle)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
        // esto crea el ProfileTracker
    }
}

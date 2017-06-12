package app.felixanampa.com.iniciosesion;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Felix on 04/06/2017.
 */

public class InicioSesionApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        AppEventsLogger.activateApp(this);
        //↑ inicialización del SDK de facebook
        //en el AndroidManifest en la parte de application se debe colocar el name de esta application
        //en este caso android:name=".InicioSesionApp"
        //esto permite que se cargue el SDK de Facebook mediante este archivo JAVA
    }
}

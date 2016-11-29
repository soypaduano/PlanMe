package itesm.mx.planme;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CambiarPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    /* Cambiar password  */

    TextView tv_cambiarPasswordTitle;
    TextView tv_NewPassword;
    TextView tv_ConfirmPassword;

    EditText et_newPassword;
    EditText et_confirmNewPassword;
    Button btn_confirmChangePassword;

    private String password;
    private String confirmarPassword;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public static final String TAG = "SignIn";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);

        et_newPassword = (EditText) findViewById(R.id.et_newPassword);
        et_confirmNewPassword = (EditText) findViewById(R.id.et_confirmPassword);

        btn_confirmChangePassword = (Button) findViewById(R.id.btn_confirmChangePassword);
        btn_confirmChangePassword.setOnClickListener(this);

        tv_cambiarPasswordTitle = (TextView) findViewById(R.id.TextViewChangePassword);
        tv_NewPassword = (TextView) findViewById(R.id.textViewNewPassword);
        tv_ConfirmPassword = (TextView) findViewById(R.id.TextViewConfirmPassword);


        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Railway.otf");
        btn_confirmChangePassword.setTypeface(type);
        tv_ConfirmPassword.setTypeface(type);
        tv_cambiarPasswordTitle.setTypeface(type);
        tv_NewPassword.setTypeface(type);

        mAuth = FirebaseAuth.getInstance();

        /* Metodo donde hacemos la autorizacion de Firebase, comprobamos si hay algun usuario actual unido */

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    user.updatePassword(confirmarPassword);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /* Cuando el usuario cambia la password comprobamos que ambas son iguales */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirmChangePassword:

                password = String.valueOf(et_newPassword.getText());
                confirmarPassword = String.valueOf(et_confirmNewPassword.getText());

                /* Comprobamos si los campos no estan vacios */
                if (password.equals("") || confirmarPassword.equals("")) {

                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();

                    /* Comprobamos que las contrasenas coinciden */
                } else if (!password.equals(confirmarPassword)) {
                    Toast.makeText(getApplicationContext(), "Las contrasenas deben coincidir", Toast.LENGTH_SHORT).show();
                } else {

                    /* Aqui tiene que ir el codigo de cambiar la contrasena del usuario*/


                    mAuth.addAuthStateListener(mAuthListener);


                    Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                    finish();


                }


                break;
        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CambiarPassword Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

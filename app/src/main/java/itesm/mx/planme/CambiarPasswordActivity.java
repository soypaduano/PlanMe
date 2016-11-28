package itesm.mx.planme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CambiarPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    /* Cambiar password  */


    EditText et_actualPassword;
    EditText et_newPassword;
    EditText et_confirmNewPassword;
    Button btn_confirmChangePassword;

    private String password;
    private String confirmarPassword;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public static final String TAG = "SignIn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);

        et_actualPassword = (EditText) findViewById(R.id.et_actualPassword);
        et_newPassword = (EditText) findViewById(R.id.et_newPassword);
        et_confirmNewPassword = (EditText) findViewById(R.id.et_confirmNewPassword);
        btn_confirmChangePassword= (Button) findViewById(R.id.btn_confirmChangePassword);


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

    }

    /* Cuando el usuario cambia la password comprobamos que ambas son iguales */

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirmChangePassword:

                password = String.valueOf(et_newPassword.getText());
                 confirmarPassword = String.valueOf(et_confirmNewPassword.getText());

                /* Comprobamos si los campos no estan vacios */
                if(password.equals("")  || confirmarPassword.equals("")){

                    Toast.makeText(getApplicationContext(),"Los campos no pueden estar vacios",Toast.LENGTH_SHORT).show();

                    /* Comprobamos que las contrasenas coinciden */
            } else if (!password.equals(confirmarPassword)){
                    Toast.makeText(getApplicationContext(),"Las contrasenas deben coincidir",Toast.LENGTH_SHORT).show();
                } else  {

                    /* Aqui tiene que ir el codigo de cambiar la contrasena del usuario*/


                    mAuth.addAuthStateListener(mAuthListener);


                    Toast.makeText(getApplicationContext(),"Password Updated",Toast.LENGTH_SHORT).show();
                    finish();


            }


                break;
        }

    }
}

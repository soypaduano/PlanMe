package itesm.mx.planme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /* Pagina del Log In, aqui el usuario introduce su email y su password para poder
     * acceder al sistema. */



    private Button btn_login;
    private EditText et_email;
    private EditText et_passwd;
    private TextView tv_forgotPass;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public static final String TAG = "SignIn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        et_passwd = (EditText) findViewById(R.id.et_passwd);
        et_email = (EditText) findViewById(R.id.et_email);
        tv_forgotPass = (TextView) findViewById(R.id.tv_forgotPass);
        btn_login.setOnClickListener(this);
        tv_forgotPass.setOnClickListener(this);

        /* Instancia de Firebase, donde podemos trabajar la autorizacion de los usuarios */

        mAuth = FirebaseAuth.getInstance();


        /* Metodo donde hacemos la autorizacion de Firebase, comprobamos si hay algun usuario actual unido */

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_login:

                /* Obtenemos los datos introducidos por el usuario */

                String correo;
                String password;
                correo = String.valueOf(et_email.getText());
                password = String.valueOf(et_passwd.getText());

                /* Hacemos comprobaciones de si esta vacio o no */

                if((correo.isEmpty())!=true && (password.isEmpty())!=true){
                    mAuth.signInWithEmailAndPassword(correo, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    /* Vemos si el usuario esta comprobado */

                                    if (task.isSuccessful()) {
                                        /* Si esta, abrimos buscar ofrecer, y aparte, le mandamos el UID del usuario que se registro */
                                        Intent myIntento = new Intent(LoginActivity.this, BuscarOfrecerActivity.class);
                                        myIntento.putExtra("uid",mAuth.getCurrentUser().getUid());
                                        startActivity(myIntento);
                                        finish();
                                    }

                                    else {
                                        /* Si el usuario esta mal, mandamos un toast avisando que hay un error. */
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                        Toast.makeText(getApplicationContext(), "SignIn Failed, try again",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                    }
                break;

            case R.id.tv_forgotPass:

                Intent myIntent = new Intent(this, RecuperarPasswordActivity.class);
                startActivity(myIntent);


        }

    }

    /* Llamamos al metodo de comprobar si hay algun usuario unido */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /* Quitamos el metodo de comprobar si hay algun usuario unido */

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

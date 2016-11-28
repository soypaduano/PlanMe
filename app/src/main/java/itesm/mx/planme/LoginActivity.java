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

        mAuth = FirebaseAuth.getInstance();

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
                String correo;
                String password;
                correo = String.valueOf(et_email.getText());
                password = String.valueOf(et_passwd.getText());
                if((correo.isEmpty())!=true && (password.isEmpty())!=true){
                    mAuth.signInWithEmailAndPassword(correo, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (task.isSuccessful()) {
                                        Intent myIntento = new Intent(LoginActivity.this, BuscarOfrecerActivity.class);
                                        myIntento.putExtra("uid",mAuth.getCurrentUser().getUid());
                                        startActivity(myIntento);
                                        finish();
                                    }
                                    else {
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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

package itesm.mx.planme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_nombre;
    private EditText et_apellido;
    private EditText et_username;
    private EditText et_correo;
    private EditText et_telefono;
    private EditText et_passwd;
    private EditText et_passwd2;

    private Spinner sp_dia;
    private Spinner sp_mes;
    private Spinner sp_año;

    private Button btn_registrar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public static final String TAG = "SignUp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_apellido = (EditText) findViewById(R.id.et_apellido);
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_username = (EditText) findViewById(R.id.et_username);
        et_correo = (EditText) findViewById(R.id.et_correo);
        et_telefono = (EditText) findViewById(R.id.et_telefono);
        et_passwd = (EditText) findViewById(R.id.et_password);
        et_passwd2 = (EditText) findViewById(R.id.et_password2);

        sp_año = (Spinner) findViewById(R.id.sp_año);
        sp_dia = (Spinner) findViewById(R.id.sp_dia);
        sp_mes = (Spinner) findViewById(R.id.sp_mes);

        btn_registrar = (Button) findViewById(R.id.btn_registrate);
        btn_registrar.setOnClickListener(this);

        initSpinners();

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

    public void initSpinners(){
        ArrayList<String> years = new ArrayList<String>();
        Calendar myCalendar = Calendar.getInstance();
        int thisYear = myCalendar.get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapterYears = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        sp_año.setAdapter(adapterYears);


        String[] months =  getResources().getStringArray(R.array.array_months);
        ArrayAdapter<String> adapterMonths = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        sp_mes.setAdapter(adapterMonths);


        ArrayList<String> days = new ArrayList<String>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        sp_dia.setAdapter(adapterDays);

    }

    @Override
    public void onClick(View view) {

        String password1;
        String password2;
        String correo;

        password1 = String.valueOf(et_passwd.getText());
        password2 = String.valueOf(et_passwd2.getText());
        correo = String.valueOf(et_correo.getText());

        if(isValidEmailAddress(correo)==true && (password1.equals(password2))==true){
            mAuth.createUserWithEmailAndPassword(correo, password1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Signup failed, try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent myIntent = new Intent(SignupActivity.this, BuscarOfrecerActivity.class);
                                startActivity(myIntent);
                                finish();
                            }
                        }
                    });
            }
    }

    public boolean isValidEmailAddress(String correo) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(correo);
        return m.matches();
    }
}
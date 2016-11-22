package itesm.mx.planme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_nombre;
    private EditText et_apellido;
    private EditText et_correo;
    private EditText et_telefono;
    private EditText et_passwd;
    private EditText et_passwd2;
    private TextView tv_birthday;
    private Button btn_setdate;

    private int year;
    private int month;
    private int day;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    static final int DATE_DIALOG_ID = 1;

    private Button btn_registrar;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;

    public static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_apellido = (EditText) findViewById(R.id.et_apellido);
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_correo = (EditText) findViewById(R.id.et_correo);
        et_telefono = (EditText) findViewById(R.id.et_telefono);
        et_passwd = (EditText) findViewById(R.id.et_password);
        et_passwd2 = (EditText) findViewById(R.id.et_password2);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);
        btn_setdate = (Button) findViewById(R.id.button_setdate);
        btn_setdate.setOnClickListener(this);

        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);

        btn_registrar = (Button) findViewById(R.id.btn_registrate);
        btn_registrar.setOnClickListener(this);

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int pyear, int pmonth, int pday) {
                year = pyear;
                month = pmonth;
                day = pday;
                updateDisplayDate();
            }
        };

    }

    private void updateDisplayDate() {
        tv_birthday.setText(new StringBuilder().append(pad(day)).append("/").append(pad(month + 1)).append("/").append(pad(year)));
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
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


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_setdate:
                showDialog(DATE_DIALOG_ID);
                break;

            case R.id.btn_registrate:
                String password1;
                String password2;
                String correo;

                password1 = String.valueOf(et_passwd.getText());
                password2 = String.valueOf(et_passwd2.getText());
                correo = String.valueOf(et_correo.getText());

                if (isValidEmailAddress(correo) == true && (password1.equals(password2)) == true) {
                    if(password1.length()>=6){
                        mAuth.createUserWithEmailAndPassword(correo, password1)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            toastmsg("Signup failed, try again");
                                        } else {
                                            int selectedId = radioSexGroup.getCheckedRadioButtonId();
                                            radioSexButton = (RadioButton) findViewById(selectedId);
                                            writeNewUser(mAuth.getCurrentUser().getUid(),
                                                    et_nombre.getText().toString(),
                                                    et_apellido.getText().toString(),
                                                    (tv_birthday.getText().toString()),
                                                    et_correo.getText().toString(),
                                                    et_telefono.getText().toString(),
                                                    radioSexButton.getText().toString()
                                            );
                                            Intent myIntent = new Intent(SignupActivity.this, BuscarOfrecerActivity.class);
                                            myIntent.putExtra("uid", mAuth.getCurrentUser().getUid());
                                            startActivity(myIntent);
                                            finish();
                                        }
                                    }
                                });
                    }
                    else
                        toastmsg("Password must have at least 6 characters");
                } else
                    toastmsg("Email or/and Password invalid");
        }
    }

    public boolean isValidEmailAddress(String correo) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(correo);
        return m.matches();
    }

    private void writeNewUser(String uid, String nombre, String apellido, String fechanacimiento, String correo, String numero, String sexo) {
        Usuario user = new Usuario(uid, nombre, apellido, fechanacimiento, correo, numero, sexo);
        mDatabase.child("users").child(uid).setValue(user);
        toastmsg("User added to the DB");
    }

    public void toastmsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, year, month, day);
        }
        return null;
    }
}
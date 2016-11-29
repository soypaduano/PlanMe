package itesm.mx.planme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    //Atributos vistas
    private EditText et_name;
    private EditText et_surname;
    private EditText et_email;
    private EditText et_phonenumber;
    private EditText et_passwd;
    private EditText et_passwd2;

    private TextView tv_birthday;

    private Button btn_setdate;
    private Button btn_register;
    private Button btn_photo;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    private ImageView img_photo;

    //Atributos
    private Bitmap bitmap;
    private String encodedImage;

    private byte[] byteArray;

    private int year;
    private int month;
    private int day;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    //Constantes
    static final int DATE_DIALOG_ID = 1;
    private static final int REQUEST_CODE = 1;
    public static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_surname = (EditText) findViewById(R.id.et_surname);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phonenumber = (EditText) findViewById(R.id.et_phonenumber);
        et_passwd = (EditText) findViewById(R.id.et_password);
        et_passwd2 = (EditText) findViewById(R.id.et_password2);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);
        btn_setdate = (Button) findViewById(R.id.button_setdate);
        btn_setdate.setOnClickListener(this);
        img_photo = (ImageView)findViewById(R.id.imageView_photo);

        btn_photo = (Button)findViewById(R.id.btn_photo);
        btn_photo.setOnClickListener(this);

        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);

        btn_register = (Button) findViewById(R.id.btn_registrate);
        btn_register.setOnClickListener(this);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Railway.otf");

        et_surname.setTypeface(type);
        et_name.setTypeface(type);
        et_email.setTypeface(type);
        et_phonenumber.setTypeface(type);
        btn_setdate.setTypeface(type);
        btn_photo.setTypeface(type);
        btn_register.setTypeface(type);


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

            case R.id.btn_photo:
                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, REQUEST_CODE);
                }*/
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
                startActivityForResult(chooseImageIntent, REQUEST_CODE);
                break;

            case R.id.btn_registrate:
                String password1;
                String password2;
                String email;

                password1 = String.valueOf(et_passwd.getText());
                password2 = String.valueOf(et_passwd2.getText());
                email = String.valueOf(et_email.getText());

                if (isValidEmailAddress(email) == true && (password1.equals(password2)) == true) {
                    if(password1.length()>=6){
                        mAuth.createUserWithEmailAndPassword(email, password1)
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
                                            String uid = mAuth.getCurrentUser().getUid();
                                            String name = et_name.getText().toString();
                                            String surname = et_surname.getText().toString();
                                            String birthday = tv_birthday.getText().toString();
                                            String email = et_email.getText().toString();
                                            String phonenumber = et_phonenumber.getText().toString();
                                            String sex = radioSexButton.getText().toString();
                                            if(!uid.equals("") && !name.equals("") && !surname.equals("")
                                            && !birthday.equals("") && !email.equals("") && !phonenumber.equals("") && !sex.equals("")
                                                    && byteArray!=null) {
                                                encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                                                writeNewUser(uid, name, surname, birthday, email, phonenumber, sex, encodedImage);
                                                Intent myIntent = new Intent(SignupActivity.this, BuscarOfrecerActivity.class);
                                                myIntent.putExtra("uid", mAuth.getCurrentUser().getUid());
                                                startActivity(myIntent);
                                                finish();
                                            }
                                            else
                                                toastmsg("Missing values!!");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

                if(requestCode== REQUEST_CODE && resultCode==RESULT_OK){
                    bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    //bitmap = (Bitmap)data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                    img_photo.setImageBitmap(bitmap);
                }
    }

    /* Metodo que verifica que el email que digitó el usuario sea valido*/
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /* Metodo que crea un nuevo usuario en la base de datos */
    private void writeNewUser(String uid, String name, String surname, String fechanacimiento, String email, String numero, String sexo, String byteArray) {
        Usuario user = new Usuario(uid, name, surname, fechanacimiento, email, numero, sexo, byteArray);
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
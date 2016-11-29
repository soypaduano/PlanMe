package itesm.mx.planme;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecuperarPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    //Atributos vistas
    private TextView tv_tuEmail;
    private Button btn_getPass;
    private EditText et_email;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);

        btn_getPass = (Button) findViewById(R.id.btn_login);
        et_email = (EditText) findViewById(R.id.et_email);
        tv_tuEmail = (TextView) findViewById(R.id.textViewYourEmail);
        btn_getPass.setOnClickListener(this);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Railway.otf");
        et_email.setTypeface(type);
        tv_tuEmail.setTypeface(type);
        btn_getPass.setTypeface(type);


        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {

        String email = et_email.getText().toString();

        mAuth.sendPasswordResetEmail(email);
        //TO-DO enviar correo para recuperarPassword

        Toast.makeText(getApplicationContext(), R.string.toastMsgGetPass,Toast.LENGTH_LONG).show();
        finish();
    }

    public void prova(){

    }
}

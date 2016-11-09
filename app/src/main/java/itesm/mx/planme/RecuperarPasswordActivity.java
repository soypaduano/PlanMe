package itesm.mx.planme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecuperarPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_getPass;
    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);

        btn_getPass = (Button) findViewById(R.id.btn_login);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_getPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String email = et_email.getText().toString();



        Toast.makeText(getApplicationContext(), R.string.toastMsgGetPass,Toast.LENGTH_LONG).show();
        finish();
    }

    public void prova(){

    }
}

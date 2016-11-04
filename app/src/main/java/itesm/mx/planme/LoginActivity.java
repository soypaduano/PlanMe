package itesm.mx.planme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login;
    private EditText et_username;
    private EditText et_passwd;
    private TextView tv_forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        et_passwd = (EditText) findViewById(R.id.et_passwd);
        et_username = (EditText) findViewById(R.id.et_email);
        tv_forgotPass = (TextView) findViewById(R.id.tv_forgotPass);
        btn_login.setOnClickListener(this);
        tv_forgotPass.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_login:
                Intent myIntento =  new Intent(this, BuscarOfrecerActivity.class);
                startActivity(myIntento);
                break;

            case R.id.tv_forgotPass:

                Intent myIntent = new Intent(this, RecuperarPasswordActivity.class);
                startActivity(myIntent);


        }

    }

    public void prova(){

    }
}

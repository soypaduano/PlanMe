package itesm.mx.planme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CambiarPasswordActivity extends AppCompatActivity implements View.OnClickListener {


    EditText et_actualPassword;
    EditText et_newPassword;
    EditText et_confirmNewPassword;
    Button btn_confirmChangePassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);

        et_actualPassword = (EditText) findViewById(R.id.et_actualPassword);
        et_newPassword = (EditText) findViewById(R.id.et_newPassword);
        et_confirmNewPassword = (EditText) findViewById(R.id.et_confirmNewPassword);
        btn_confirmChangePassword= (Button) findViewById(R.id.btn_confirmChangePassword);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirmChangePassword:
                Toast.makeText(getApplicationContext(),"Password Updated",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

    }
}

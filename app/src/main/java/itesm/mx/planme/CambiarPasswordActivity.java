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
    EditText et_nuevaPassword;
    EditText et_confirmarNuevaPassword;
    Button btn_confirmarCambioPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);

        et_actualPassword = (EditText) findViewById(R.id.et_actualPassword);
        et_nuevaPassword = (EditText) findViewById(R.id.et_nuevaPassword);
        et_confirmarNuevaPassword = (EditText) findViewById(R.id.et_confirmarNuevaPassword);
        btn_confirmarCambioPassword= (Button) findViewById(R.id.btn_confirmarCambioPassword);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirmarCambioPassword:
                Toast.makeText(getApplicationContext(),"Se actualizo contraseña",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

    }
}

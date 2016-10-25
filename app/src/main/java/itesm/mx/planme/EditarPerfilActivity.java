package itesm.mx.planme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarPerfilActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_editarNombre;
    EditText et_editarApellido;
    EditText et_editarUsername;
    EditText et_editarTelefono;
    EditText et_editarDescripcion;
    Button btn_actualizarFoto;
    Button btn_actualizarPassword;
    Button btn_guardarCambios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        et_editarNombre = (EditText) findViewById(R.id.et_editarNombre);
        et_editarApellido = (EditText) findViewById(R.id.et_editarApellido);
        et_editarUsername = (EditText) findViewById(R.id.et_editarUsername);
        et_editarTelefono = (EditText) findViewById(R.id.et_editarTelefono);
        et_editarDescripcion = (EditText) findViewById(R.id.et_editarDescripcion);

        btn_actualizarFoto = (Button) findViewById(R.id.btn_actualizarFoto);
        btn_actualizarPassword = (Button) findViewById(R.id.btn_actualizarPassword);
        btn_guardarCambios = (Button) findViewById(R.id.btn_guardarCambios);

        btn_actualizarFoto.setOnClickListener(this);
        btn_actualizarPassword.setOnClickListener(this);
        btn_guardarCambios.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_guardarCambios:
                finish();
                break;

            case R.id.btn_actualizarFoto:
                Toast.makeText(getApplicationContext(),"Se tomara una foto al usuario",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_actualizarPassword:
                Intent myIntento = new Intent(this, CambiarPasswordActivity.class);
                startActivity(myIntento);
                break;
        }
    }
}

package itesm.mx.planme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditarPerfilActivity extends AppCompatActivity {

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



    }
}

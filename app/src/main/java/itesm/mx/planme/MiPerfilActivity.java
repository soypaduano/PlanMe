package itesm.mx.planme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MiPerfilActivity extends AppCompatActivity {

    Button btn_editarPerfil;
    ImageView iv_miFoto;
    TextView tv_miDescripcion;
    TextView tv_miEdad;
    TextView tv_miNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        btn_editarPerfil = (Button) findViewById(R.id.btn_editarPerfil);
        tv_miNombre = (TextView) findViewById(R.id.tv_miNombre);
        tv_miEdad = (TextView) findViewById(R.id.tv_miEdad);
        tv_miDescripcion = (TextView) findViewById(R.id.tv_miDescripcion);
        iv_miFoto = (ImageView) findViewById(R.id.iv_miFoto);


    }
}

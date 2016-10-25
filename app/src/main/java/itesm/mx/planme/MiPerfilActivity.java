package itesm.mx.planme;

import android.content.Intent;
import android.os.IInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MiPerfilActivity extends AppCompatActivity implements View.OnClickListener {

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
        btn_editarPerfil.setOnClickListener(this);
        tv_miNombre = (TextView) findViewById(R.id.tv_miNombre);
        tv_miEdad = (TextView) findViewById(R.id.tv_miEdad);
        tv_miDescripcion = (TextView) findViewById(R.id.tv_miDescripcion);
        iv_miFoto = (ImageView) findViewById(R.id.iv_miFoto);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_editarPerfil:
                Intent myIntent = new Intent(this, EditarPerfilActivity.class);
                startActivity(myIntent);
                break;
        }

    }
}

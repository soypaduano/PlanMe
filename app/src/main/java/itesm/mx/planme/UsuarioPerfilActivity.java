package itesm.mx.planme;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UsuarioPerfilActivity extends AppCompatActivity {

    TextView tv_nombreUsuario;
    Button btn_llamarUsuario;
    ImageView iv_fotoUsuario;
    TextView tv_descripcionUsuario;
    TextView tv_edadUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);

        
        tv_nombreUsuario = (TextView) findViewById(R.id.tv_nombreUsuario);
        tv_descripcionUsuario = (TextView) findViewById(R.id.tv_descripcionUsuario);
        tv_edadUsuario = (TextView) findViewById(R.id.tv_edadUsuario);
        iv_fotoUsuario = (ImageView) findViewById(R.id.iv_fotoUsuario);
        btn_llamarUsuario= (Button) findViewById(R.id.btn_llamarUsuario);

    }


}

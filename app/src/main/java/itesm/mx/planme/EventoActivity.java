package itesm.mx.planme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EventoActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_llamar;
    private Button btn_unirAlPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        btn_llamar = (Button) findViewById(R.id.btn_llamar);
        btn_unirAlPlan = (Button) findViewById(R.id.btn_unirse);
        btn_llamar.setOnClickListener(this);
        btn_unirAlPlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_llamar:
                Toast.makeText(getApplicationContext(),"Llamando al creador del plan...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_unirse:
                Intent myIntent = new Intent(this,BuscarOfrecerActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
                Toast.makeText(getApplicationContext(),"Te has unido al plan", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_creador:

                Intent myIntento = new Intent(this, UsuarioPerfilActivity.class);
                startActivity(myIntento);
                break;
        }

    }
}

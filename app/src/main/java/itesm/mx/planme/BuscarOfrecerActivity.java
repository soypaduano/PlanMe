package itesm.mx.planme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BuscarOfrecerActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_crearPlan;
    private Button btn_buscarPlan;
    private Button btn_verPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_ofrecer);

        btn_buscarPlan = (Button) findViewById(R.id.btn_buscarPlan);
        btn_crearPlan = (Button) findViewById(R.id.btn_crearPlan);
        btn_verPerfil = (Button) findViewById(R.id.btn_verPerfil);
        btn_crearPlan.setOnClickListener(this);
        btn_buscarPlan.setOnClickListener(this);
        btn_verPerfil.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_crearPlan:

                Intent myIntent = new Intent(this, NewEventActivity.class);
                startActivity(myIntent);
                break;

            case R.id.btn_buscarPlan:

                Intent myIntento = new Intent(this, Timeline.class);
                startActivity(myIntento);
                break;

            case R.id.btn_verPerfil:

                Intent myIntento1 = new Intent(this, MiPerfilActivity.class);
                startActivity(myIntento1);
                break;
        }
    }

    public void prova(){

    }
}

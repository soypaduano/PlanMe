package itesm.mx.planme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class BuscarOfrecerActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_crearPlan;
    private Button btn_buscarPlan;
    private Button btn_verPerfil;
    private Button btn_signout;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_ofrecer);

        btn_buscarPlan = (Button) findViewById(R.id.btn_buscarPlan);
        btn_crearPlan = (Button) findViewById(R.id.btn_crearPlan);
        btn_verPerfil = (Button) findViewById(R.id.btn_verPerfil);
        btn_signout = (Button)findViewById(R.id.button_signout);
        btn_crearPlan.setOnClickListener(this);
        btn_buscarPlan.setOnClickListener(this);
        btn_verPerfil.setOnClickListener(this);
        btn_signout.setOnClickListener(this);

        Intent intent = getIntent();

        if(intent!=null){
            uid = intent.getStringExtra("uid");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_crearPlan:
                Intent myIntent = new Intent(this, NewEventActivity.class);
                myIntent.putExtra("uid", uid);
                startActivity(myIntent);
                break;

            case R.id.btn_buscarPlan:
                Intent myIntento = new Intent(this, Timeline.class);
                myIntento.putExtra("uid", uid);
                startActivity(myIntento);
                break;

            case R.id.btn_verPerfil:
                Intent myIntento1 = new Intent(this, MiPerfilActivity.class);
                startActivity(myIntento1);
                break;

            case R.id.button_signout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Signed Out",
                        Toast.LENGTH_SHORT).show();
                finish();
        }
    }
}
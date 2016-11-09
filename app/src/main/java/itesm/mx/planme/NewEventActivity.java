package itesm.mx.planme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class NewEventActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner sp_categorias;
    private Spinner sp_horas;
    private Spinner sp_minutos;

    private Button btn_publicar;
    private Button btn_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        sp_categorias = (Spinner) findViewById(R.id.sp_categoria);
        sp_horas = (Spinner) findViewById(R.id.sp_horas);
        sp_minutos = (Spinner) findViewById(R.id.sp_minutos);
        initSpinners();

        btn_foto = (Button) findViewById(R.id.btn_foto);
        btn_publicar = ( Button) findViewById(R.id.btn_publicar);
        btn_foto.setOnClickListener(this);
        btn_publicar.setOnClickListener(this);

    }

    public void initSpinners(){

        String[] categories =  getResources().getStringArray(R.array.array_categories);
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        sp_categorias.setAdapter(adapterCategories);


        ArrayList<String> hours = new ArrayList<String>();
        for (int i = 0; i <= 24; i++) {
            hours.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterHours = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hours);
        sp_horas.setAdapter(adapterHours);

        ArrayList<String> mins = new ArrayList<String>();
        for (int i = 0; i <= 60; i++) {
            mins.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterMins = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mins);
        sp_minutos.setAdapter(adapterMins);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_publicar:

                Intent myIntent = new Intent(this, Timeline.class);
                startActivity(myIntent);
                break;

            case R.id.btn_foto:
                Toast.makeText(getApplicationContext(),"Debe tomar foto o elegir de galeria", Toast.LENGTH_LONG).show();
                break;
        }

    }
}

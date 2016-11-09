package itesm.mx.planme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Timeline extends AppCompatActivity implements  View.OnClickListener{

    private ListView lv_planesActivos;
    private ListView lv_misPlanes;

    private TextView tv_planActivos;
    private TextView tv_misPlanes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lv_misPlanes = (ListView) findViewById(R.id.lv_misPlanes);
        lv_planesActivos = (ListView) findViewById(R.id.lv_planesActivos);

        tv_misPlanes = (TextView) findViewById(R.id.textView24);
        tv_planActivos = (TextView) findViewById(R.id.textView25);
        tv_planActivos.setOnClickListener(this);
        tv_misPlanes.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.textView24:

                Intent myIntent = new Intent(this, EventoActivity.class);
                startActivity(myIntent);
                break;

            case R.id.textView25:

                Intent myIntento = new Intent(this, EventoActivity.class);
                startActivity(myIntento);
                break;
        }
    }
}

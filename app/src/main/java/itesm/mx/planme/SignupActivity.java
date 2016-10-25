package itesm.mx.planme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_nombre;
    private EditText et_apellido;
    private EditText et_username;
    private EditText et_correo;
    private EditText et_telefono;
    private EditText et_passwd;
    private EditText et_passwd2;

    private Spinner sp_dia;
    private Spinner sp_mes;
    private Spinner sp_año;

    private Button btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_apellido = (EditText) findViewById(R.id.et_apellido);
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_username = (EditText) findViewById(R.id.et_username);
        et_correo = (EditText) findViewById(R.id.et_correo);
        et_telefono = (EditText) findViewById(R.id.et_telefono);
        et_passwd = (EditText) findViewById(R.id.et_passwd);
        et_passwd2 = (EditText) findViewById(R.id.et_passwd2);

        sp_año = (Spinner) findViewById(R.id.sp_año);
        sp_dia = (Spinner) findViewById(R.id.sp_dia);
        sp_mes = (Spinner) findViewById(R.id.sp_mes);

        btn_registrar = (Button) findViewById(R.id.btn_registrate);
        btn_registrar.setOnClickListener(this);

        initSpinners();


    }

    public void initSpinners(){
        ArrayList<String> years = new ArrayList<String>();
        Calendar myCalendar = Calendar.getInstance();
        int thisYear = myCalendar.get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapterYears = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        sp_año.setAdapter(adapterYears);


        String[] months =  getResources().getStringArray(R.array.array_months);
        ArrayAdapter<String> adapterMonths = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        sp_mes.setAdapter(adapterMonths);


        ArrayList<String> days = new ArrayList<String>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        sp_dia.setAdapter(adapterDays);

    }

    @Override
    public void onClick(View view) {

        Intent myIntent = new Intent(this, BuscarOfrecerActivity.class);
        startActivity(myIntent);
    }
}

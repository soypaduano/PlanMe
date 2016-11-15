package itesm.mx.planme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class NewEventActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE = 1;
    private Spinner sp_categorias;
    private Spinner sp_horas;
    private Spinner sp_minutos;

    String[] categories;


    // SEBASTIANO

    private EditText et_planname;
    private EditText et_descripcion;
    private EditText et_place;

    private Button btn_publicar;
    private Button btn_foto;

    private ImageView img_Foto;

    private Bitmap bitmap;
    private byte[] byteArray;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        sp_categorias = (Spinner) findViewById(R.id.sp_categoria);
        sp_horas = (Spinner) findViewById(R.id.sp_horas);
        sp_minutos = (Spinner) findViewById(R.id.sp_minutos);
        categories = getResources().getStringArray(R.array.array_categories);
        initSpinners();

        et_planname = (EditText)findViewById(R.id.editText_PlanName);
        et_descripcion = (EditText)findViewById(R.id.editText_description);
        et_place = (EditText)findViewById(R.id.editText_place);

        img_Foto = (ImageView)findViewById(R.id.imageView_photo);

        btn_foto = (Button) findViewById(R.id.btn_foto);
        btn_publicar = ( Button) findViewById(R.id.btn_publicar);
        btn_foto.setOnClickListener(this);
        btn_publicar.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        if(intent!=null)
            uid = intent.getStringExtra("uid");

    }


    public void initSpinners(){

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
                String planname;
                String description;
                String time;
                String place;
                String plantype;

                planname = et_planname.getText().toString();
                description = et_descripcion.getText().toString();
                time = (String.valueOf(sp_horas.getSelectedItemPosition()) + String.valueOf(sp_minutos.getSelectedItemPosition()));
                plantype = categories[sp_categorias.getSelectedItemPosition()];
                place = et_place.getText().toString();

                String encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);

                if(!(planname.equals("")) && !(description.equals("")) && !(time.equals("")) && !(plantype.equals("")) && !(place.equals("")) && byteArray!=null){
                    writeNewEvent(planname,description, time, plantype,place, encodedImage,uid);
                    Intent myIntent = new Intent(this, BuscarOfrecerActivity.class);
                    myIntent.putExtra("uid",uid);
                    startActivity(myIntent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Missing values!!",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_foto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, REQUEST_CODE);
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            img_Foto.setImageBitmap(bitmap);
        }
    }

    private void writeNewEvent(String nombre, String descripcion, String horario, String tipodeplan, String address, String byteArray, String uid) {
        Event event = new Event(nombre, descripcion, horario, tipodeplan, address, byteArray, uid);
        mDatabase.child("events").child(nombre).setValue(event);
    }
}
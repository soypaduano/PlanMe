package itesm.mx.planme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class NewEventActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE = 1;
    private Spinner sp_categorias;
    private Spinner sp_horas;
    private Spinner sp_minutos;

    private EditText et_planname;
    private EditText et_descripcion;
    private EditText et_place;

    private Button btn_publicar;
    private Button btn_foto;
    
    private ImageView img_Foto;

    private Bitmap bitmap;
    private byte[] byteArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        sp_categorias = (Spinner) findViewById(R.id.sp_categoria);
        sp_horas = (Spinner) findViewById(R.id.sp_horas);
        sp_minutos = (Spinner) findViewById(R.id.sp_minutos);
        initSpinners();

        et_planname = (EditText)findViewById(R.id.editText_PlanName);
        et_descripcion = (EditText)findViewById(R.id.editText_description);
        et_place = (EditText)findViewById(R.id.editText_place);
        
        img_Foto = (ImageView)findViewById(R.id.imageView_photo); 

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
}
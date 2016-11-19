package itesm.mx.planme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class EventoActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_llamar;
    private Button btn_unirAlPlan;
    private Event evento;
    private TextView tv_name;
    private TextView tv_description;
    private TextView tv_place;
    private TextView tv_time;
    private TextView tv_date;
    private TextView tv_creatorname;
    private ImageView img_photoEvent;
    private String creatorname = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_evento);

        btn_llamar = (Button) findViewById(R.id.btn_llamar);
        btn_unirAlPlan = (Button) findViewById(R.id.btn_unirse);

        tv_name = (TextView)findViewById(R.id.textView_nameEvent);
        tv_description = (TextView)findViewById(R.id.textView_description2);
        tv_place = (TextView)findViewById(R.id.textView_place2);
        tv_place.setOnClickListener(this);
        tv_time = (TextView)findViewById(R.id.textView_time);
        tv_date = (TextView)findViewById(R.id.textView_date);
        tv_creatorname = (TextView)findViewById(R.id.textView_creatorname);

        img_photoEvent = (ImageView)findViewById(R.id.imageView_photoEvent);

        btn_llamar.setOnClickListener(this);
        btn_unirAlPlan.setOnClickListener(this);

        Intent intent = getIntent();

        if(intent!=null){
            evento = (Event)intent.getSerializableExtra("evento");
            tv_name.setText(evento.getNombre());
            tv_description.setText(evento.getDescripcion());
            tv_place.setText(evento.getAddress());
            tv_time.setText(evento.getHorario());
            tv_date.setText(evento.getFecha());

            String encodedImage = evento.getByteArray();
            byte[] image = Base64.decode(encodedImage, Base64.DEFAULT);
            if (image != null){
                Bitmap bmimage = BitmapFactory.decodeByteArray(image, 0, image.length);
                img_photoEvent.setImageBitmap(bmimage);
            }

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Usuario user = snapshot.child(evento.getuid()).getValue(Usuario.class);
                    creatorname = user.getNombre();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("The read failed: ", databaseError.getMessage());
                }
            });
            tv_creatorname.setText(creatorname);
        }
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

            case R.id.textView_creatorname:
                Intent myIntento = new Intent(this, UsuarioPerfilActivity.class);
                startActivity(myIntento);
                break;

            case R.id.textView_place2:
                String address = tv_place.getText().toString();
                Intent intentMap = new Intent(Intent.ACTION_VIEW);
                Uri myUri = Uri.parse("geo:0,0?q=" + address);
                intentMap.setData(myUri); //lat lng or address query
                if (intentMap.resolveActivity(getPackageManager()) != null)
                    startActivity(intentMap);
                break;
        }
    }
}
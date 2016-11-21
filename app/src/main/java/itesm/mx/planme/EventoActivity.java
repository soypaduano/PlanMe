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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
    private String creatorName = "";
    private DatabaseReference mDatabase;
    private String uid;
    private String creatorNumber = "";
    private ArrayList<Usuario> listParticipants = new ArrayList<Usuario>();
    private ArrayList<String> keyUsers = new ArrayList<String>();
    private TextView tv_participants;
    private ListView lv_participants;
    private UsuarioAdapter adapterPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_evento);

        lv_participants = (ListView)findViewById(R.id.listView_participants);

        btn_llamar = (Button) findViewById(R.id.btn_llamar);
        btn_unirAlPlan = (Button) findViewById(R.id.btn_unirse);

        tv_name = (TextView)findViewById(R.id.textView_nameEvent);
        tv_description = (TextView)findViewById(R.id.textView_description2);
        tv_place = (TextView)findViewById(R.id.textView_place2);
        tv_place.setOnClickListener(this);
        tv_time = (TextView)findViewById(R.id.textView_time);
        tv_date = (TextView)findViewById(R.id.textView_date);
        tv_creatorname = (TextView)findViewById(R.id.textView_creatorname);
        tv_participants = (TextView)findViewById(R.id.textView_participants);

        img_photoEvent = (ImageView)findViewById(R.id.imageView_photoEvent);

        btn_llamar.setOnClickListener(this);
        btn_unirAlPlan.setOnClickListener(this);

        Intent intent = getIntent();

        if(intent!=null){
            evento = (Event)intent.getSerializableExtra("evento");
            uid = intent.getStringExtra("uid");
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
                    creatorName = user.getNombre();
                    tv_creatorname.setText(creatorName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("The read failed: ", databaseError.getMessage());
                }
            });
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        checkIfJoined();
        getCreatorNumber();
        findKeysUser();

        adapterPart = new UsuarioAdapter(getApplicationContext(), listParticipants);
        lv_participants.setAdapter(adapterPart);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_llamar:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(creatorNumber));
                startActivity(intent);
                break;

            case R.id.btn_unirse:
                toastmsg("te has unido al plan");
                mDatabase.child("participants").child(uid).push().setValue(evento);
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

    public void toastmsg(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }

    public void checkIfJoined() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("participants").child(uid);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Event eventoget = postSnapshot.getValue(Event.class);
                    if(checkIfEqualEvent(eventoget, evento)==true){
                        btn_unirAlPlan.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
    }

    public void getCreatorNumber(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(evento.getuid());

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                creatorNumber = "tel:" + user.getNumero();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
    }

    public void findKeysUser() {

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("participants");

        ref1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshotdad : snapshot.getChildren()) {
                    String keyUser = postSnapshotdad.getKey();
                    for (DataSnapshot postSnapshotchild : postSnapshotdad.getChildren()) {
                        Event getEvento = postSnapshotchild.getValue(Event.class);
                        if(checkIfEqualEvent(getEvento, evento)==true){
                            boolean found = false;
                            for(int i=0; i<keyUsers.size(); i++){
                                if(keyUsers.get(i).toString().equals(keyUser))
                                    found = true;
                                break;
                            }
                            if(found==false)
                                keyUsers.add(keyUser);
                        }
                    }
                }
                for(int i=0; i<keyUsers.size(); i++){
                    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(keyUsers.get(i));

                    ref2.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            Usuario user = snapshot.getValue(Usuario.class);
                            boolean found = false;
                            int i=0;
                            for(; i< listParticipants.size(); i++) {
                                if(checkIfExistUser(listParticipants.get(i), user)==true)
                                    found = true;
                                break;
                            }
                            if(found == false){
                                listParticipants.add(user);
                                tv_participants.setText("Participants: (" + listParticipants.size() + ")");
                                adapterPart.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("The read failed: ", databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
    }

    public boolean checkIfEqualEvent(Event getEvent, Event evento){
        boolean found = false;
        if(getEvent.getNombre().equals(evento.getNombre()) && getEvent.getFecha().equals(evento.getFecha()) && getEvent.getHorario().equals(evento.getHorario()) && getEvent.getDescripcion().equals(evento.getDescripcion()) && getEvent.getAddress().equals(evento.getAddress()) && getEvent.getTipodeplan().equals(evento.getTipodeplan()))
            found = true;
        return found;
    }

    public boolean checkIfExistUser(Usuario getUser, Usuario user){
        if(getUser.getCorreo().equals(user.getCorreo()))
            return true;
        return false;
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
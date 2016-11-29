package itesm.mx.planme;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static itesm.mx.planme.R.id.btn_login;

public class BuscarOfrecerActivity extends AppCompatActivity implements View.OnClickListener{

    //Atributos vistas
    private Button btn_createPlan;
    private Button btn_findPlan;
    private Button btn_showProfile;
    private Button btn_signout;

    //Atributos
    private String uid;
    private String name;
    private String surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_ofrecer);

        btn_findPlan = (Button) findViewById(R.id.btn_findPlan);
        btn_createPlan = (Button) findViewById(R.id.btn_createPlan);
        btn_showProfile = (Button) findViewById(R.id.btn_showProfile);
        btn_signout = (Button)findViewById(R.id.button_signout);
        btn_createPlan.setOnClickListener(this);
        btn_findPlan.setOnClickListener(this);
        btn_showProfile.setOnClickListener(this);
        btn_signout.setOnClickListener(this);


        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Railway.otf");
        btn_findPlan.setTypeface(type);
        btn_createPlan.setTypeface(type);
        btn_showProfile.setTypeface(type);
        btn_signout.setTypeface(type);
        btn_signout.setTextColor(Color.parseColor("#d42c30"));


        Intent intent = getIntent();

        if(intent!=null){
            uid = intent.getStringExtra("uid");
        }

        //toastWelcome();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_createPlan:
                Intent myIntent = new Intent(this, NewEventActivity.class);
                myIntent.putExtra("uid", uid);
                startActivity(myIntent);
                break;

            case R.id.btn_findPlan:
                Intent myIntento = new Intent(this, Timeline.class);
                myIntento.putExtra("uid", uid);
                startActivity(myIntento);
                break;

            case R.id.btn_showProfile:
                Intent myIntento1 = new Intent(this, MiPerfilActivity.class);
                myIntento1.putExtra("uid", uid);
                startActivity(myIntento1);
                break;

            case R.id.button_signout:
                FirebaseAuth.getInstance().signOut();
                toastmsg("Signed Out");
                finish();
        }
    }

    @Override
    public void onBackPressed(){
        FirebaseAuth.getInstance().signOut();
        toastmsg("Signed Out");
        finish();
    }

    //Toast que da bienvenida al usuario que se recien se loguea
    public void toastWelcome(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                name = user.getname();
                surname = user.getsurname();
                toastmsg(getString(R.string.welcome) + name + " " + surname + "!!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
    }

    public void toastmsg(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}
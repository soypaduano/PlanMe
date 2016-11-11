package itesm.mx.planme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Timeline extends AppCompatActivity implements  View.OnClickListener{

    private ListView lv_planesActivos;
    private ListView lv_misPlanes;

    private TextView tv_planActivos;
    private TextView tv_misPlanes;

    private String uid;

    private EventAdapter adapter;
    private ArrayList<Event> listAllEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lv_misPlanes = (ListView) findViewById(R.id.lv_misPlanes);
        lv_planesActivos = (ListView) findViewById(R.id.lv_planesActivos);

        tv_misPlanes = (TextView) findViewById(R.id.textView24);
        tv_planActivos = (TextView) findViewById(R.id.textView25);

        Intent intent = getIntent();
        if(intent!=null){
            uid = intent.getStringExtra("uid");
        }

        listAllEvents = showAllEvents();

        adapter = new EventAdapter(getApplicationContext(), listAllEvents);
        lv_planesActivos.setAdapter(adapter);

    }

    public ArrayList<Event> showAllEvents() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Map<String, Event> td = (HashMap<String, Event>) postSnapshot.getValue();
                    listAllEvents.add((Event) td.values());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });

        return listAllEvents;
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
package itesm.mx.planme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Timeline extends AppCompatActivity implements  View.OnClickListener, AdapterView.OnItemClickListener{

    private ListView lv_planesActivos;
    private ListView lv_misPlanes;

    private String uid;

    private EventAdapter adapterAllEvents, adapterMyEvents;
    private ArrayList<Event> listAllEvents = new ArrayList<Event>();
    private ArrayList<Event> listMyEvents = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lv_misPlanes = (ListView) findViewById(R.id.lv_misPlanes);
        lv_planesActivos = (ListView) findViewById(R.id.lv_planesActivos);

        Intent intent = getIntent();
        if(intent!=null){
            uid = intent.getStringExtra("uid");
        }

        listAllEvents = showAllEvents();
        listMyEvents = showMyEvents();

        adapterAllEvents = new EventAdapter(getApplicationContext(), listAllEvents);
        lv_planesActivos.setAdapter(adapterAllEvents);
        lv_planesActivos.setOnItemClickListener(this);

        adapterMyEvents = new EventAdapter(getApplicationContext(), listMyEvents);
        lv_misPlanes.setAdapter(adapterMyEvents);
        lv_misPlanes.setOnItemClickListener(this);

        registerForContextMenu(lv_misPlanes);

    }

    public ArrayList<Event> showAllEvents() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Event evento = postSnapshot.getValue(Event.class);
                    listAllEvents.add(evento);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });

        return listAllEvents;
    }

    public ArrayList<Event> showMyEvents() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Event evento = postSnapshot.getValue(Event.class);
                    if(evento.getuid().equals(uid)) {
                        listMyEvents.add(evento);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
        return listMyEvents;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int id = item.getItemId();

        Event evento = listMyEvents.get(info.position);

        switch (id) {

            case R.id.delete:
                FirebaseDatabase.getInstance().getReference("events").orderByChild("nombre").equalTo(evento.getNombre()).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                    String key = postSnapshot.getKey();
                                    DatabaseReference ref = postSnapshot.getRef();
                                    Toast.makeText(getApplicationContext(),key, Toast.LENGTH_LONG).show();
                                    ref.setValue(null);
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                            }
                        });
                listMyEvents.remove(evento);
                adapterMyEvents.notifyDataSetChanged();
                //Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                break;

            case R.id.back:
                Toast.makeText(getApplicationContext(), "Back", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event evento = (Event)parent.getItemAtPosition(position);
        Intent intent =  new Intent(this, EventoActivity.class);
        intent.putExtra("evento", evento);
        //intent.putExtra("position", position);
        startActivity(intent);
    }
}
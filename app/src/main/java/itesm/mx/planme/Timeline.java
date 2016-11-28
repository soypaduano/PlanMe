package itesm.mx.planme;

import android.content.Intent;
import android.os.Bundle;
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

    private ListView lv_activeEvents;
    private ListView lv_myEvents;

    private String uid;

    private EventAdapter adapterAllEvents, adapterMyEvents;
    private ArrayList<Event> listAllEvents = new ArrayList<Event>();
    private ArrayList<Event> listMyEvents = new ArrayList<Event>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lv_myEvents = (ListView) findViewById(R.id.lv_myEvents);
        lv_activeEvents = (ListView) findViewById(R.id.lv_activeEvents);

        Intent intent = getIntent();
        if(intent!=null){
            uid = intent.getStringExtra("uid");
        }

        createListAllEvents();
        createListMyEvents();

        adapterAllEvents = new EventAdapter(getApplicationContext(), listAllEvents);
        lv_activeEvents.setAdapter(adapterAllEvents);
        lv_activeEvents.setOnItemClickListener(this);

        adapterMyEvents = new EventAdapter(getApplicationContext(), listMyEvents);
        lv_myEvents.setAdapter(adapterMyEvents);
        lv_myEvents.setOnItemClickListener(this);

        registerForContextMenu(lv_myEvents);

    }

    public void createListAllEvents() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Event evento = postSnapshot.getValue(Event.class);
                    listAllEvents.add(evento);
                }
                adapterAllEvents.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
    }

    public void createListMyEvents() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("participants").child(uid);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Event evento = postSnapshot.getValue(Event.class);
                    boolean found = false;
                    for(int i=0; i< listMyEvents.size(); i++){
                        if(checkIfEqual(listMyEvents.get(i), evento)==true){
                            found = true;
                            break;
                        }
                    }
                    if(found==false)
                        listMyEvents.add(evento);
                }
                adapterMyEvents.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
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

            case R.id.disjoin:
                FirebaseDatabase.getInstance().getReference("participants").child(uid).orderByChild("nombre").equalTo(evento.getname()).addListenerForSingleValueEvent(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                    //String key = postSnapshot.getKey();
                                    //toastmsg(key);
                                    DatabaseReference ref = postSnapshot.getRef();
                                    ref.setValue(null);
                                }
                                adapterMyEvents.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                            }
                        });
                listMyEvents.remove(evento);
                toastmsg("Disjoined");
                break;

            case R.id.back:
                Toast.makeText(getApplicationContext(), "Back", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }


    public void toastmsg(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event evento = (Event)parent.getItemAtPosition(position);
        Intent intent =  new Intent(this, EventoActivity.class);
        intent.putExtra("evento", evento);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }

    public boolean checkIfEqual(Event eventArray, Event evento){
        boolean found = false;
        if(eventArray.getname().equals(evento.getname()) && eventArray.getdate().equals(evento.getdate()) && eventArray.gettime().equals(evento.gettime()) && eventArray.getdescription().equals(evento.getdescription()) && eventArray.getAddress().equals(evento.getAddress()) && eventArray.getplantype().equals(evento.getplantype()))
            found = true;
        return found;
    }
}
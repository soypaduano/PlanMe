package itesm.mx.planme;

import android.content.Intent;
import android.os.IInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Calendar;

public class MiPerfilActivity extends AppCompatActivity implements View.OnClickListener {
    
    private String uid;

    private TextView tv_name;
    private TextView tv_surname;
    private TextView tv_age;
    private TextView tv_phonenumber;
    private TextView tv_email;
    private Button btn_editProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        tv_age = (TextView)findViewById(R.id.textView_profileAge);
        tv_surname = (TextView)findViewById(R.id.textView_profileSurname);
        tv_name = (TextView)findViewById(R.id.textView_profileName);
        tv_phonenumber = (TextView)findViewById(R.id.textView_profilePhone);
        tv_email = (TextView)findViewById(R.id.textView_profileEmail);
        btn_editProfile = (Button)findViewById(R.id.button_editProfile);

        Intent intent = getIntent();

        if(intent!=null)
            uid = intent.getStringExtra("uid");
        
        getUser();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_editProfile:
                Intent myIntent = new Intent(this, EditarPerfilActivity.class);
                startActivity(myIntent);
                break;
        }
    }
    
    public void getUser(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                tv_name.setText("Name: " + user.getNombre());
                tv_surname.setText("Surname: " + user.getApellido());
                tv_email.setText("Email: " + user.getCorreo());
                tv_phonenumber.setText("Phone Number: " + user.getNumero());
                final Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                String bdayS = user.getFechanacimiento();
                int bdayI = Integer.parseInt(bdayS.substring(bdayS.length()-4));
                tv_age.setText("Age: " + String.valueOf(year-bdayI));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
        
    }
}

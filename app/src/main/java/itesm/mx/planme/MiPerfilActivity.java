package itesm.mx.planme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.IInterface;
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

import java.util.Calendar;

public class MiPerfilActivity extends AppCompatActivity implements View.OnClickListener {

    //Atributos
    private String uid;

    //Atributos vista
    private TextView tv_name;
    private TextView tv_surname;
    private TextView tv_age;
    private TextView tv_phonenumber;
    private TextView tv_email;
    private Button btn_editProfile;
    private ImageView img_photo;


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
        img_photo = (ImageView)findViewById(R.id.imageView_profile);

        btn_editProfile.setOnClickListener(this);


        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Railway.otf");

        tv_name.setTypeface(type);
        tv_surname.setTypeface(type);
        tv_age.setTypeface(type);
        tv_phonenumber.setTypeface(type);
        tv_email.setTypeface(type);
        btn_editProfile.setTypeface(type);


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

    /* Metodo que carga los datos del usuario logueado desde Firebase*/
    public void getUser(){

        //Aquí se referencia a la base de datos y se especifica de qué usuario se necesita la información
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                String encodedImage = user.getByteArray();
                byte[] image = Base64.decode(encodedImage, Base64.DEFAULT);

                if (image != null){
                    Bitmap bmimage = BitmapFactory.decodeByteArray(image, 0, image.length);
                    img_photo.setImageBitmap(bmimage);
                }
                tv_name.setText(getString(R.string.profilename) + user.getname());
                tv_surname.setText(getString(R.string.profilesurname) + user.getsurname());
                tv_email.setText(getString(R.string.profileemail) + user.getemail());
                tv_phonenumber.setText(getString(R.string.profilenumber) + user.getnumber());
                final Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                String bdayS = user.getbirthday();
                int bdayI = Integer.parseInt(bdayS.substring(bdayS.length()-4));
                tv_age.setText(getString(R.string.profileage) + String.valueOf(year-bdayI));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });
        
    }
}

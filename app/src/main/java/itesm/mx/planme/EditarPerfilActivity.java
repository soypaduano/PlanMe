package itesm.mx.planme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;


public class EditarPerfilActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_editName;
    EditText et_editSurname;
    EditText et_editPhoneNumber;

    Button btn_changePhoto;
    Button btn_changePassword;
    Button btn_save;

    ImageView iv_profilePhoto;

    //Atributos
    private String uid;
    private Usuario user;
    private Bitmap bitmap;
    private byte[] byteArray;
    private String encodedImage;

    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        et_editName = (EditText) findViewById(R.id.et_editName);
        et_editSurname = (EditText) findViewById(R.id.et_editSurname);
        et_editPhoneNumber = (EditText) findViewById(R.id.et_editPhoneNumber);

        btn_changePhoto = (Button) findViewById(R.id.btn_changePhoto);
        btn_changePassword = (Button) findViewById(R.id.btn_changePassword);
        btn_save = (Button) findViewById(R.id.btn_save);

        iv_profilePhoto = (ImageView) findViewById(R.id.imageView_profile);
        btn_changePhoto.setOnClickListener(this);
        btn_changePassword.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        if(getIntent()!=null)
            uid = getIntent().getStringExtra("uid");

        getUser();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                saveChanges();
                finish();
                break;

            case R.id.btn_changePhoto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, REQUEST_CODE);
                }                break;

            case R.id.btn_changePassword:
                Intent myIntento = new Intent(this, CambiarPasswordActivity.class);
                startActivity(myIntento);
                finish();
                break;
        }
    }

    private void saveChanges() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String name = et_editName.getText().toString();
        String surname = et_editSurname.getText().toString();
        String number = et_editPhoneNumber.getText().toString();
        String email = mAuth.getCurrentUser().getEmail();
        String gender = user.getSexo();
        String birthDay = user.getbirthday();

        user = new Usuario(uid, name, surname, birthDay , email, number, gender,encodedImage );
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(user);
        Toast.makeText(getApplicationContext(), "Info updated", Toast.LENGTH_SHORT).show();

    }

    public void getUser(){

        //Aquí se referencia a la base de datos y se especifica de qué usuario se necesita la información
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                user = snapshot.getValue(Usuario.class);
                et_editName.setText( user.getname());
                et_editSurname.setText( user.getsurname());
                et_editPhoneNumber.setText( user.getnumber());
                encodedImage = user.getByteArray();
                byteArray = Base64.decode(encodedImage, Base64.DEFAULT);

                if (byteArray != null){
                    Bitmap bmimage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    iv_profilePhoto.setImageBitmap(bmimage);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== REQUEST_CODE && resultCode==RESULT_OK){
            bitmap = (Bitmap)data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);

            iv_profilePhoto.setImageBitmap(bitmap);
        }
    }
}

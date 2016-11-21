package itesm.mx.planme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;


public class NewEventActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE = 1;
    private static final int MAP_CODE = 2;
    private Spinner sp_categorias;

    private TextView tv_time;
    private TextView tv_place;
    private Button btn_settime;
    private TextView tv_date;
    private Button btn_setdate;
    private int pHour;
    private int pMinute;
    private int year;
    private int month;
    private int day;
    TimePickerDialog.OnTimeSetListener mTimeSetListener;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;

    String[] categories;


    // SEBASTIANO

    private EditText et_planname;
    private EditText et_descripcion;
    private EditText et_place;

    private Button btn_publish;
    private Button btn_photo;

    private ImageView img_photo;

    private Bitmap bitmap;
    private byte[] byteArray;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private String uid;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        sp_categorias = (Spinner) findViewById(R.id.sp_categoria);
        categories = getResources().getStringArray(R.array.array_categories);
        initSpinners();

        tv_time = (TextView)findViewById(R.id.textView_time);
        et_place = (EditText) findViewById(R.id.editText_place);
        tv_place = (TextView)findViewById(R.id.textView_place);
        btn_settime = (Button)findViewById(R.id.btn_settime);
        btn_settime.setOnClickListener(this);
        tv_date = (TextView)findViewById(R.id.textView_date);
        btn_setdate = (Button)findViewById(R.id.btn_setdate);
        btn_setdate.setOnClickListener(this);
        tv_place.setOnClickListener(this);

        et_planname = (EditText)findViewById(R.id.editText_PlanName);
        et_descripcion = (EditText)findViewById(R.id.editText_description);

        img_photo = (ImageView)findViewById(R.id.imageView_photo);

        btn_photo = (Button)findViewById(R.id.btn_photo);
        btn_publish = (Button)findViewById(R.id.btn_publish);
        btn_photo.setOnClickListener(this);
        btn_publish.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        if(intent!=null)
            uid = intent.getStringExtra("uid");

        final Calendar cal = Calendar.getInstance();
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMinute = cal.get(Calendar.MINUTE);

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                pHour = cal.get(Calendar.HOUR_OF_DAY);
                pMinute = cal.get(Calendar.MINUTE);
                if(hourOfDay>=pHour && minute>=pMinute){
                    pHour = hourOfDay;
                    pMinute = minute;
                    updateDisplayTime();
                }
                else{
                    toastmsg("Time Invalid");
                }
            }
        };

        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker view, int pyear, int pmonth, int pday){
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
                if(pyear>=year && pmonth>=month && pday>=day){
                    year = pyear;
                    month = pmonth;
                    day = pday;
                    updateDisplayDate();
                }
                else
                    toastmsg("Date Invalid");
            }
        };
    }

    private void updateDisplayTime() {
        tv_time.setText(new StringBuilder().append(pad(pHour)).append(":").append(pad(pMinute)));
    }

    private void updateDisplayDate() {
        tv_date.setText(new StringBuilder().append(pad(day)).append("/").append(pad(month + 1)).append("/").append(pad(year)));
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public void initSpinners(){

        ArrayAdapter<String> adapterCategories = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        sp_categorias.setAdapter(adapterCategories);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_publish:
                String planname;
                String description;
                String time;
                String place;
                String plantype;
                String date;

                planname = et_planname.getText().toString();
                description = et_descripcion.getText().toString();
                time = tv_time.getText().toString();
                date = tv_date.getText().toString();
                plantype = categories[sp_categorias.getSelectedItemPosition()];
                place = et_place.getText().toString();

                String encodedImage = "";

                if(byteArray!=null) {
                    encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                }

                if(!(planname.equals("")) && !(description.equals("")) && !(time.equals("")) && !(date.equals("")) && !(plantype.equals("")) && !(place.equals("")) && byteArray!=null){
                    writeNewEvent(planname,description, time, plantype, date, place, encodedImage,uid);
                    Intent myIntent = new Intent(this, BuscarOfrecerActivity.class);
                    myIntent.putExtra("uid",uid);
                    startActivity(myIntent);
                    finish();
                }
                else
                    toastmsg("Missing values!!");
                break;

            case R.id.btn_photo:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, REQUEST_CODE);
                }
                break;

            case R.id.btn_settime:
                showDialog(TIME_DIALOG_ID);
                break;

            case R.id.btn_setdate:
                showDialog(DATE_DIALOG_ID);
                break;

            case R.id.textView_place:
                String address = et_place.getText().toString();
                if(!address.equals("")){
                    Intent intentMap = new Intent(Intent.ACTION_VIEW);
                    Uri myUri = Uri.parse("geo:0,0?q=" + address);
                    intentMap.setData(myUri); //lat lng or address query
                    if (intentMap.resolveActivity(getPackageManager()) != null) {
                        startActivity(intentMap);
                    }
                }
                else
                    toastmsg("Address empty!!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE:
                if(resultCode==RESULT_OK){
                    bitmap = (Bitmap) data.getExtras().get("data");

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                    img_photo.setImageBitmap(bitmap);
                }
                break;
            case MAP_CODE:
                if(resultCode==RESULT_OK){

                }
        }

    }

    public void toastmsg(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }


    private void writeNewEvent(String nombre, String descripcion, String horario, String tipodeplan, String date, String address, String byteArray, String uid) {
        Event event = new Event(nombre, descripcion, horario, tipodeplan, date, address, byteArray, uid);
        mDatabase.child("events").push().setValue(event);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,mTimeSetListener, pHour, pMinute, false);
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,mDateSetListener, year, month, day);
        }
        return null;
    }


}
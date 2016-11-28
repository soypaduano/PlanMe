package itesm.mx.planme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;


public class NewEventActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    private static final int REQUEST_CODE = 1;
    private static final int MAP_CODE = 2;
    private Spinner sp_categories;

    private TextView tv_time;
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

    private EditText et_planname;
    private EditText et_description;

    private Button btn_publish;
    private Button btn_photo;

    private ImageView img_photo;

    private Bitmap bitmap;
    private byte[] byteArray;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private String uid;

    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private String place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        sp_categories = (Spinner) findViewById(R.id.sp_categoria);
        categories = getResources().getStringArray(R.array.array_categories);
        initSpinners();

        tv_time = (TextView)findViewById(R.id.textView_time);
        btn_settime = (Button)findViewById(R.id.btn_settime);
        btn_settime.setOnClickListener(this);
        tv_date = (TextView)findViewById(R.id.textView_date);
        btn_setdate = (Button)findViewById(R.id.btn_setdate);
        btn_setdate.setOnClickListener(this);

        et_planname = (EditText)findViewById(R.id.editText_PlanName);
        et_description = (EditText)findViewById(R.id.editText_description);

        img_photo = (ImageView)findViewById(R.id.imageView_photo);

        btn_photo = (Button)findViewById(R.id.btn_photo);
        btn_publish = (Button)findViewById(R.id.btn_publish);
        btn_photo.setOnClickListener(this);
        btn_publish.setOnClickListener(this);


        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Railway.otf");

        tv_time.setTypeface(type);
        btn_settime.setTypeface(type);
        btn_photo.setTypeface(type);
        tv_date.setTypeface(type);
        btn_setdate.setTypeface(type);
        et_planname.setTypeface(type);
        et_description.setTypeface(type);
        btn_publish.setTypeface(type);


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

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
        }
    };

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
        sp_categories.setAdapter(adapterCategories);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_publish:
                String planname;
                String description;
                String time;
                String plantype;
                String date;

                planname = et_planname.getText().toString();
                description = et_description.getText().toString();
                time = tv_time.getText().toString();
                date = tv_date.getText().toString();
                plantype = categories[sp_categories.getSelectedItemPosition()];
                place = mAutocompleteTextView.getText().toString();

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


    private void writeNewEvent(String nombre, String description, String horario, String tipodeplan, String date, String address, String byteArray, String uid) {
        Event event = new Event(nombre, description, horario, tipodeplan, date, address, byteArray, uid);
        mDatabase.child("events").push().setValue(event);
        mDatabase.child("participants").child(uid).push().setValue(event);
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


    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }
}
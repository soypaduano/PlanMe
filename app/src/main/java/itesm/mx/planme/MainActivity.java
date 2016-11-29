package itesm.mx.planme;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {


    /* Pagina inicial */



    private Button btn_login;
    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Railway.otf");
        btn_login.setTypeface(type);
        btn_signup.setTypeface(type);




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_login:

                Intent myIntent = new Intent(this,LoginActivity.class);
                startActivity(myIntent);
                break;
            case R.id.btn_signup:

                Intent myIntento = new Intent(this, SignupActivity.class);
                startActivity(myIntento);
                break;
        }

    }
}

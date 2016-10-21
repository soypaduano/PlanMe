package itesm.mx.planme;

import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {

    private Space space;
    public static final String DEBUG_TAG = "GesturesActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        space.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "La accion ha sido ABAJO");
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "La acción ha sido MOVER");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        Log.d(DEBUG_TAG, "La acción ha sido ARRIBA");
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "La accion ha sido CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG,
                                "La accion ha sido fuera del elemento de la pantalla");
                        return true;
                    default:
                        return true;
                }
            }
        });
    }
}

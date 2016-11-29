package itesm.mx.planme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class EventAdapter extends ArrayAdapter<Event>{

    private Context context;

    public EventAdapter(Context context, ArrayList<Event> eventos){
        super(context, 0, eventos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }

        TextView tv_planname = (TextView) convertView.findViewById(R.id.textView_planname);
        TextView tv_description = (TextView) convertView.findViewById(R.id.textView_description);
        TextView tv_time = (TextView) convertView.findViewById(R.id.textView_time);
        TextView tv_place = (TextView) convertView.findViewById(R.id.textView_place);
        TextView tv_plantype = (TextView)convertView.findViewById(R.id.textView_plantype);
        TextView tv_date = (TextView)convertView.findViewById(R.id.textView_dateevent);

        //
        //TextView tv_uid = (TextView)convertView.findViewById(R.id.textView_uidcreator);
        ImageView img_Foto = (ImageView) convertView.findViewById(R.id.imageView_foto);

        Event event = getItem(position);

        String name = getContext().getResources().getString(R.string.eventname) + event.getname();
        String description = getContext().getResources().getString(R.string.descriptionevent) + event.getdescription();
        String time = getContext().getResources().getString(R.string.timeevent) + event.gettime();
        String date = getContext().getResources().getString(R.string.dateevent) + event.getdate();
        String place = getContext().getResources().getString(R.string.placeevent) + event.getAddress();
        String type = getContext().getResources().getString(R.string.typeevent) + event.getplantype();

        tv_planname.setText(name);
        tv_description.setText(description);
        tv_time.setText(time);
        tv_date.setText(date);
        tv_place.setText(place);
        tv_plantype.setText(type);
        //tv_uid.setText("UID creator: " + event.getuid());

        String encodedImage = event.getByteArray();
        byte[] image = Base64.decode(encodedImage, Base64.DEFAULT);

        if (image != null){
            Bitmap bmimage = BitmapFactory.decodeByteArray(image, 0, image.length);
            img_Foto.setImageBitmap(bmimage);
        }

        return convertView;
    }
}

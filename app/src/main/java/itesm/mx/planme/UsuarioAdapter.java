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
import java.util.ArrayList;


public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    private Context context;

    public UsuarioAdapter(Context context, ArrayList<Usuario> usuarios){
        super(context, 0, usuarios);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.usuario, parent, false);
        }

        TextView tv_name = (TextView)convertView.findViewById(R.id.textView_name);
        TextView tv_surname = (TextView)convertView.findViewById(R.id.textView_surname);
        ImageView img_Foto = (ImageView) convertView.findViewById(R.id.imageView_usuario);

        Usuario usuarioicon = getItem(position);
        tv_name.setText(usuarioicon.getname());
        tv_surname.setText(usuarioicon.getsurname());

        String encodedImage = usuarioicon.getByteArray();

        if (encodedImage != null){
            byte[] image = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bmimage = BitmapFactory.decodeByteArray(image, 0, image.length);
            img_Foto.setImageBitmap(bmimage);
        }

        return convertView;
    }
}
package a07580542.emergencyphonenumber.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import a07580542.emergencyphonenumber.R;
import a07580542.emergencyphonenumber.model.PhoneItem;

/**
 * Created by Dream on 26/11/2560.
 */

public class PhoneListAdapter extends ArrayAdapter<PhoneItem> {

    private Context context;
    private int layoutresid;
    private ArrayList<PhoneItem> phoneitemslist;

    public PhoneListAdapter(@NonNull Context context, int layoutresid, @NonNull ArrayList<PhoneItem> phoneitemslist) {
        super(context, layoutresid, phoneitemslist);
        this.context=context;
        this.layoutresid=layoutresid;
        this.phoneitemslist=phoneitemslist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemlayout = inflater.inflate(layoutresid,null);
        PhoneItem item = phoneitemslist.get(position);
        ImageView phoneiv = itemlayout.findViewById(R.id.phoneimageview);
        TextView phonetitletv = itemlayout.findViewById(R.id.titletextview);
        TextView phonenumbertv = itemlayout.findViewById(R.id.phonenumbertextview);
        phonetitletv.setText(item.title);
        phonenumbertv.setText(item.number);
        AssetManager asset = context.getAssets();
        try {
            InputStream ip = asset.open(item.picture);
            Drawable drawble = Drawable.createFromStream(ip,null);
            phoneiv.setImageDrawable(drawble);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemlayout;
    }
}

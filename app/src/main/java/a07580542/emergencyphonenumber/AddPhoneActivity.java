package a07580542.emergencyphonenumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import pl.aprilapps.easyphotopicker.EasyImage;

public class AddPhoneActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText phonetitle,phonenumber;
    private ImageView phoneimage;
    private Button phoneadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        phonetitle = findViewById(R.id.phone_title_edittext);
        phonenumber = findViewById(R.id.phone_number_edittext);
        phoneimage = findViewById(R.id.phone_image_view);
        phoneadd = findViewById(R.id.phone_add_button);

        phoneadd.setOnClickListener(this);
        phoneimage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
     int viewid = view.getId();
     if(viewid==R.id.phone_image_view){
         EasyImage.openChooserWithGallery(AddPhoneActivity.this,"ถ่ายรูปหรือเลือกรูปภาพที่ต้องการ",0 );
     }else if(viewid==R.id.phone_add_button){

     }
    }
}

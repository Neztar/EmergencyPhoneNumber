package a07580542.emergencyphonenumber;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import a07580542.emergencyphonenumber.db.PhonedbHelper;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class AddPhoneActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText phonetitle,phonenumber;
    private ImageView phoneimage;
    private Button phoneadd;

    private File img_selected;
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
            if(img_selected==null){
                Toast.makeText(getApplicationContext(), "คุณยังไม่ได้เลือกรูปภาพ", Toast.LENGTH_LONG).show();
                return;
            }
            File privateDir = getApplicationContext().getFilesDir();
            File dstFile = new File(privateDir,img_selected.getName());
         try {
             copyFile(img_selected,dstFile);
         } catch (IOException e) {
             e.printStackTrace();
             return;
         }
         if(saveDatatoDB()){
             finish();
         }else{return;}
     }
    }

    private boolean saveDatatoDB() {
        String phoneTitle = phonetitle.getText().toString();
        String phoneNumber = phonenumber.getText().toString();
        String imgfilename = img_selected.getName();

        ContentValues cv = new ContentValues();
        cv.put(PhonedbHelper.getCol_title(),phoneTitle);
        cv.put(PhonedbHelper.getCol_number(),phoneNumber);
        cv.put(PhonedbHelper.getCol_picture(),imgfilename);
        PhonedbHelper db = new PhonedbHelper(getApplicationContext());
        SQLiteDatabase sqldb = db.getWritableDatabase();
        long result = sqldb.insert(PhonedbHelper.getTb_name(),null,cv);
        if(result==-1){
            Toast.makeText(this, "Error!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                //Handle the images
                img_selected = imagesFiles.get(0);
                Drawable drawable = Drawable.createFromPath(img_selected.getAbsolutePath());
                phoneimage.setImageDrawable(drawable);
            }
        });
    }
    public static void copyFile(File src, File dst) throws IOException {
        FileInputStream inputStream = new FileInputStream(src);
        FileOutputStream outputStream = new FileOutputStream(dst);
        byte[] buffer = new byte[1024];

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
    }
}

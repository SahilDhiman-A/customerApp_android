package com.spectra.consumer.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.spectra.consumer.R;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

public class CropforProfile extends AppCompatActivity {
    /*       -----Creating all views used in class -----------   */
    @BindView(R.id.cropImageView)
    CropImageView cropImageView;
    Bitmap bitmap,mImageBitmap;
    Intent intent;
    Uri uri;
    Uri file_upload;
    String type;



    /*       -----Inflating layout used in class -----------   */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_layout);
        ButterKnife.bind(this);
        intent=getIntent();
        uri=Uri.parse(intent.getStringExtra("uri"));
        type=intent.getStringExtra("type");
        cropImageView.setImageUriAsync(uri);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setAutoZoomEnabled(false);
        cropImageView.setAspectRatio(7,7);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    // save image in bitmap format
    private void savebitmap(Bitmap bitmap) {
        File file;
        Random random=new Random();
        int rand_name=random.nextInt();
        try {

            String file_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) +
                    "/Spectra";
            File dir = new File(file_path);
            if (!dir.exists())
                dir.mkdirs();
            file = new File(dir, "userImage"+rand_name+ ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap = Bitmap.createScaledBitmap(bitmap,700,700,false);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            mImageBitmap = decodeFile(file);
            mImageBitmap = CompressImage(file);
            Uri tempUri = getImageUri(getApplicationContext(), mImageBitmap);
            File finalFile = new File(getRealPathFromURI(tempUri));
            file_upload=Uri.fromFile(finalFile);
                Intent intent =new Intent(CropforProfile.this,MyAccountActivity.class);
                intent.putExtra("file",file.getPath());
                intent.putExtra("file_upload",file_upload.toString());
                setResult(Activity.RESULT_OK,intent);
                finish();
        }
        catch(Exception ignored){

        }


    }
    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    private Bitmap decodeFile(File f){
        Bitmap b = null;

        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis;
        try {
            fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int scale = 1;
            if (o.outHeight > 1000 || o.outWidth > 1000) {
                scale = (int)Math.pow(2, (int) Math.ceil(Math.log(1000 /
                        (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return b;
    }
    private Bitmap CompressImage(File file){
        Bitmap compressedImage = null;
        try {
            compressedImage = new Compressor(this)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .compressToBitmap(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressedImage;
    }
    @OnClick({R.id.txt_cancel,R.id.txt_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_done:
                bitmap=cropImageView.getCroppedImage();
                savebitmap(bitmap);
                break;
            case R.id.txt_cancel:
                onBackPressed();
                break;
        }
        }
}

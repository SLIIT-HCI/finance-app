package com.example.financemanagementapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartReceiptCapture extends AppCompatActivity {

    private String currentImagePath = null;
    private static final int IMAGE_REQUEST = 1;

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_receipt_capture);

        imageView = findViewById(R.id.captureImage);
        textView = findViewById(R.id.textId);

        //check app level permission is granted for Camera
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            //grant the permission
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        }

    }

    /************************************************************************************/

    public void dispatchTakePictureIntent(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager()) != null ) {
            File imageFile = null;

            try {
                imageFile = getImageFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if(imageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(this,"com.example.android.fileprovider", imageFile);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, IMAGE_REQUEST);
            }
        }
    }

    /************************************************************************************/

    private File getImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStamp + "_";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageName, "jpg", storageDirectory);
        currentImagePath =imageFile.getAbsolutePath();
        return imageFile;
    }

    /************************************************************************************/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentImagePath);
            //imageView.setRotation(180);
            imageView.setImageBitmap(bitmap);
        }

         /*


        Bitmap bitmap = BitmapFactory.decodeFile(currentImagePath);

        Matrix matrix = new Matrix();
        //matrix.postRotate(90);
        /*
        try {
            ExifInterface exif = new ExifInterface(currentImagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
                default:
                    break;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        imageView.setImageBitmap(bitmap);
        //bitmap.recycle();
        */

    }

    /************************************************************************************/

    public void extractData(View view) {
        //perform text detection here
        //define TextRecognizer
        TextRecognizer recognizer = new TextRecognizer.Builder(SmartReceiptCapture.this).build();

        //Get bitmap from imageview
        Bitmap bitmap2 = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        //get frame from bitmap
        Frame frame = new Frame.Builder().setBitmap(bitmap2).build();

        //get data from frame
        SparseArray<TextBlock> sparseArray =  recognizer.detect(frame);

        //set data on textview
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0;i < sparseArray.size(); i++){
            TextBlock tx = sparseArray.get(i);
            String str = tx.getValue();
            stringBuilder.append(str);
        }

        StringBuilder extractedText = stringBuilder;
        textView.setText(stringBuilder);

        String date = " ";
        String regex1 = "(\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}/\\d{1,2})";
        Pattern p = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = p.matcher(extractedText);
        while (matcher1.find()) {
             date = matcher1.group();
        }
        Toast.makeText(getApplicationContext(), date,  Toast.LENGTH_SHORT).show();

        String time = " ";
        String regex2 = "([\\d]{1,2}:[\\d]{1,2})";
        Pattern p2 = Pattern.compile(regex2, Pattern.CASE_INSENSITIVE);
        Matcher matcher2 = p2.matcher(extractedText);
        while (matcher2.find()) {
            time = matcher2.group();
        }
        Toast.makeText(getApplicationContext(), time,  Toast.LENGTH_SHORT).show();

    }

}
package com.example.financemanagementapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartReceiptCapture extends AppCompatActivity {

    private static final String TAG = " ";
    static final int REQUEST_IMAGE_CAPTURE = 1;
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


    public void dispatchTakePictureIntent(View view) {
        //open the camera => create an Intent object
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        //from bundle, extract the image
        Bitmap bitmap = (Bitmap) bundle.get("data");
        //set image in imageview
        imageView.setImageBitmap(bitmap);

    }


    /************************************************************************************/

    /*
    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
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
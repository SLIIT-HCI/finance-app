package com.example.financemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextRecognitionActivity extends AppCompatActivity {

    private final int REQUEST_CODE=10;
    ImageView image;
    Button importBtn, detectbtn;
    TextView diplayDetectedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);

        image = findViewById(R.id.receipt);
        importBtn = findViewById(R.id.importBtn);
        detectbtn = findViewById(R.id.txtDetectBtn);
        diplayDetectedText = findViewById(R.id.diplayDetectedText);

        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(getImage,REQUEST_CODE);
                importBtn.setText("Repick Image");
            }
        });

        detectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectText();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==REQUEST_CODE && data != null && data.getData() != null) {
            Uri uri = data.getData();
            image.setImageURI(uri);
        }

    }

    private void detectText() {
        //perform text detection here
        //define TextRecognizer
        TextRecognizer recognizer = new TextRecognizer.Builder(TextRecognitionActivity.this).build();

        //Get bitmap from imageview
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();

        //get frame from bitmap
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        //get data from frame
        SparseArray<TextBlock> sparseArray =  recognizer.detect(frame);

        //set data on textview
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0;i < sparseArray.size(); i++){
            TextBlock tx = sparseArray.get(i);
            String str = tx.getValue();
            stringBuilder.append(str);
        }
        diplayDetectedText.setText(stringBuilder);

        StringBuilder extractedText = stringBuilder;

        String date = " ";
        String regex1 = "(\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}.\\d{1,2}.\\d{4}|\\d{1,2}-\\d{1,2}-\\d{4}|\\d{1,2}/\\d{1,2})";
        Pattern p = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = p.matcher(extractedText);
        while (matcher1.find()) {
            date = matcher1.group();
        }
        Toast.makeText(getApplicationContext(), date,  Toast.LENGTH_SHORT).show();

        String time = " ";
        String regex2 = "([\\d]{1,2}:[\\d]{1,2}|[\\d]{1,2}:[\\d]{1,2} [aApP][mM])";
        Pattern p2 = Pattern.compile(regex2, Pattern.CASE_INSENSITIVE);
        Matcher matcher2 = p2.matcher(extractedText);
        while (matcher2.find()) {
            time = matcher2.group();
        }
        Toast.makeText(getApplicationContext(), time,  Toast.LENGTH_SHORT).show();

    }


}
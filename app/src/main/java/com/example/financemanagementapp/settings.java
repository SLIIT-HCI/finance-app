package com.example.financemanagementapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class settings extends AppCompatActivity {

    Button LanguageS;
    //private String listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_settings);

       //change actionbar title, if you dont change it will be according to your system's default language
        //ActionBar actionBar = getSupportActionBar();
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.app_name));
      

        LanguageS  = (Button) findViewById(R.id.Lpopup);
        LanguageS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();
            }
        });
    }

    private void showChangeLanguageDialog() {
        //array of languages to display in alert box
        final String[] listItems = {"English", "French", "Hindi", "Sinhala", "Italian", "Japanese", "Korean", "Dutch", "Tamil", "Chinese"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(settings.this);
        mBuilder.setTitle("Choose Language....");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    //English
                    setLocale("en");
                    recreate();
                }
                else if (i == 1){
                    //French
                    setLocale("fr");
                    recreate();
                }
                else if (i == 2){
                    //Hindi
                    setLocale("hi");
                    recreate();
                }
                else if (i == 3){
                    //Sinhala
                    setLocale("si");
                    recreate();
                }

                else if (i == 4){
                    //Italian
                    setLocale("it");
                    recreate();
                }
                else if (i == 5){
                    //Japanese
                    setLocale("ja");
                    recreate();
                }
                else if (i == 6){
                    //Korean
                    setLocale("ko");
                    recreate();
                }
                else if (i == 7){
                    //Dutch
                    setLocale("nl");
                    recreate();
                }
                else if (i == 8){
                    //Tamil
                    setLocale("ta");
                    recreate();
                }
                else if (i == 9){
                    //Chinese
                    setLocale("zh");
                    recreate();
                }

                //dismiss the alert dialog when language is selected
                dialogInterface.dismiss();
                
                
            }
        });
        AlertDialog mDialog = mBuilder.create();
        //show alert dialog
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale= new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //save data to the shared preference
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    //load language saved in shared preferences
    public void loadLocale(){
        SharedPreferences pref = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        setLocale(language);
    }
}
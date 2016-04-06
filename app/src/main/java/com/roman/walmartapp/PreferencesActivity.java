package com.roman.walmartapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class PreferencesActivity extends AppCompatActivity {

    RadioGroup myRadGroup;
    RadioButton myIpod;
    RadioButton myLaptop;
    RadioButton myCandy;
    RadioButton myChocolate;
    RadioButton myMp3;
    ToggleButton mySpecials;
    TextView mySpecialsText;
    Button myReset;
    Button myAdd;
    EditText myEditText;
    TextView myTextLocation;
    TextView myTextTitle;
    public static String titleSearch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        myTextTitle = (TextView) findViewById(R.id.foodPref);
        myIpod = (RadioButton) findViewById(R.id.ipodRB);
        myLaptop = (RadioButton) findViewById(R.id.laptopRB);
        myCandy = (RadioButton) findViewById(R.id.candyRB);
        myChocolate = (RadioButton) findViewById(R.id.ChocolateRB);
        myMp3 = (RadioButton) findViewById(R.id.mp3RB);
        mySpecials = (ToggleButton) findViewById(R.id.specialToggle);
        mySpecialsText = (TextView) findViewById(R.id.specialText);
        myTextLocation = (TextView) findViewById(R.id.myLocationText);
        myEditText = (EditText) findViewById(R.id.myEmailEditText);
        myReset = (Button) findViewById(R.id.resetBtn);
        myAdd = (Button) findViewById(R.id.addBtn);
        myRadGroup = (RadioGroup) findViewById(R.id.radioGroup);

        final SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        myTextTitle.setText(prefs.getString("title", "Items"));
        myIpod.setChecked(prefs.getBoolean("RBIpod", true));
        myLaptop.setChecked(prefs.getBoolean("RBLaptop", false));
        myCandy.setChecked(prefs.getBoolean("RBcandy", false));
        myChocolate.setChecked(prefs.getBoolean("RBchocolate", false));
        myMp3.setChecked(prefs.getBoolean("RBmp3", false));
        mySpecials.setChecked(prefs.getBoolean("SpecialToggle", false));
        mySpecialsText.setText(prefs.getString("special", "Don't Send Me Specials"));
        myTextLocation.setText(prefs.getString("location", "None"));
        if(prefs.getBoolean("Reset", false)){
            findViewById(R.id.addView).setVisibility(View.GONE);
            findViewById(R.id.resetView).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.addView).setVisibility(View.VISIBLE);
            findViewById(R.id.resetView).setVisibility(View.GONE);
        }
        titleSearch = myTextTitle.getText().toString();

        myIpod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myTextTitle.setText("Ipod");
                    titleSearch = "iPod";
                    editor.putString("title", myTextTitle.getText().toString());
                    editor.putBoolean("RBIpod", true);
                    editor.commit();
                } else {
                    editor.putBoolean("RBIpod", false);
                    editor.commit();
                }
            }
        });
        myLaptop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myTextTitle.setText("Laptop");
                    titleSearch = "Laptop";
                    editor.putString("title", myTextTitle.getText().toString());
                    editor.putBoolean("RBLaptop", true);
                    editor.commit();
                } else {
                    editor.putBoolean("RBLaptop", false);
                    editor.commit();
                }
            }
        });
        myCandy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myTextTitle.setText("Candy");
                    titleSearch = "Candy";
                    editor.putString("title", myTextTitle.getText().toString());
                    editor.putBoolean("RBcandy", true);
                    editor.commit();
                } else {
                    editor.putBoolean("RBcandy", false);
                    editor.commit();
                }
            }
        });
        myChocolate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myTextTitle.setText("Chocolate");
                    titleSearch = "Chocolate";
                    editor.putString("title", myTextTitle.getText().toString());
                    editor.putBoolean("RBchocolate", true);
                    editor.commit();
                } else {
                    editor.putBoolean("RBchocolate", false);
                    editor.commit();
                }
            }
        });
        myMp3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myTextTitle.setText("Mp3");
                    titleSearch = "Mp3";
                    editor.putString("title", myTextTitle.getText().toString());
                    editor.putBoolean("RBmp3", true);
                    editor.commit();
                } else {
                    editor.putBoolean("RBmp3", false);
                    editor.commit();
                }
            }
        });


        mySpecials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySpecials.isChecked()) {
                    //mySpecialsText.setText(prefs.getString("special", "Send Me Specials"));
                    mySpecialsText.setText("Send Me Specials");
                    editor.putString("special", mySpecialsText.getText().toString());
                    editor.putBoolean("SpecialToggle", true);
                    editor.commit();
                } else {
                    mySpecialsText.setText("Don't Send Me Specials");
                    editor.putString("special", mySpecialsText.getText().toString());
                    editor.putBoolean("SpecialToggle", false);
                    editor.commit();
                }
            }
        });

        myReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.addView).setVisibility(View.VISIBLE);
                findViewById(R.id.resetView).setVisibility(View.GONE);

                myTextLocation.setText("");
                editor.putString("location", myTextLocation.getText().toString());
                editor.putBoolean("Reset", false);
                editor.commit();
            }
        });
        myAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.addView).setVisibility(View.GONE);
                findViewById(R.id.resetView).setVisibility(View.VISIBLE);

                myTextLocation.setText(myEditText.getText().toString());
                editor.putString("location", myTextLocation.getText().toString());
                editor.putBoolean("Reset", true);
                editor.commit();
            }
        });
    }
}

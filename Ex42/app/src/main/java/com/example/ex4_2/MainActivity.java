package com.example.ex4_2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView tvSelect;
    CheckBox chkAgreee;
    RadioGroup rGroup1;
    RadioButton rdoDog, rdoCat, rdoRabbit;
    Button btnOk;
    ImageView imgPet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("애완동물 사진보기");
        tvSelect=(TextView) findViewById(R.id.tvSelect);
        chkAgreee= (CheckBox) findViewById(R.id.chkAgree);
        rGroup1 = (RadioGroup) findViewById(R.id.rGroup1);
        rdoDog = (RadioButton) findViewById(R.id.rdoCat);
        rdoRabbit = (RadioButton) findViewById(R.id.rdoRabbit);
        btnOk = (Button) findViewById(R.id.btnOk);
        imgPet = (ImageView) findViewById(R.id.imgPet);

        chkAgreee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    tvSelect.setVisibility(View.VISIBLE);
                    rGroup1.setVisibility(View.VISIBLE);
                    btnOk.setVisibility(View.VISIBLE);
                    imgPet.setVisibility(View.VISIBLE);
                } else {
                    tvSelect.setVisibility(View.INVISIBLE);
                    rGroup1.setVisibility(View.INVISIBLE);
                    btnOk.setVisibility(View.INVISIBLE);
                    imgPet.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedRadioButtonId = rGroup1.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.rdoDog) {
                    imgPet.setImageResource(R.drawable.dog);
                } else if (checkedRadioButtonId == R.id.rdoCat) {
                    imgPet.setImageResource(R.drawable.cat);
                } else if (checkedRadioButtonId == R.id.rdoRabbit) {
                    imgPet.setImageResource(R.drawable.rabbit);
                } else {
                    Toast.makeText(getApplicationContext(), "동물 선택", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
package com.example.ex4_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edtText1, edtText2;
    Button btnAdd, btnSub, btnMul, btnDiv;
    TextView txtViewResult;
    String num1, num2;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("계산기");
        edtText1 = (EditText) findViewById(R.id.edtText1);
        edtText2 = (EditText) findViewById(R.id.edtText2);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        txtViewResult = (TextView) findViewById(R.id.txtViewResult);

        btnAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                num1 = edtText1.getText().toString();
                num2 = edtText2.getText().toString();

                result = Integer.parseInt(num1) + Integer.parseInt(num2);
                txtViewResult.setText("계산 결과 : " + result);
                return true;
            }
        });

        btnSub.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                num1 = edtText1.getText().toString();
                num2 = edtText2.getText().toString();

                result = Integer.parseInt(num1) - Integer.parseInt(num2);
                txtViewResult.setText("계산 결과 : " + result);
                return true;
            }
        });

        btnMul.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                num1 = edtText1.getText().toString();
                num2 = edtText2.getText().toString();

                result = Integer.parseInt(num1) * Integer.parseInt(num2);
                txtViewResult.setText("계산 결과 : " + result);
                return true;
            }
        });

        btnDiv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                num1 = edtText1.getText().toString();
                num2 = edtText2.getText().toString();

                result = Integer.parseInt(num1) / Integer.parseInt(num2);
                txtViewResult.setText("계산 결과 : " + result);
                return true;
            }
        });
    }
}
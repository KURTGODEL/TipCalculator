package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private EditText txtAmount;
    private SeekBar seekBar1;
    private TextView lblSeekBar;
    private EditText txtTip;
    private EditText txtTotal;
    private TextView lblAmount;
    private Double billAmount;
    private TextView lblTest;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAmount = findViewById(R.id.txtAmount);
        seekBar1 = findViewById(R.id.seekBar1);
        txtTip = findViewById(R.id.txtTip);
        txtTotal = findViewById(R.id.txtTotal);
        lblSeekBar = findViewById(R.id.lblSeekBar);
        lblAmount = findViewById(R.id.lblAmount);
        lblTest = findViewById(R.id.lblTest);


        DBHelper dbHelper = new DBHelper(this);
        dbHelper.insertUsers();

        lblTest.setText(String.valueOf(dbHelper.numberOfRows()));

        lblTest.setText(dbHelper.getUser(1));


        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lblSeekBar.setText(String.valueOf(progress) + "%");

                calculateTipTotal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        txtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    billAmount = Double.parseDouble(s.toString()) / 100;
                    lblAmount.setText(currencyFormat.format(billAmount));
                }catch (Exception ex){ }

                calculateTipTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void calculateTipTotal(){
        String strAmount = txtAmount.getText().toString();

        if(!strAmount.isEmpty()) {
            Double tip = new Double(seekBar1.getProgress());
            Double amount = billAmount;
            Double total = ((tip / 100) * amount) + amount;

            txtTip.setText(currencyFormat.format(tip * billAmount / 100));
            txtTotal.setText(currencyFormat.format(total));
        }
    }
}

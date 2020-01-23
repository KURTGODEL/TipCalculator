package com.example.tipcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

    /* ********* ACTIVITY #3 ********* */
    private EditText editTextName, editTextSurname, editTextMarks;
    private Button buttonAddData;
    private Button buttonViewAll;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    /* ********* ACTIVITY #2 ********* */
    DatabaseHelper myDB;


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
        lblTest = findViewById(R.id.txtView3);

        /* ********* ACTIVITY #3 ********* */
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextMarks = findViewById(R.id.editTextMarks);
        buttonAddData = findViewById(R.id.button_add);
        buttonViewAll = findViewById(R.id.button_viewall);


        /* ********* ACTIVITY #1 ********* */
        //DBHelper dbHelper = new DBHelper(this);
        //dbHelper.insertUsers();

        //lblTest.setText(String.valueOf(dbHelper.numberOfRows()));

        //lblTest.setText(dbHelper.getUser(1));



        /* ********* ACTIVITY #2 ********* */
        myDB = new DatabaseHelper(this);
        AddData();
        ViewAll();



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

    /* ********* ACTIVITY #3 ********* */
    public void AddData(){
        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertData(
                        editTextName.getText().toString(),
                        editTextSurname.getText().toString(),
                        editTextMarks.getText().toString());

                if(isInserted){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /* ********* ACTIVITY #3 ********* */
    public void ViewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();

                if(res.getCount() == 0){
                    showMessage("ALERT", "No data found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while(res.moveToNext()){
                    buffer.append("Id: " + res.getString(0) + "\t ");
                    buffer.append("Name: " + res.getString(1)+ "\t ");
                    buffer.append("Surname: " + res.getString(2)+ "\t ");
                    buffer.append("Marks: " + res.getString(3)+ "\n\n");
                }

                showMessage("DATA", buffer.toString());
            }
        });
    }

    /* ********* ACTIVITY #3 ********* */
    public void showMessage(String title, String message){
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.scrollable, null);

        TextView textview = (TextView) view.findViewById(R.id.textmsg);
        textview.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(view);
        builder.setTitle(title);

        AlertDialog alert = builder.create();
        alert.show();
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

package com.example.machine_manager;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditMachine extends AppCompatActivity {

    RadioButton radioButton;
    RadioButton radioButton2;
    Button readyButton;
    Button cancelButton;
    EditText editText;
    int machineNumber;
    String machineType ="";

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_machine);

        editText = (EditText) findViewById(R.id.editText3);
        readyButton = (Button) findViewById(R.id.ready_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);



        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioButton = new RadioButton(EditMachine.this);
        radioButton2 = new RadioButton(EditMachine.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        radioButton.setLayoutParams(layoutParams);
        radioButton.setText(R.string.mill);
        //radioButton.setTextColor(getResources().getColor(R.color.colorWhite));
        //radioButton2.setTextColor(getResources().getColor(R.color.colorWhite));
        Log.i("EditMachine",radioButton.getTextColors().toString());
        radioButton2.setLayoutParams(layoutParams);
        radioButton2.setText(R.string.turn);

        final Button button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i==0){
                    radioGroup.addView(radioButton);
                    radioGroup.addView(radioButton2);
                    i=1;
                }
                else {
                    radioGroup.removeAllViews();
                    i=0;
                }

            }
        });

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button8.setText(R.string.mill);
                machineType = getResources().getString(R.string.type_mill);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button8.setText(R.string.turn);
                machineType = getResources().getString(R.string.type_turn);
            }
        });

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    machineNumber = Integer.parseInt(editText.getText().toString());
                    if (machineType.isEmpty()) {
                        Toast.makeText(EditMachine.this, "Выберите тип станка",Toast.LENGTH_LONG).show();
                    }
                    else {
                        int result = MainActivity.detailMachineHelper.insertMachineToDB(machineNumber, machineType);
                        if (result == 2) {
                            Toast.makeText(EditMachine.this,"Данные введены в базу", Toast.LENGTH_SHORT).show();
                            Log.i("EditMachine", "Данные введены в базу" );
                        } else if (result == 0){
                            String text = "Станок с номером "+ machineNumber + " уже существует в базе данных!";
                            Toast.makeText(EditMachine.this,text, Toast.LENGTH_SHORT).show();
                            Log.i("EditMachine", text );
                        }
                        else {
                            Toast.makeText(EditMachine.this,"Ошибка при введении данных", Toast.LENGTH_SHORT).show();
                            Log.i("EditMachine", "Ошибка при введении данных" );
                        }
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(EditMachine.this, "Введите номер станка правильно",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }



}

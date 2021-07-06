package com.example.machine_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditDetail extends AppCompatActivity {

    EditText editDetailNumber;
    EditText editOperationNumber;
    EditText editTimeOfOperation;
    Button choiceTypeOfOperation;
    RadioGroup radioGroupEditDetail;
    RadioButton buttonMill;
    RadioButton buttonTurn;
    Button buttonReady;
    Button buttonCancel;
    private static String detailNumber;
    private static int operationNumber = 0;
    private static Double operationTime = 0.0;
    private static String operationType = "";
    private static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail);

        editDetailNumber = (EditText) findViewById(R.id.editDetailNumber);
        editOperationNumber = (EditText) findViewById(R.id.editOperationNumber);
        editTimeOfOperation = (EditText) findViewById(R.id.editOperationTime);
        choiceTypeOfOperation = (Button)findViewById(R.id.choiceOperationType);
        radioGroupEditDetail = (RadioGroup) findViewById(R.id.radio_group_Edit_Detail);
        buttonReady = (Button) findViewById(R.id.button_ready);
        buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonMill  = new RadioButton(this);
        buttonTurn  = new RadioButton(this);
        final LinearLayout.LayoutParams paramsForButton =  new LinearLayout.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        buttonMill.setText(R.string.type_operation_mill);
        buttonTurn.setText(R.string.type_operation_turn);

        choiceTypeOfOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i==0){
                    radioGroupEditDetail.addView(buttonMill,paramsForButton);
                    radioGroupEditDetail.addView(buttonTurn,paramsForButton);
                    i=1;
                }
                else {
                    radioGroupEditDetail.removeAllViews();
                    i=0;
                }
            }
        });

        buttonMill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceTypeOfOperation.setText(R.string.type_operation_mill);
                operationType = getResources().getString(R.string.type_operation_mill);
                radioGroupEditDetail.removeAllViews();
                i=0;
            }
        });

        buttonTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceTypeOfOperation.setText(R.string.type_operation_turn);
                operationType = getResources().getString(R.string.type_operation_turn);
                radioGroupEditDetail.removeAllViews();
                i=0;
            }
        });

        buttonReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    detailNumber = editDetailNumber.getText().toString();
                } catch (Exception e) {
                    Toast.makeText(EditDetail.this,"неправильно введен номер детали", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                try {
                    operationNumber = Integer.parseInt( editOperationNumber.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(EditDetail.this,"неправильно введен номер операции", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                try {
                    operationTime = Double.parseDouble(editTimeOfOperation.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(EditDetail.this,"неправильно введено время операции", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                if (detailNumber.isEmpty() || operationNumber == 0 || operationType.isEmpty() || operationTime == 0.0){
                    Toast.makeText(EditDetail.this,"Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                else {
                    int response = MainActivity.detailMachineHelper.insertDetailToDB(detailNumber, operationNumber, operationType, operationTime);
                    if (response == 2){
                        Toast.makeText(EditDetail.this,"Данные введены в базу", Toast.LENGTH_SHORT).show();
                    }
                    else if (response == 1){
                        Toast.makeText(EditDetail.this,"Ошибка при вставке данных", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String text = "Деталь № "+ detailNumber + ", операция № "+ operationNumber + " уже существует в базе данных!";
                        Toast.makeText(EditDetail.this,text, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

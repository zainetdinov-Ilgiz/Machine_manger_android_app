package com.example.machine_manager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class EditProvision extends AppCompatActivity implements View.OnClickListener {

    EditText editTextBatchNumber;
    EditText editTextQuantityDetails;
    Button buttonChoiceDetailNumber;
    Button ready;
    Button cancel;
    LinearLayout layoutChoice;
    LinearLayout layoutChoiceOperationNumber;
    ListView detailNumberList;
    private static int count = 0;
    private int butchNumber ;
    private int quantityDetails;
    String detailNumber;
    Integer[] checkedOperationsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_provision);

        editTextBatchNumber = findViewById(R.id.enter_Batch_Number);
        editTextQuantityDetails = findViewById(R.id.enter_Quantity_Of_Details);
        buttonChoiceDetailNumber = findViewById(R.id.choice_Detail_Number);
        ready = findViewById(R.id.ready_button_choice_layout);
        cancel = findViewById(R.id.cancel_button_choice_layout);
        layoutChoice = findViewById(R.id.layout_choiceDetailNumber);
        layoutChoiceOperationNumber = findViewById(R.id.layout_choiceOperationNumber);
        detailNumberList = new ListView(EditProvision.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 680);
        detailNumberList.setLayoutParams(params);
        buttonChoiceDetailNumber.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ready.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.choice_Detail_Number:
                if (count == 0) {
                    ArrayList<String> NumberList = MainActivity.detailMachineHelper.getDetailNumberList();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, NumberList);
                    detailNumberList.setAdapter(adapter);
                    layoutChoice.addView(detailNumberList);
                    layoutChoice.bringToFront();
                    detailNumberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            detailNumber = ((TextView)view).getText().toString();
                            String text = "Деталь № " + detailNumber
                                    + ". Выберите операции:";
                            buttonChoiceDetailNumber.setText(text);
                            layoutChoice.removeAllViews();
                            ArrayList<Integer> operationNumberList = MainActivity.detailMachineHelper.getOperationNumberList(detailNumber);
                            Collections.sort(operationNumberList);
                            for (Integer operationNumber : operationNumberList){
                                CheckBox checkBox = new CheckBox(EditProvision.this);
                                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                checkBox.setLayoutParams(params2);
                                checkBox.setChecked(true);
                                //String text2 = "Операция № " + operationNumber.toString();
                                String text2 = operationNumber.toString();
                                checkBox.setText(text2);
                                layoutChoice.addView(checkBox);
                            }
                            layoutChoice.bringToFront();
                        }
                    });
                    count = 1;
                }
                else {
                    layoutChoice.removeAllViews();
                    count = 0;
                }
                break;
            case R.id.cancel_button_choice_layout:
                finish();
                break;
            case R.id.ready_button_choice_layout:
                ArrayList<Integer> checkedOperationsArray = new ArrayList<>();
                try {
                    butchNumber = Integer.parseInt(editTextBatchNumber.getText().toString());
                    quantityDetails = Integer.parseInt(editTextQuantityDetails.getText().toString());
                    if (layoutChoice.getChildCount()!=0) {
                        for (int i = 0; i < layoutChoice.getChildCount(); i++){
                            if (((CheckBox)layoutChoice.getChildAt(i)).isChecked()) {
                                checkedOperationsArray.add( Integer.parseInt(((CheckBox)layoutChoice.getChildAt(i)).getText().toString()));
                            }
                        }
                    }
                    else {
                        Toast.makeText(EditProvision.this,"Заполните все поля", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Toast.makeText(EditProvision.this,"Все прошло удачно", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(EditProvision.this,"Заполните все поля", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                MainActivity.detailMachineHelper.insertProvisionToDB(butchNumber,quantityDetails,detailNumber, checkedOperationsArray);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}

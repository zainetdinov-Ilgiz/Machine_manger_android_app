package com.example.machine_manager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.machine_manager.data.DetailMachineContract;

import java.util.ArrayList;

public class MachineChoice extends AppCompatActivity implements View.OnClickListener {

    Button button5;
    Button button6;
    Button buttonBack;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    ListView listView;
    ListView listView2;
    ListView listView3;
    LinearLayout.LayoutParams layoutParams;
    LinearLayout.LayoutParams layoutParams2;
    private static int count = 0;
    private static int count2 = 0;
    View customLayout;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine__choice);

        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        buttonBack = findViewById(R.id.buttonBackFromMachineChoice);
        linearLayout = findViewById(R.id.linear_layout);
        linearLayout2 = findViewById(R.id.linear_layout2);
        listView = new ListView(MachineChoice.this);
        listView2 = new ListView(MachineChoice.this);
        listView3 = findViewById(R.id.List_view3);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 750);
        layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 680);
        listView.setLayoutParams(layoutParams);
        listView2.setLayoutParams(layoutParams2);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.button5:

                if (count == 0) {
                    linearLayout2.removeAllViews();
                    ArrayList<Integer> machineNumberList;
                    final String machineType = getResources().getString(R.string.type_mill);
                    machineNumberList = MainActivity.detailMachineHelper.getMachine(machineType);
                    final ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, machineNumberList);
                    listView.setAdapter(adapter);
                    linearLayout.bringToFront();
                    linearLayout.addView(listView);
                    ArrayAdapter adapter1;
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String machineNumber1 = ((TextView) view).getText().toString();
                            Integer machineNumber = Integer.parseInt (machineNumber1);
                            Cursor cursor = MainActivity.detailMachineHelper.getDataFromDetailMachine(machineNumber);
                            String[] data ;
                            LayoutInflater inflater = getLayoutInflater();
                            ArrayList<View> list = new ArrayList<>();
                            while (cursor.moveToNext()){
                                data = new String[6];
                                data[0]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER));
                                data[1]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER));
                                data[2]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailMachineTable.COLUMN_BATCH_NUMBER));
                                data[3]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailMachineTable.COLUMN_QUANTITY_OF_DETAILS));
                                data[4]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_BEGINNING));
                                data[5]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_ENDING));
                                customLayout = new CustomLayout().onCreateView(inflater, data);
                                list.add(customLayout);
                            }
                            linearLayout.removeAllViews();
                            ArrayAdapter<View> adapter1 = new CustomArrayAdapter(getApplicationContext(), list);
                            listView3.setAdapter(adapter1);
                            listView3.bringToFront();
                            count=0;
                            Toast.makeText(MachineChoice.this, "Выбран " + machineType +" станок с номером " +
                                    machineNumber1,Toast.LENGTH_LONG).show();
                        }
                    });
                    count = 1;
                }
                else {
                    linearLayout.removeAllViews();
                    count = 0;
                }
                break;
            case R.id.button6:

                if (count2 == 0) {
                    ArrayList<Integer> machineNumberList;
                    final String machineType = getResources().getString(R.string.type_turn);
                    machineNumberList = MainActivity.detailMachineHelper.getMachine(machineType);
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, machineNumberList);
                    listView2.setAdapter(adapter);
                    linearLayout2.bringToFront();
                    linearLayout2.addView(listView2);
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String machineNumber1 = ((TextView) view).getText().toString();
                            Integer machineNumber = Integer.parseInt (machineNumber1);
                            Cursor cursor = MainActivity.detailMachineHelper.getDataFromDetailMachine(machineNumber);
                            String[] data ;
                            LayoutInflater inflater = getLayoutInflater();
                            ArrayList<View> list = new ArrayList<>();
                            while (cursor.moveToNext()){
                                data = new String[6];
                                data[0]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER));
                                data[1]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER));
                                data[2]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailMachineTable.COLUMN_BATCH_NUMBER));
                                data[3]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailMachineTable.COLUMN_QUANTITY_OF_DETAILS));
                                data[4]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_BEGINNING));
                                data[5]= cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_ENDING));
                                customLayout = new CustomLayout().onCreateView(inflater, data);
                                list.add(customLayout);
                            }
                            linearLayout2.removeAllViews();
                            ArrayAdapter<View> adapter1 = new CustomArrayAdapter(getApplicationContext(), list);
                            listView3.setAdapter(adapter1);
                            listView3.bringToFront();
                            count2 = 0;
                            Toast.makeText(MachineChoice.this, "Выбран " + machineType +" станок с номером " +
                                    ((TextView) view).getText().toString(),Toast.LENGTH_LONG).show();
                        }
                    });
                    count2 = 1;
                }
                else {
                    linearLayout2.removeAllViews();
                    count2 = 0;
                }
                break;
            case R.id.buttonBackFromMachineChoice:
                finish();
        }

    }

}
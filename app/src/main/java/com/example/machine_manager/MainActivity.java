package com.example.machine_manager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.machine_manager.data.DetailMachineContract;
import com.example.machine_manager.data.DetailMachineHelper;

public class MainActivity extends AppCompatActivity {


    protected static DetailMachineHelper detailMachineHelper;
    SQLiteDatabase detailMachineDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonMachineChoice = (Button) findViewById(R.id.buttonMachineChoice);
        buttonMachineChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, MachineChoice.class);
                startActivity(intent);
            }
        });

        Button buttonEditProvision = findViewById(R.id.buttonEditProvision);
        buttonEditProvision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditProvision.class);
                startActivity(intent);


            }
        });

        Button buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonEditMachine = (Button) findViewById(R.id.buttonEditMachine);
        buttonEditMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditMachine.class);
                startActivity(intent);
            }
        });

        Button buttonEditDetail = (Button) findViewById(R.id.buttonEditDetail);
        buttonEditDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditDetail.class);
                startActivity(intent);
            }
        });

        detailMachineHelper = new DetailMachineHelper(this);


    }
}

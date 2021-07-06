package com.example.machine_manager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DetailMachineHelper extends SQLiteOpenHelper {


    private static final String LOG_TAG = DetailMachineHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "Detail_Machine.db";
    private static final int DATABASE_VERSION = 5;

    public DetailMachineHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_DETAIL_TABLE = "CREATE TABLE " + DetailMachineContract.DetailTable.TABLE_NAME + " ("
                + DetailMachineContract.DetailTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER+" TEXT NOT NULL, "
                + DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER + " INTEGER NOT NULL, "
                + DetailMachineContract.DetailTable.COLUMN_TYPE_OF_OPERATION + " TEXT NOT NULL, "
                + DetailMachineContract.DetailTable.COLUMN_TIME_OF_OPERATION + " REAL NOT NULL, "
                + "UNIQUE( " + DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER + ", "
                + DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER + " ));";

        String SQL_CREATE_MACHINE_TABLE = "CREATE TABLE " + DetailMachineContract.MachineTable.TABLE_NAME + " ("
                + DetailMachineContract.MachineTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER + " INTEGER NOT NULL, "
                + DetailMachineContract.MachineTable.COLUMN_TYPE_OF_MACHINE + " TEXT NOT NULL, "
                + DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_ENDING + " TEXT NOT NULL DEFAULT '0000-00-00 00:00', "
                + " UNIQUE ( " + DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER + " ));";

        String SQL_CREATE_DETAIL_MACHINE_TABLE = "CREATE TABLE " + DetailMachineContract.DetailMachineTable.TABLE_NAME + " ("
                + DetailMachineContract.DetailMachineTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DetailMachineContract.DetailMachineTable.COLUMN_BATCH_NUMBER + " INTEGER NOT NULL, "
                + DetailMachineContract.DetailMachineTable.COLUMN_QUANTITY_OF_DETAILS + " INTEGER NOT NULL, "
                + DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER +" TEXT NOT NULL, "
                + DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER + " INTEGER NOT NULL, "
                + DetailMachineContract.DetailTable.COLUMN_TYPE_OF_OPERATION + " TEXT NOT NULL, "
                + DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER + " INTEGER NOT NULL, "
                + DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_BEGINNING + " TEXT NOT NULL, "
                + DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_ENDING + " TEXT NOT NULL, " +
                "UNIQUE ( "
                +DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER + ", "
                + DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER +", "
                + DetailMachineContract.DetailMachineTable.COLUMN_BATCH_NUMBER + " ));";

        db.execSQL(SQL_CREATE_DETAIL_TABLE);
        db.execSQL(SQL_CREATE_MACHINE_TABLE);
        db.execSQL(SQL_CREATE_DETAIL_MACHINE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String message = "Обновляемся с версии 4 до версии 5";
        Log.i(LOG_TAG, message);
        db.execSQL("DROP TABLE " + DetailMachineContract.DetailTable.TABLE_NAME);
        db.execSQL("DROP TABLE " + DetailMachineContract.MachineTable.TABLE_NAME);
        db.execSQL("DROP TABLE " + DetailMachineContract.DetailMachineTable.TABLE_NAME);
        onCreate(db);
    }

    public int insertDetailToDB(String detailNumber, int operationNumber, String operationType, Double operationTime){

        int response;

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DetailMachineContract.DetailTable.TABLE_NAME + " WHERE "
                + DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER + " = " + "'" + detailNumber + "' AND "
                + DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER + " = " + operationNumber + " ;";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        while (cursor.moveToNext()){
            count++;
        }
        cursor.close();
        long insertId ;
        if (count == 0) {
            ContentValues values = new ContentValues();
            values.put(DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER, detailNumber);
            values.put(DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER, operationNumber);
            values.put(DetailMachineContract.DetailTable.COLUMN_TYPE_OF_OPERATION, operationType);
            values.put(DetailMachineContract.DetailTable.COLUMN_TIME_OF_OPERATION, operationTime);
            insertId = db.insert(DetailMachineContract.DetailTable.TABLE_NAME, null, values);

            if (insertId == -1){
               response = 1;

            }
            else {
                response = 2;
            }
        }
        else {
            response = 0;
        }
        return response;
    }

    public int insertMachineToDB(int machineNumber, String machineType){

        int response;

        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT * FROM " + DetailMachineContract.MachineTable.TABLE_NAME
                + " WHERE " + DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER  + " = " + machineNumber +" ;";
        Cursor cursor = db.rawQuery(query,null);
        int count = 0;
        while (cursor.moveToNext()){
            count++;
        }
        cursor.close();
        long insertId ;
        if (count == 0) {
            ContentValues values = new ContentValues();
            values.put(DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER, machineNumber);
            values.put(DetailMachineContract.MachineTable.COLUMN_TYPE_OF_MACHINE, machineType);
            insertId = db.insert(DetailMachineContract.MachineTable.TABLE_NAME, null, values);
            if (insertId == -1){
                response = 1;
            }
            else {
                response = 2;
            }
        }
        else {
            response = 0;
        }

        return  response;
    }

    public ArrayList<Integer> getMachine(String machineType){

        ArrayList<Integer> machineNumberList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT " + DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER + " FROM "
                + DetailMachineContract.MachineTable.TABLE_NAME
                + " WHERE " + DetailMachineContract.MachineTable.COLUMN_TYPE_OF_MACHINE + " = " + "'" + machineType + "'" +" ;";
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            int machineNumber = cursor.getInt(cursor.getColumnIndex(DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER));
            machineNumberList.add(machineNumber);
        }
        cursor.close();
        return machineNumberList;
    }

    public ArrayList<String> getDetailNumberList(){

        ArrayList<String> detailNumberList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT " + DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER + " FROM "
                + DetailMachineContract.DetailTable.TABLE_NAME +" ;";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()){
            detailNumberList.add(cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER)));
        }
        cursor.close();
        return detailNumberList;
    }

    public ArrayList<Integer> getOperationNumberList(String detailNumber){
        ArrayList<Integer> operationNumberList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT " + DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER + " FROM "
                + DetailMachineContract.DetailTable.TABLE_NAME + " WHERE " + DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER + " = "
                + "'" + detailNumber + "' ;";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            operationNumberList.add(cursor.getInt(cursor.getColumnIndex(DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER)));
        }
        cursor.close();
        return operationNumberList;
    }

    public void insertProvisionToDB(Integer batchNumber, Integer quantityDetail, String detailNumber, ArrayList<Integer> operationNumbers){
        SQLiteDatabase db = this.getWritableDatabase();
        for (Integer operationNumber : operationNumbers ) {
            String query = " SELECT " + DetailMachineContract.DetailTable.COLUMN_TYPE_OF_OPERATION
                    + ", " + DetailMachineContract.DetailTable.COLUMN_TIME_OF_OPERATION
                    + " FROM "
                    + DetailMachineContract.DetailTable.TABLE_NAME + " WHERE "
                    + DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER + " = " + "'" + detailNumber + "'"
                    + " AND " + DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER + " = " + operationNumber + " ;";
            Cursor cursor = db.rawQuery(query, null);
            String operationType = "";
            Float operationTime = (float)0;
            while (cursor.moveToNext()) {
                operationType = cursor.getString(cursor.getColumnIndex(DetailMachineContract.DetailTable.COLUMN_TYPE_OF_OPERATION));
                operationTime = cursor.getFloat(cursor.getColumnIndex(DetailMachineContract.DetailTable.COLUMN_TIME_OF_OPERATION));
            }
            cursor.close();
            String machineType ="";
            if (operationType.equals("Фрезерная")){
                machineType = "Фрезерный";
            }
            else {
                machineType = "Токарный";
            }
            String query1 = " SELECT " + DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER
                    + " FROM " + DetailMachineContract.MachineTable.TABLE_NAME
                    + " WHERE " + DetailMachineContract.MachineTable.COLUMN_TYPE_OF_MACHINE + " = " + "'"+ machineType + "' AND "
                    + DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_ENDING + " = (SELECT MIN( "
                    + DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_ENDING + ") FROM "
                    + DetailMachineContract.MachineTable.TABLE_NAME + " ) LIMIT 1;";
            Cursor cursor1 = db.rawQuery(query1, null);
            Cursor cursor2 = null;
            String dateOfBeginning = "";
            int machineNumber = 0;
            while (cursor1.moveToNext()) {
                machineNumber = cursor1.getInt(cursor1.getColumnIndex(DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER));
                String text = ((Integer)machineNumber).toString();
                String query2 = "SELECT " + DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_ENDING
                        + " FROM " + DetailMachineContract.MachineTable.TABLE_NAME
                        + " WHERE " + DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER
                        + " = " + machineNumber + " ;";
                cursor2 = db.rawQuery(query2,null);
                while (cursor2.moveToNext()) {
                    dateOfBeginning = cursor2.getString(cursor2.getColumnIndex(DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_ENDING));
                }
                cursor2.close();
            }
             cursor1.close();

             Float operationsTime = operationTime * quantityDetail;
             int countHour = (int) (operationTime*quantityDetail);
             int countMinutes = (int) ((operationsTime*60) - (countHour*60));
            Calendar calendar = Calendar.getInstance();
            java.util.Date date = calendar.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            format1.format(date);
            if (dateOfBeginning.equals("0000-00-00 00:00")){
                dateOfBeginning = format1.format(date);
            }
            else {
                try {
                    date = format1.parse(dateOfBeginning);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Date date1;
            calendar.add(Calendar.HOUR, countHour );
            calendar.add(Calendar.MINUTE, countMinutes);
            date1 = calendar.getTime();
            String dateOfEnding = format1.format(date1);
             ContentValues values = new ContentValues();
             values.put(DetailMachineContract.DetailMachineTable.COLUMN_BATCH_NUMBER, batchNumber);
             values.put(DetailMachineContract.DetailMachineTable.COLUMN_QUANTITY_OF_DETAILS, quantityDetail);
             values.put(DetailMachineContract.DetailTable.COLUMN_DETAIL_NUMBER, detailNumber);
             values.put(DetailMachineContract.DetailTable.COLUMN_OPERATION_NUMBER, operationNumber);
             values.put(DetailMachineContract.DetailTable.COLUMN_TYPE_OF_OPERATION, operationType);
             values.put(DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER, machineNumber);
             values.put(DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_BEGINNING, dateOfBeginning);
             values.put(DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_ENDING, dateOfEnding);
             long insertId = db.insert(DetailMachineContract.DetailMachineTable.TABLE_NAME, null, values);
             String query3 = "UPDATE " + DetailMachineContract.MachineTable.TABLE_NAME + " SET "
                     + DetailMachineContract.DetailMachineTable.COLUMN_DATE_OF_ENDING + " = '" + dateOfEnding + "' WHERE "
                     + DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER + " = " + machineNumber;
             db.execSQL(query3);
        }
    }

    public Cursor getDataFromDetailMachine(Integer machineNumber){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DetailMachineContract.DetailMachineTable.TABLE_NAME + " WHERE "
                + DetailMachineContract.MachineTable.COLUMN_MACHINE_NUMBER + " = " + machineNumber;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
}

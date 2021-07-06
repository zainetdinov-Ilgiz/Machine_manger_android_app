package com.example.machine_manager.data;

import android.provider.BaseColumns;

public final class DetailMachineContract {

    public DetailMachineContract(){
    }

    public static final class DetailTable implements BaseColumns{

        public static final String TABLE_NAME = "Detail";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DETAIL_NUMBER = "Detail_Number";
        public static final String COLUMN_OPERATION_NUMBER = "Operation_Number";
        public static final String COLUMN_TYPE_OF_OPERATION = "Type_Of_Operation";
        public static final String COLUMN_TIME_OF_OPERATION = "Time_Of_Operation";

    }

    public static final class MachineTable implements BaseColumns{

        public static final String TABLE_NAME = "Machine";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_MACHINE_NUMBER = "Machine_Number";
        public static final String COLUMN_TYPE_OF_MACHINE = "Type_Of_Machine";

    }

    public static final class DetailMachineTable implements BaseColumns{

        public static final String TABLE_NAME = "Detail_Machine";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BATCH_NUMBER = "Batch_Number";
        public static final String COLUMN_QUANTITY_OF_DETAILS = "Quantity_Of_details";
        public static final String COLUMN_DATE_OF_BEGINNING = "Date_Of_Beginning";
        public static final String COLUMN_DATE_OF_ENDING = "Date_Of_Ending";

    }
}

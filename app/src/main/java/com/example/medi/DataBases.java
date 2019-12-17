package com.example.medi;

import android.provider.BaseColumns;

public class DataBases {
    public static final class CreateDB implements BaseColumns {
        public static final String codeNum = "codeNumber";
        public static final String name = "name";
        public static final String func = "function";
        public static final String use = "usage";
        public static final String cau = "caution";
        public static final String _TABLENAME0 = "history";
        public static final String _CREATE0 = "CREATE TABLE IF NOT EXISTS "+_TABLENAME0+" ("
                +_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +codeNum+" TEXT NOT NULL , "
                +name+" TEXT NOT NULL , "
                +func+" TEXT NOT NULL , "
                +use+" TEXT NOT NULL , " +cau+" TEXT NOT NULL"+
                ")";
    }

}

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
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +codeNum+" text not null , "
                +name+" text not null , "
                +func+" text not null , "
                +use+" text not null" +cau+"text not null"+
                " );";
    }
}

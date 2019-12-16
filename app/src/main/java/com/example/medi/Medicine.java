package com.example.medi;

import android.database.sqlite.SQLiteDatabase;

public class Medicine {

    public static final String TABLE_NAME = "history";
    public static final String COLUMN_ID = "id";
    public static final String DIV_NM = "divNm";
    public static final String GNL_NM = "gnlNm";
    public static final String FOMNTP_NM = "fomnTpNm";
    public static final String GNL_NM_CD = "gnlNmCd";
    public static final String INJCPTH_NM = "injcPthNm";
    public static final String IQTY_TXT = "iqtyTxt";
    public static final String MEFT_DIV_NO = "meftDivNo";
    public static final String UNIT = "unit";

    private int id;
    private String divNm;
    private String gnlNm;
    private String fomnTpNm;
    private String gnlNmCd;
    private String injcPthNm;
    private String iqtyTxt;
    private String meftDivNo;
    private String unit;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("
            +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +DIV_NM+" TEXT NOT NULL , "
            +GNL_NM+" TEXT NOT NULL , "
            +FOMNTP_NM+" TEXT NOT NULL , "
            +GNL_NM_CD+" TEXT NOT NULL , "
            +INJCPTH_NM+" TEXT NOT NULL , "
            +IQTY_TXT+" TEXT NOT NULL , "
            +MEFT_DIV_NO+" TEXT NOT NULL , "
            +UNIT+" TEXT NOT NULL )";

    public Medicine(){}

    public Medicine(int id, String divNm, String gnlNm, String fomnTpNm, String gnlNmCd, String injcPthNm, String iqtyTxt, String meftDivNo, String unit) {
        this.id = id;
        this.divNm = divNm;
        this.gnlNm = gnlNm;
        this.fomnTpNm = fomnTpNm;
        this.gnlNmCd = gnlNmCd;
        this.injcPthNm = injcPthNm;
        this.iqtyTxt = iqtyTxt;
        this.meftDivNo = meftDivNo;
        this.unit = unit;
    }


    public static String getDivNm() {
        return DIV_NM;
    }

    public void setDivNm(String divNm) {
        this.divNm = divNm;
    }

    public static String getGnlNm() {
        return GNL_NM;
    }

    public void setGnlNm(String gnlNm) {
        this.gnlNm = gnlNm;
    }

    public String getFomnTpNm() {
        return fomnTpNm;
    }

    public void setFomnTpNm(String fomnTpNm) {
        this.fomnTpNm = fomnTpNm;
    }

    public static String getFomntpNm() {
        return FOMNTP_NM;
    }

    public static String getGnlNmCd() {
        return GNL_NM_CD;
    }

    public void setGnlNmCd(String gnlNmCd) {
        this.gnlNmCd = gnlNmCd;
    }

    public String getInjcPthNm() {
        return injcPthNm;
    }

    public void setInjcPthNm(String injcPthNm) {
        this.injcPthNm = injcPthNm;
    }

    public static String getInjcpthNm() {
        return INJCPTH_NM;
    }

    public static String getIqtyTxt() {
        return IQTY_TXT;
    }

    public void setIqtyTxt(String iqtyTxt) {
        this.iqtyTxt = iqtyTxt;
    }

    public static String getMeftDivNo() {
        return MEFT_DIV_NO;
    }

    public void setMeftDivNo(String meftDivNo) {
        this.meftDivNo = meftDivNo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public static String getUNIT() {
        return UNIT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

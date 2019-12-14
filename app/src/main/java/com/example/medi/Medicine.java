package com.example.medi;

public class Medicine {
    public int codeNum;
    public String name;
    public String func;
    public String cau ;
    public String use;

    public int getCodeNum() {
        return codeNum;
    }

    public String getName() {
        return name;
    }

    public String getFunc() {
        return func;
    }

    public String getCau() {
        return cau;
    }

    public String getUse(){
        return use;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public void setCau(String cau) {
        this.cau = cau;
    }
}

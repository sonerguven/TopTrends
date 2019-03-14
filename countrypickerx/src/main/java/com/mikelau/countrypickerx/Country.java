package com.mikelau.countrypickerx;

import android.content.Context;

import java.util.Locale;

public class Country {
    private String isoCode;
    private String dialingCode;
    private boolean selectedIsoCode;

    public Country() {

    }

    public Country(String isoCode, String dialingCode) {
        this.isoCode = isoCode;
        this.dialingCode = dialingCode;
    }
    public Country(String isoCode, String dialingCode, boolean selectedIsoCode) {
        this.isoCode = isoCode;
        this.dialingCode = dialingCode;
        this.selectedIsoCode = selectedIsoCode;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public String getCountryName(Context con) {
        return new Locale(con.getResources().getConfiguration().locale.getLanguage(), isoCode).getDisplayCountry();
    }

    public boolean getSelectedIsoCode(){
        return selectedIsoCode;
    }
    public void setSelectedIsoCode(boolean selectedIsoCode){
        this.selectedIsoCode = selectedIsoCode;
    }
}

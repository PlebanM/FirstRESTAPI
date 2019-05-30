package com.api.Models;

public class BestGenderData {

    private String gender;
    private int year;
    private int limit;

    public BestGenderData() {
    }

    public BestGenderData(String gender, int year, int limit) {
        this.gender = gender;
        this.year = year;
        this.limit = limit;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

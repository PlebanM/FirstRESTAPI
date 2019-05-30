package com.api.Models;

public class BestAge {

    private int age;
    private int year;
    private int limit;

    public BestAge() {
    }

    public BestAge(int age, int year, int limit) {
        this.age = age;
        this.year = year;
        this.limit = limit;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

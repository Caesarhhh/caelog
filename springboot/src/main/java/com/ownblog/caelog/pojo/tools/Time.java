package com.ownblog.caelog.pojo.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Time implements Comparable<Time>{
    public int year;
    public int month;
    public int day;
    public int hour;
    public int min;
    public int sec;

    public Time() {
    }

    public Time(int year, int month, int day, int hour, int min, int sec) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public Time(String s){
        this.year=Integer.parseInt(s.substring(0,4));
        this.month=Integer.parseInt(s.substring(5,7));
        this.day=Integer.parseInt(s.substring(8,10));
        this.hour=Integer.parseInt(s.substring(11,13));
        this.min=Integer.parseInt(s.substring(14,16));
        this.sec=Integer.parseInt(s.substring(17,19));
    }

    @Override
    public String toString() {
        return "time{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", min=" + min +
                ", sec=" + sec +
                '}';
    }

    public static void main(String[] args) {
        Time s1=new Time("2021-02-26 01:07:38");
        Time s2=new Time("2021-02-26 01:05:38");
        Time s3=new Time("2021-02-26 05:07:38");
        Time s4=new Time("2021-02-26 04:07:38");
        Time s5=new Time("2021-02-26 03:07:38");
        List<Time> times=new ArrayList<Time>();
        times.add(s1);
        times.add(s2);
        times.add(s3);
        times.add(s4);
        times.add(s5);
        Collections.sort(times);
        System.out.println(times);
    }

    @Override
    public int compareTo(Time o) {
        if(this.year-o.year!=0){
            return this.year-o.year;
        }
        else{
            if(this.month-o.month!=0){
                return this.month-o.month;
            }
            else{
                if(this.day-o.day!=0){
                    return this.day-o.day;
                }
                else{
                    if(this.hour-o.hour!=0){
                        return this.hour-o.hour;
                    }
                    else{
                        if(this.min-o.min!=0){
                            return this.min-o.min;
                        }
                        else{
                                return this.sec-o.sec;
                        }
                    }
                }
            }
        }
    }
}

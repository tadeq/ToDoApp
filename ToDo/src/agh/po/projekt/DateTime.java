package agh.po.projekt;

import java.io.Serializable;

/*
    class DateTime implements Comparable,Serializable:

    DateTime(String date, String time);
    DateTime(String date);
    DateTime(int d, int m, int y);
    String getDate();
    String getTime();
    int getYear();
    int getMonth();
    int getDay();
    int getHour();
    int getMinutes();
    String toString();
    boolean equals(DateTime dt);
    boolean dateEquals(DateTime dt);
    boolean dateIsCorrect();
    boolean timeIsCorrect();
    int calcDayOfWeek ();
    DateTime nextDay();
    DateTime prevDay();
    int compareTo();
 */


public class DateTime implements Comparable<DateTime>,Serializable{
    private String date;
    private String time;

    public DateTime(String date, String time){
        this.date=date;
        this.time=time;
        if (!this.dateIsCorrect() || !this.timeIsCorrect())
            throw new IllegalArgumentException("Niepoprawna data lub czas.");
    }

    public DateTime(String date){
        this.date=date;
        this.time="";
        if (!this.dateIsCorrect())
            throw new IllegalArgumentException("Niepoprawna data.");
    }

    public DateTime(int d,int m, int y){
        if (m<10)
            this.date=d+"-0"+m+"-"+y;
        else this.date=d+"-"+m+"-"+y;
        this.time="";
    }

    public String getDate(){ return this.date; }

    public String getTime(){ return this.time; }

    public int getYear(){
        if (this.date.length()==10)
            return Integer.parseInt(this.date.substring(6));
        else return Integer.parseInt(this.date.substring(5));
    }

    public int getMonth(){
        if (this.date.length()==10)
            return Integer.parseInt(this.date.substring(3,5));
        else return Integer.parseInt(this.date.substring(2,4));
    }

    public int getDay(){
        if (this.date.length()==10)
            return Integer.parseInt(this.date.substring(0,2));
        else return Integer.parseInt(this.date.substring(0,1));
    }

    public int getHour(){
        if (this.time.length()==5)
            return Integer.parseInt(this.time.substring(0,2));
        else return Integer.parseInt(this.time.substring(0,1));
    }

    public int getMinutes(){
        if (this.time.length()==5)
            return Integer.parseInt(this.time.substring(3));
        else return Integer.parseInt(this.time.substring(2));
    }

    @Override
    public String toString() {
        return (this.date+" "+this.time);
    }

    public boolean equals(DateTime dt) {
        return (this.date.equals(dt.getDate()) && this.time.equals(dt.getTime()));
    }

    public boolean dateEquals (DateTime dt) {return (this.date.equals(dt.getDate()));}

    public boolean dateIsCorrect(){
        int[] days={31,28,31,30,31,30,31,31,30,31,30,31};
        if(this.date.isEmpty()) return false;
        if(this.date.length()!=9 && this.date.length()!=10) return false;
        if(this.date.length()==10 && (this.date.charAt(2)!='-' || this.date.charAt(5)!='-')) return false;
            else if (this.date.length()==9 && (this.date.charAt(1)!='-' || this.date.charAt(4)!='-')) return false;
        try{
            getDay();
            getMonth();
            getYear();
        }catch (NumberFormatException e){
            return false;
        }
        int y=this.getYear();
        if ((y%4==0 & y%100!=0) || (y%400==0)) days[1]=29;
        if (this.getMonth()>12 || this.getMonth()<0 || this.getYear()<1900 || this.getYear()>9999) return false;
        if (this.getDay()<0 || this.getDay()>days[this.getMonth()-1]) return false;
        return true;
    }

    public boolean timeIsCorrect(){
        if(this.time.isEmpty()) return false;
        if(this.time.length()!=4 && this.time.length()!=5) return false;
        if(this.time.length()==5 && this.time.charAt(2)!=':') return false;
            else if (this.time.length()==4 && this.time.charAt(1)!=':') return false;
        try{
            getHour();
            getMinutes();
        }catch (NumberFormatException e){
            return false;
        }
        if (this.getHour()>=24 || this.getHour()<0 || this.getMinutes()>=60 || this.getMinutes()<0) return false;
        return true;
    }

    public int calcDayOfWeek (){
        int[] noOfDays={0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334}; // liczba dni roku na poczatku kazdego miesiaca
        int month=this.getMonth();
        int year=this.getYear();
        int dayOfYear=this.getDay()+noOfDays[month-1];
        int yy,c,g;
        int res;
        if (month>2 && ((year%4==0 & year%100!=0) || (year%400==0))) dayOfYear++;
        yy=(year-1)%100;
        c=(year-1)-yy;
        g=yy+(yy/4);
        res=((((((c / 100) % 4) * 5) + g) % 7)+dayOfYear-1) % 7;
        return res;
    }

    public DateTime nextDay(){
        int[] days={31,28,31,30,31,30,31,31,30,31,30,31};
        int d=this.getDay();
        int m=this.getMonth();
        int y=this.getYear();
        if ((y%4==0 & y%100!=0) || (y%400==0)) days[1]=29;
        if (d<days[m-1]) d++;
        else if (m<=11){
            d=1;
            m++;
        } else {
            d=1;
            m=1;
            y++;
        }
        return new DateTime(d,m,y);
    }

    public DateTime prevDay(){
        int[] days={31,28,31,30,31,30,31,31,30,31,30,31};
        int d=this.getDay();
        int m=this.getMonth();
        int y=this.getYear();
        if ((y%4==0 & y%100!=0) || (y%400==0)) days[1]=29;
        if (d>1) d--;
        else if (m>1){
            m--;
            d=days[m-1];
        } else {
            d=days[11];
            m=12;
            y--;
        }
        return new DateTime(d,m,y);
    }

    @Override
    public int compareTo(DateTime dt) {
        int compareYear= Integer.compare(this.getYear(),dt.getYear());
        if (compareYear!=0) return compareYear;
        int compareMonth= Integer.compare(this.getMonth(),dt.getMonth());
        if (compareMonth!=0) return compareMonth;
        int compareDay= Integer.compare(this.getDay(),dt.getDay());
        if (compareDay!=0 || this.time.isEmpty() || dt.time.isEmpty()) return compareDay;
        int compareHour=Integer.compare(this.getHour(),dt.getHour());
        if (compareHour!=0) return compareHour;
        return (Integer.compare(this.getMinutes(),dt.getMinutes()));
    }
}

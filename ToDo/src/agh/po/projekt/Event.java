package agh.po.projekt;

import java.io.Serializable;

/*
    class Event implements Serializable:

    Event(String name, String startDate, String startTime, String endDate, String endTime, String details);
    String toString();
    String getName();
    DateTime getStartDate();
    DateTime getEndDate();
 */

public class Event implements Serializable{
    private String name;
    private DateTime startDate;
    private DateTime endDate;
    private String details;


    public Event(String name, String startDate, String startTime, String endDate, String endTime, String details){
        if (name.isEmpty())
            throw new IllegalArgumentException("Puste pole wymagane.");
        this.name=name;
        if (startTime.isEmpty()) this.startDate=new DateTime(startDate);
            else this.startDate=new DateTime(startDate,startTime);
        if (endTime.isEmpty()) this.endDate=new DateTime(endDate);
            else this.endDate=new DateTime(endDate,endTime);
        this.details=details;
        if (this.startDate.compareTo(this.endDate)>0)
            throw new IllegalArgumentException("Czas końca nie może być wcześniej niż początku.");
    }

    @Override
    public String toString() {
        if (startDate.getTime().isEmpty() && endDate.getTime().isEmpty()) return ("Wydarzenie: "+this.name);
        else if (endDate.getTime().isEmpty()) return ("Wydarzenie: "+this.name+"\nStart: "+startDate.getTime()+"    "+details);
            else return ("Wydarzenie: "+this.name+"\nStart: "+startDate.getTime()+"   Koniec: "+endDate.getTime()+"    "+details);
    }

    public String getName() {
        return this.name;
    }

    public DateTime getStartDate(){
        return this.startDate;
    }

    public DateTime getEndDate(){
        return this.endDate;
    }
}
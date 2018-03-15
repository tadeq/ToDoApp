package agh.po.projekt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    class Calendar implements Selectable, Serializable:

    Calendar();
    void initScanner()
    void showCalendar();
    void showCalendar(DateTime s,DateTime e);
    void showDay();
    void showDay(DateTime day);
    void showWeek();
    void showWeek(DateTime startDate);
    void addEvent();
    boolean isEvent();
 */

public class Calendar implements Selectable,Serializable{
    private String[] daysNames={"pon","wt","śr","czw","pt","sob","nie"};
    private String[] months={"Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień"};
    private int[] days={31,28,31,30,31,30,31,31,30,31,30,31};
    private List<Event> events;
    private transient Scanner scanner;

    public Calendar(){
        this.scanner=new Scanner(System.in);
        this.events=new ArrayList<>();
    }

    public void initScanner(){
        this.scanner=new Scanner(System.in);
    }

    public void showCalendar() {
        System.out.print("\nPodaj datę, od której wyświetlić kalendarz (DD-MM-YYYY): ");
        String start = scanner.nextLine();
        System.out.print("Podaj datę, do której wyświetlić kalendarz (DD-MM-YYYY): ");
        String end = scanner.nextLine();
        try {
            DateTime s=new DateTime(start);
            DateTime e=new DateTime(end);
            if ((s.compareTo(e))>0)
                throw new IllegalArgumentException("Czas końca nie może być wcześniej niż początku.");
            showCalendar(s, e);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public void showCalendar(DateTime s,DateTime e){
        System.out.println("^ - na ten dzień zaplanowane są wydarzenia");
        int d=s.calcDayOfWeek();
        for (int i=s.getYear();i<=e.getYear();i++){
            if ((i%4==0 & i%100!=0) || (i%400==0)) days[1]=29;
            else days[1]=28;
            for (int j=1;(j<=e.getMonth()) || (j<=12 && i<e.getYear());j++){
                if (i==s.getYear() && j<s.getMonth()) continue;
                else System.out.println(months[j-1]+" "+i);
                for (int k=1;(k<=e.getDay()) || (k<=days[j-1] && j<e.getMonth()) || (k<=days[j-1] && i<e.getYear());k++){
                    if (i==s.getYear() && j==s.getMonth() && k<s.getDay()) continue;
                    else System.out.printf("%5s",k+ " ");
                }
                System.out.println("");
                for (int k=1;(k<=e.getDay()) || (k<=days[j-1] && j<e.getMonth()) || (k<=days[j-1] && i<e.getYear());k++){
                    if (i==s.getYear() && j==s.getMonth() && k<s.getDay()) continue;
                    else System.out.printf("%5s",daysNames[d]+ " ");
                    d=(d+1)%7;
                }
                System.out.println("");
                for (int k=1;(k<=e.getDay()) || (k<=days[j-1] && j<e.getMonth()) || (k<=days[j-1] && i<e.getYear());k++){
                    if (i==s.getYear() && j==s.getMonth() && k<s.getDay()) continue;
                    if (isEvent(k,j,i)) System.out.printf("%5s","  ^ ");
                    else System.out.printf("%5s"," ");
                }
                System.out.println("");
            }
        }
    }

    public void showDay(){
        System.out.print("\nPodaj dzień do wyświetlenia (DD-MM-YYYY): ");
        try {
            String day = scanner.nextLine();
            showDay(new DateTime(day));
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public void showDay(DateTime day){
        System.out.println("\n"+daysNames[day.calcDayOfWeek()]+" "+day.getDate());
        for (int i=0;i<events.size();i++){
            if(day.getDate().equals(events.get(i).getStartDate().getDate())) System.out.println(events.get(i).toString()+"\n");
        }
        System.out.println("\nN-Przejście do następnego dnia    P-Przejście do poprzedniego dnia    D-Usuń wydarzenie    B-Powrót");
        char c=selectOption();
        switch(c) {
            case 'n':
            case 'N':
                showDay(day.nextDay());
                break;
            case 'P':
            case 'p':
                showDay(day.prevDay());
                break;
            case 'd':
            case 'D':
                deleteEvent(day);
                break;
            case 'B':
            case 'b':
                break;
            default:
                System.out.println("Nieprawidłowy klawisz.");
                break;
        }
    }

    public void showWeek(){
        System.out.print("\nPodaj pierwszy dzień tygodnia do wyświetlenia (DD-MM-YYYY): ");
        try {
            String start = scanner.nextLine();
            showWeek(new DateTime(start));
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    public void showWeek(DateTime startDate){
        DateTime endDate=startDate;
        DateTime date=startDate;
        DateTime datePrev=startDate;
        for (int i=0;i<6;i++){
            endDate=endDate.nextDay();
        }
        for (int i=0;i<6;i++){
            datePrev=datePrev.prevDay();
        }
        showCalendar(startDate,endDate);
        for (int i=0;i<=6;i++){
            System.out.println("\n"+daysNames[date.calcDayOfWeek()]+" "+date.getDate());
            for (int j=0;j<events.size();j++){
                if(date.getDate().equals(events.get(j).getStartDate().getDate())) System.out.println(events.get(j).toString());
            }
            date=date.nextDay();
        }
        System.out.println("\nN-Przejście do następnego tygodnia    P-Przejście do poprzedniego tygodnia    B-Powrót");
        char c=selectOption();
        switch(c) {
            case 'n':
            case 'N':
                showWeek(endDate.nextDay());
                break;
            case 'P':
            case 'p':
                showWeek(datePrev.prevDay());
                break;
            case 'B':
            case 'b':
                break;
            default:
                System.out.println("Nieprawidłowy klawisz.");
                break;
        }
    }

    public void addEvent(){
        String name;
        String date;
        String startTime;
        String endTime;
        String details;
        System.out.println("\n* - pola obowiązkowe");
        System.out.print("Nazwa wydarzenia*: ");
        name=scanner.nextLine();
        System.out.print("Data wydarzenia (DD-MM-YYYY)*: ");
        date=scanner.nextLine();
        System.out.print("Czas rozpoczęcia (HH:MM): ");
        startTime=scanner.nextLine();
        System.out.print("Czas zakończenia (HH:MM): ");
        endTime=scanner.nextLine();
        System.out.print("Dodatkowe szczegóły: ");
        details=scanner.nextLine();

        try {
            events.add(new Event(name, date, startTime, date, endTime, details));
            System.out.println("\nDodano wydarzenie.");
        }
        catch(IllegalArgumentException e){
            System.out.println("\n"+e.getMessage()+" Wydarzenie nie zostało utworzone.");
        }
    }

    public void deleteEvent(DateTime day){
        boolean removed=false;
        System.out.print("Podaj nazwę wydarzenia do usunięcia: ");
        String eventName=scanner.nextLine();
        System.out.print("Podaj godzinę rozpoczęcia (opcjonalnie):");
        String eventTime=scanner.nextLine();
        if (new DateTime(day.getDate(),eventTime).timeIsCorrect())
        {
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).getStartDate().getDate().equals(day.getDate()) && events.get(i).getName().equals(eventName)) {
                    if (eventTime.isEmpty()) {
                        removed = true;
                        events.remove(i);
                        i--;
                    } else {
                        if (events.get(i).getStartDate().getTime().equals(eventTime)) {
                            removed = true;
                            events.remove(i);
                            i--;
                        }
                    }
                }
            }
            if (!removed) System.out.println("Nie znaleziono wydarzenia o tej nazwie w tym dniu.");
            else System.out.println("Usunięto wydarzenie o podaniej nazwie.");
        }
        else System.out.println("Błędna godzina. Nie usunięto wydarzenia.");
    }

    public boolean isEvent (int d, int m, int y){
        for (int i=0;i<events.size();i++){
            if(events.get(i).getStartDate().getDay()==d && events.get(i).getStartDate().getMonth()==m &&events.get(i).getStartDate().getYear()==y) return true;
        }
        return false;
    }
}

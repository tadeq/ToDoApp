package agh.po.projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    class AppRunner implements Selectable,Serializable:

    AppRunner();
    ArrayList<Checklist> getChecklists();
    Calendar getCalendar();
    void showMainMenu();
    void showChecklistsMenu();
    void selectChecklist();
    void addChecklist();
    void deleteChecklist();
    void showNotes();
    void addNote();
    void deleteNote();
    void run();
 */

public class AppRunner implements Selectable,Serializable{
    private Calendar calendar;
    private List<Checklist> checklists;
    private transient Scanner scanner;
    private List<Note> notes;

    public AppRunner(){
        scanner=new Scanner(System.in);
        calendar=new Calendar();
        checklists=new ArrayList<>();
        notes=new ArrayList<>();
    }
    public List<Checklist> getChecklists() {
        return this.checklists;
    }
    public Calendar getCalendar() {
        return this.calendar;
    }

    public void showMainMenu(){
        System.out.println("TO-DO");
        char c='0';
        while (c!='Q' && c!='q') {
            System.out.println("\n\nD-Plany na wybrany dzień    W-Plany na wybrany tydzień");
            System.out.println("L-Listy zadań    C-kalendarz    E-Dodaj wydarzenie");
            System.out.println("P-Przypnij notatkę do ekranu głównego    U-Odepnij notatkę od ekranu głównego");
            System.out.println("Q-Wyjście");
            showNotes();
            c=selectOption();
            switch (c) {
                case 'd':
                case 'D':
                    calendar.showDay();
                    break;
                case 'w':
                case 'W':
                    calendar.showWeek();
                    break;
                case 'l':
                case 'L':
                    showChecklistsMenu();
                    break;
                case 'c':
                case 'C':
                    calendar.showCalendar();
                    break;
                case 'e':
                case 'E':
                    calendar.addEvent();
                    break;
                case 'p':
                case 'P':
                    addNote();
                    break;
                case 'u':
                case 'U':
                    deleteNote();
                    break;
                case 'Q':
                case 'q':
                    System.out.println("Wyjście z aplikacji.");
                    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("E://ToDo/ToDoApp.bin"))){
                        outputStream.writeObject(this);
                    } catch(FileNotFoundException e){
                        System.out.println(e.getMessage());
                    } catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                    System.exit(0);
                    break;
                default:
                    System.out.println("Nieprawidłowy klawisz.");
                    break;
            }
        }
    }

    public void showChecklistsMenu() {
        char c='0';
        while (c!='B' && c!='b') {
            System.out.println("\nLISTY ZADAŃ\n");
            System.out.println("A-Dodaj nową listę    S-Wybierz listę do wyświetlenia    D-Usuń listę    B-Powrót");
            for (int i = 0; i < checklists.size(); i++) {
                System.out.println((i + 1) + ". " + checklists.get(i).getName());
            }
            c = selectOption();
            switch (c) {
                case 'A':
                case 'a':
                    addChecklist();
                    break;
                case 'S':
                case 's':
                    selectChecklist();
                    break;
                case 'D':
                case 'd':
                    deleteChecklist();
                    break;
                case 'B':
                case 'b':
                    break;
                default:
                    System.out.println("Nieprawidłowy klawisz");
                    break;
            }
        }
    }

    public void selectChecklist(){
        System.out.print("\nPodaj numer listy do wyświetlenia: ");
        try {
            int n = Integer.parseInt(scanner.nextLine()) - 1;
            checklists.get(n).show();
        } catch(IndexOutOfBoundsException e){
            System.out.println("Brak listy o podanym numerze.");
        } catch(NumberFormatException e){
            System.out.println("Podany znak nie jest liczbą.");
        }
    }

    public void addChecklist(){
        System.out.print("\nPodaj nazwę listy zadań: ");
        String name=scanner.nextLine();
        checklists.add(new Checklist(name));
    }

    public void deleteChecklist(){
        boolean removed=false;
        System.out.print("Podaj numer listy do usunięcia: ");
        try {
            int n = Integer.parseInt(scanner.nextLine());
            checklists.remove(n - 1);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Brak listy o podanym numerze.");
        }catch(NumberFormatException e){
            System.out.println("Podany znak nie jest liczbą.");
        }
    }

    public void showNotes(){
        for (int i=0;i<this.notes.size();i++){
            notes.get(i).showNote(i+1);
        }
    }

    public void addNote(){
        System.out.print("\nPodaj treść notatki (max. 50 znaków): ");
        String text=scanner.nextLine();
        notes.add(new Note(text));
    }

    public void deleteNote(){
        try {
            System.out.print("\nPodaj numer notatki, którą chcesz odpiąć: ");
            int n = Integer.parseInt(scanner.nextLine()) - 1;
            notes.remove(n);
        } catch(IndexOutOfBoundsException e){
            System.out.println("Brak notatki o podanym numerze.");
        } catch(NumberFormatException e){
            System.out.println("Podany znak nie jest liczbą.");
        }
    }

    public void run(){
        scanner=new Scanner(System.in);
        if (this.calendar.equals(null));
            this.calendar = new Calendar();
        if (this.checklists.equals(null))
            this.checklists = new ArrayList<>();
        if (this.notes.equals(null))
            this.notes = new ArrayList<>();
        this.calendar.initScanner();
        showMainMenu();
    }
}

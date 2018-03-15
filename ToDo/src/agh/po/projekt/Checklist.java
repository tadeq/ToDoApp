package agh.po.projekt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    class Checklist implements Selectable, Serializable:

    Checklist();
    void initScanner()
    String getName();
    boolean isDone(int i);
    void addTasks();
    void show();
    void changeToDone();
 */

public class Checklist implements Selectable,Serializable{
    private String name;
    private List<String> list;
    private List<Boolean> done;
    private transient Scanner scanner;

    public Checklist(String name){
        this.scanner=new Scanner(System.in);
        this.name=name;
        this.list = new ArrayList<>();
        this.done = new ArrayList<>();
        addTasks();
    }

    public void initScanner(){
        this.scanner=new Scanner(System.in);
    }

    public String getName(){
        return this.name;
    }

    public boolean isDone(int i){
        return this.done.get(i);
    }

    public void addTasks(){
        initScanner();
        System.out.println("\nPodaj zadania do wykonania. Każde zatwierdź klawiszem ENTER.\n2xENTER aby zapisać. ");
        String task=scanner.nextLine();
        while (!task.isEmpty()){
            list.add(task);
            done.add(false);
            task=scanner.nextLine();
        }
    }

    public void show()  {
        initScanner();
        System.out.println("\nA-Dodaj zadania do listy    E-Oznacz wybrane zadania jako wykonane    B-Powrót\n");
        System.out.println(this.name);
        for (int i=0;i<list.size();i++){
            if (!isDone(i)) System.out.printf("%13s","Niewykonane  ");
            else System.out.printf("%13s","Wykonane     ");
            System.out.println((i+1)+". "+ list.get(i));
        }
        char c=selectOption();
        switch(c){
            case 'A':
            case 'a':
                this.addTasks();
                break;
            case 'E':
            case 'e':
                this.changeToDone();
                break;
            case 'B':
            case 'b':
                break;
            default:
                System.out.println("Nieprawidłowy klawisz");
                break;
        }
    }

    public void changeToDone()  {
        initScanner();
        System.out.println("\nPodaj numery, które chcesz oznaczyć jako wykonane. Każdy zatwierdź klawiszem ENTER.\n2xENTER aby zapisać.");
        String no=scanner.nextLine();
        while(!no.isEmpty()){
            try {
                int i = Integer.parseInt(no);
                done.set(i - 1, true);
                no = scanner.nextLine();
            } catch (IndexOutOfBoundsException e){
                System.out.println("Brak zadania o podanym numerze.");
            } catch (NumberFormatException e){
                System.out.println("Podany znak nie jest liczbą.");
            }
        }
        System.out.println("Zapisano zmiany.");
        this.show();
    }
}

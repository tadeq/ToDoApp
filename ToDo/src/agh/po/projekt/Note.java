package agh.po.projekt;

import java.io.Serializable;

/*
    class Note implements Serializable:

    Note();
    String getText();
    void showNote(int number);
 */

public class Note implements Serializable{
    private String text;

    public Note(String text){
        this.text=text;
        if (this.text.length()>50) this.text=this.text.substring(0,50);
    }

    public String getText(){
        return this.text;
    }

    public void showNote(int number){
        for (int i=0;i<this.text.length()+6;i++) System.out.print("-");
        System.out.print("\n");
        for (int i=0;i<this.text.length()+6;i++){
            if (i==0 || i==this.text.length()+5) System.out.print("|");
            else if (i==1) System.out.print(number);
            else System.out.print(" ");
        }
        System.out.print("\n");
        System.out.println("|  "+this.text+"  |");
        for (int i=0;i<this.text.length()+6;i++){
            if (i==0 || i==this.text.length()+5) System.out.print("|");
            else System.out.print(" ");
        }
        System.out.print("\n");
        for (int i=0;i<this.text.length()+6;i++) System.out.print("-");
        System.out.print("\n");
    }
}

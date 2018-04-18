package agh.po.projekt;

import java.io.*;

public class ToDoApp {
    public static void main(String args[]) {
        AppRunner application = new AppRunner();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("ToDoApp.bin"))) {
            application = (AppRunner) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        application.run();
    }
}

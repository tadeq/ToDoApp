package agh.po.projekt;

import java.util.Scanner;

public interface Selectable {
    default char selectOption() {
        boolean correctOption = true;
        char c = '0';
        Scanner scanner = new Scanner(System.in);
        do {
            correctOption = true;
            try {
                String s = scanner.nextLine();
                if (s.length() > 1)
                    System.out.println("Podano więcej niż jeden znak. Wybrana zostanie opcja odpowiadająca pierwszemu.");
                c = s.charAt(0);
            } catch (IndexOutOfBoundsException e) {
                correctOption = false;
                System.out.println("Nie wybrano żadnej opcji. Wybierz jedną z podanych powyżej.");
            }
        } while (!correctOption);
        return c;
    }

}

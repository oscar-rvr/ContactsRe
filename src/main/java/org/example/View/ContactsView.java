package org.example.View;
import java.util.Scanner;
public class ContactsView {
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu(){
        System.out.println("\n[menu] Enter action (add, list, search, count, exit):");
    }

    public void showListMenu(){
        System.out.println("[list] Enter action ([number], back):");
    }

    public void showRecordMenu(){
        System.out.println("[record] Enter action (edit, delete, menu):");
    }

    public void showSearchMenu(){
        System.out.println("[search] Enter action ([number], back, again):");
    }

    public void showMessage(String message){
        System.out.println(message);
    }

    public String getUserInput(){
        return scanner.nextLine();
    }

}

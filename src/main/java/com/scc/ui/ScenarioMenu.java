package com.scc.ui;

import com.scc.dao.ScenarioDao;
import com.scc.model.ScenarioEntity;

import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {
    private final ScenarioDao dao;
    private final Scanner scanner;

    public ScenarioMenu() {
        this.dao = new ScenarioDao();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            printHeader();
            System.out.println("[1] Add New Order");
            System.out.println("[2] View Active Orders");
            System.out.println("[3] Exit System");
            printDivider();
            System.out.print("Enter choice: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Enter choice (1-3): ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline buffer

            switch (choice) {
                case 1 -> addNewOrder();
                case 2 -> viewActiveOrders();
                case 3 -> System.out.println("\nExiting system... Thank you!");
                default -> System.out.println("\nInvalid choice! Please select between 1 and 3.");
            }
        } while (choice != 3);
    }

    private void addNewOrder() {
        printDivider();
        System.out.println("--- ADD NEW ORDER ---");
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter Drink Item: ");
        String drinkItem = scanner.nextLine();

        System.out.print("Enter Price: ");
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid price. Enter Price: ");
            scanner.next();
        }
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline buffer

        System.out.print("Enter Status (PENDING / PREPARING / COMPLETED): ");
        String status = scanner.nextLine().toUpperCase();

        dao.insertRecord(customerName, drinkItem, price, status);
        System.out.println("\nSUCCESS: Order added to queue!");
    }

    private void viewActiveOrders() {
        List<ScenarioEntity> orders = dao.getAllRecords();

        printDivider();
        System.out.println("           ACTIVE COFFEE ORDERS");
        printDivider();
        System.out.println("+-----+-----------------------+---------------------+-----------+-------------+");
        System.out.println("| ID  | CUSTOMER NAME         | DRINK ITEM          | PRICE     | STATUS      |");
        System.out.println("+-----+-----------------------+---------------------+-----------+-------------+");

        for (ScenarioEntity order : orders) {
            System.out.printf("| %-3d | %-21s | %-19s | %9.2f | %-11s |\n",
                    order.id(),
                    order.customerName(),
                    order.drinkItem(),
                    order.price(),
                    order.status());
        }

        System.out.println("+-----+-----------------------+---------------------+-----------+-------------+");
        System.out.println("Total Records Displayed: " + orders.size());
    }

    private void printHeader() {
        System.out.println("=============================================");
        System.out.println("           COFFEE SHOP ORDERS SYSTEM");
        System.out.println("=============================================");
    }

    private void printDivider() {
        System.out.println("=============================================");
    }
}
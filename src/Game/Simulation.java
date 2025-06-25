package Game;

import Entity.*;
import java.util.Scanner;

public class Simulation {
    private static volatile boolean running = true;
    public static void main(String[] args) {

        World world = World.initAction();

        Thread inputTread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while(running) {
                String input = scanner.nextLine();
                if(input.trim().isEmpty()
                ) {
                    running = false;
                    System.out.println("Остановка");
                }
            }
        });

        inputTread.start();

        int turn = 1;
        while(running) {
            System.out.println("=== Ход " + turn + " ===");
            world.turnAction();
            world.render();
            turn++;

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }















//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter - начать, q - выход");
//
//        while (true){
//            System.out.print(">> ");
//            String input = scanner.nextLine();
//
//            if (input.equalsIgnoreCase("q")){
//                System.out.println("Остановка");
//                break;
//            }
//            world.turnAction();
//            world.render();
//        }
//        scanner.close();
//
////        for (int i = 0; i < 10; i++) {
////            System.out.println("=== Ход " + (i + 1) + " ===");
////            world.turnAction();
////            world.render();
////        }
    }
}

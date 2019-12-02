package SavageCook;
/*
 *
 * @author Danilo Sambugaro created on 26/11/2019 inside the package - SavageCook
 *
 */

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        LargePot largePot = new LargePot(10);
        Cook cook = new Cook(largePot);
        Savage savage = new Savage(largePot);

        Random r = new Random();
        int qtSavages = r.nextInt((10 - 2) + 1) + 2; // Gera um número aleatório entre 2 e 10

        Thread cookThread = new Thread(cook, "Cook");
        cookThread.start();

        // Cria qtSavages threads de Savagas e as inicia
        for (int i = 0; i < qtSavages; i++) {
            Thread savageThread = new Thread(savage, "Savage " + i);
            savageThread.start();
        }
    }
}

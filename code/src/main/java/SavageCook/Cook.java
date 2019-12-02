package SavageCook;
/*
 *
 * @author Danilo Sambugaro created on 26/11/2019 inside the package - SavageCook
 *
 */

import java.util.Random;

public class Cook implements Runnable {

    private LargePot pot;

    public Cook(LargePot pot) {
        this.pot = pot;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("[ " + Thread.currentThread().getName() + " ] Cooking...");
                Random r = new Random();
                // Gera um número aleatório entre 5000 e 15000
                int sleepTime = r.nextInt((15000 - 5000) + 1) + 5000;
                Thread.sleep(sleepTime); // Dorme por sleepTime milisegundos
                System.out.println("[ " + Thread.currentThread().getName() + " ] Done!");
                pot.putServings();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

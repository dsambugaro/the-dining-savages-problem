package SavageCook;
/*
 *
 * @author Danilo Sambugaro created on 26/11/2019 inside the package - SavageCook
 *
 */

import java.util.Random;

public class Savage implements Runnable {

    private LargePot pot;

    public Savage(LargePot pot) {
        this.pot = pot;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int served = pot.getServed();
                System.out.println("[ " + Thread.currentThread().getName() + " ] Get served! Eating...");
                Random r = new Random();
                // Gera um número aleatório entre 1000 e 5000
                int sleepTime = r.nextInt((5000 - 1000) + 1) + 1000;
                Thread.sleep(sleepTime); // Dorme por sleepTime milisegundos
                System.out.println("[ " + Thread.currentThread().getName() + " ] Done!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

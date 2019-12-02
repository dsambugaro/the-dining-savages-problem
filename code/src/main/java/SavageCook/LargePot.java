package SavageCook;
/*
 *
 * @author Danilo Sambugaro created on 26/11/2019 inside the package - SavageCook
 *
 */

import java.util.concurrent.Semaphore;

public class LargePot {
    private int servings = 0;
    private int size;
    private Semaphore mutex = new Semaphore(1);
    private Semaphore empty = new Semaphore(0);
    private Semaphore full = new Semaphore(0);

    public LargePot(int size) {
        this.size = size;
    }

    public void putServings() throws InterruptedException {
        empty.acquire();
        this.servings = this.size;
        System.out.println("[ " + Thread.currentThread().getName() + " ] The pot is full again!");
        full.release();
    }

    public int getServed() throws InterruptedException {
        mutex.acquire();

        if (this.isEmpty()) {
            System.out.println("[ " + Thread.currentThread().getName() + " ] The pot is empty! Waking up cook...");
            empty.release();
            full.acquire();
        }

        int s = --this.servings;
        mutex.release();
        return s;
    }

    private boolean isEmpty(){
        return this.servings == 0;
    }
}


# The dining savages problem

### Descrição do problema

Uma tribo de selvagens come jantares comunitários de uma panela grande que pode conter M porções de missionário recheado.
Quando um selvagem quer comer, ele se serve da panela, a menos que esta esteja vazia.
Se a panela estiver vazia, este selvagem acorda o cozinheiro e aguarda até que o cozinheiro volte a encher a panela.

Qualquer número de selvagens rodam o código:

```
while (true) {
    getServingFromPot();
    eat();
}
```

E um cozinheiro roda o código:

```
while (true) {
    putServingsInPot(M);
}
```

O desafio é sincronizar os códigos. As restrições de sincronização são:
  * O selvagens não pode chamar //getServingFromPot// se o pote estiver vazio.
  * O cozinheiro pode chamar //putServingsInPot// apenas se a panela estiver vazia.

### Solução implementada em Java

Classe principal: Instancia o pote, o cozinheiro e os selvagens

```
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
```

Cozinheiro: Cozinha e enche o pote
```
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
```

Selvagem: Se serve do pote e come
```
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
```

Pote largo: Guarda as porções e também toda a lógica de sincronização

```
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
```

### Referências

[A.B.: The Little Book of Semaphores](http://greenteapress.com/semaphores/LittleBookOfSemaphores.pdf|Downey)

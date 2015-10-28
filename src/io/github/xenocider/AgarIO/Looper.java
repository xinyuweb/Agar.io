package io.github.xenocider.AgarIO;


import io.github.xenocider.AgarIO.SexyStuff.IdiotBox;

public class Looper {

    public static long initialTime = System.nanoTime();
    public static long finalTime;
    public static double timeDelta;
    public int dediredFPS = 60;
    public int FPS;
    private static int sleepTime = 1;

    public static void mainLoop(){

        while(true){

            finalTime = System.nanoTime();

            timeDelta = finalTime - initialTime;

            //System.out.println(timeDelta / 100000000000d);
            System.out.println(finalTime);
            //System.out.println(initialTime);

            try {
                Thread.sleep(sleepTime);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

        }
    }
}
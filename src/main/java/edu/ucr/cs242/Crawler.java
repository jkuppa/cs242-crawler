package edu.ucr.cs242;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Crawler {

    private BlockingQueue queue;

    public Crawler() {
        this.queue = new LinkedBlockingQueue();
    }

    public static void main(String[] args) {

        try {
            Crawler c = new Crawler();
            c.go("http://en.wikipedia.org/");

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void go(String url) {

        try {

            queue.add(url);

            (new Thread(new Scheduler(queue))).start();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

package edu.ucr.cs242;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;

public class Scheduler implements Runnable {

    private BlockingQueue queue;

    private HashSet<String> completedSet = new HashSet<>();

    public Scheduler(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {

        try {

            String url = (String) queue.peek();

            while (url != null) {

                if (url.isEmpty()) {
                    continue;
                }

                try {

                    //only pass URL to downloader if we have not seen the URL before
                    if (!completedSet.contains(url)) {
                        Downloader downloader = new Downloader(queue, url);
                        if (downloader.go()) {
                            queue.remove(url);
                            completedSet.add(url);
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Thread.sleep(2000);
                url = (String) queue.peek();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}

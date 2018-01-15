package edu.ucr.cs242;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

public class Scheduler implements Runnable {

    private BlockingQueue queue;

    private HashSet<String> completedSet = new HashSet<>();

    ExecutorService executor = Executors.newFixedThreadPool(4);

    public Scheduler(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {

        List<Future<DownloaderResult>> futures = new ArrayList<>();

       // CompletionService<DownloaderResult> completionService =
        //        new ExecutorCompletionService<DownloaderResult>(executor);

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
                        Future<DownloaderResult> future = executor.submit(downloader);
                        futures.add(future);

                        queue.remove(url);
                        completedSet.add(url);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Thread.sleep(3000);
                url = (String) queue.peek();
            }

            //now retrieve the futures after computation (auto wait for it)

            /*
            int received = 0;
            while(received < futures.size()) {
                Future<DownloaderResult> resultFuture = completionService.take();
                DownloaderResult result = resultFuture.get();
                received ++;
            }
             */

            executor.shutdown();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

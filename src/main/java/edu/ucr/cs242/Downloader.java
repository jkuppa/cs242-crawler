package edu.ucr.cs242;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.concurrent.Callable;

public class Downloader implements Callable<DownloaderResult> {

    private String urlToDownload;
    private Queue queue;

    public Downloader(Queue queue, String urlToDownload) {
        this.queue = queue;
        this.urlToDownload = urlToDownload;
    }


    /**
     * Download the URL, collect and post URLs to queue,
     * and send the page data to the indexer
     */
    @Override
    public DownloaderResult call() throws Exception {

        DownloaderResult result = new DownloaderResult(this.urlToDownload);

        try {

            long start = System.currentTimeMillis();

            Document doc = Jsoup
                    .connect(this.urlToDownload)
                    .userAgent("Mozilla/5.0 (compatible; cs242-crawler/1.0; +https://github.com/jorgemariomercado/cs242-crawler/wiki/Bot)")
                    .get();

            long stop = System.currentTimeMillis();
            result.setDownloadTime(stop - start);

            Elements e = doc.getElementsByTag("html");
            if (e.hasAttr("lang") && !e.attr("lang").equals("en")) {
                result.setResult(true);
                result.setSkipped(Boolean.TRUE);
                return result;
            }

            // 1. Get all URLs in page and post to queue
            Elements anchors = doc.getElementsByTag("a");

            Set<String> urls = new HashSet<>();
            for (Element anchor : anchors) {
                //Page p = new Page(anchor.attr("title"), anchor.absUrl("href"));
                String u = anchor.absUrl("href");
                if (u != null && !u.isEmpty()) {
                    urls.add(u);
                }
            }

            postUrlsToQueue(urls);

            // 2. Send page data to indexer
            indexData(doc.outerHtml());

            result.setResult(true);

            System.out.println(result);
            return result;

        } catch (Exception ex) {
            result.setThrowable(ex);
            result.setResult(false);
            System.out.println(result);
            return result;
        }
    }

    /**
     * @param urls
     */
    private void postUrlsToQueue(Set<String> urls) {
        for (String s : urls) {
            queue.add(s);
        }
    }

    private void indexData(Object data) {

        // index the data here
    }
}

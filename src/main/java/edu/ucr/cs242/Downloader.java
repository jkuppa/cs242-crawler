package edu.ucr.cs242;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class Downloader {

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
    public boolean go() {

        try {

            System.out.printf("Fetching page: %s", this.urlToDownload);
            Document doc = Jsoup.connect(this.urlToDownload).get();

            Elements e = doc.getElementsByTag("html");
            if(e.hasAttr("lang") && ! e.attr("lang").equals("en")) {
                System.out.printf(" (wrong lang: %s)\n", e.attr("lang"));
                return true;
            } else {
                System.out.printf("\n");
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

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
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

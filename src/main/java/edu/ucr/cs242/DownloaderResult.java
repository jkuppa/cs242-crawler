package edu.ucr.cs242;

public class DownloaderResult {

    private Boolean result;
    private String url;
    private Throwable throwable;
    private Boolean skipped = Boolean.FALSE;
    private String threadName = Thread.currentThread().getName();
    private long downloadTime = 0;

    public DownloaderResult(String url) {
        this.url = url;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Boolean getSkipped() {
        return skipped;
    }

    public void setSkipped(Boolean skipped) {
        this.skipped = skipped;
    }


    public long getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(long downloadTime) {
        this.downloadTime = downloadTime;
    }


    @Override
    public String toString() {
        return "DownloaderResult{" +
                "result=" + result +
                ", url='" + url + '\'' +
                ", throwable=" + throwable +
                ", skipped=" + skipped +
                ", threadName='" + threadName + '\'' +
                ", downloadTime=" + downloadTime +
                '}';
    }
}

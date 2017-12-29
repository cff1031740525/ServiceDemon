package test.bwei.com.servicedemon.bean;

/**
 * Author:Chen
 * E-mail:1031740525@qq.com
 * Time: 2017/12/28
 * Description:
 */

public class DownloadBean {
    private String url;
    private String threadId;
    private String start;
    private String end;

    public DownloadBean(String url, String threadId, String start, String end) {
        this.url = url;
        this.threadId = threadId;
        this.start = start;
        this.end = end;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}

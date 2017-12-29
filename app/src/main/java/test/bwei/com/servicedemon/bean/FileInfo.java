package test.bwei.com.servicedemon.bean;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Author:Chen
 * E-mail:1031740525@qq.com
 * Time: 2017/12/28
 * Description:
 */

public class FileInfo implements Serializable{
    private String url;
    private String fileName;

    public FileInfo(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

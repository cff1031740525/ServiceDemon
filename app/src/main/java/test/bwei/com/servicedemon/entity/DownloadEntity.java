package test.bwei.com.servicedemon.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author:Chen
 * E-mail:1031740525@qq.com
 * Time: 2017/12/28
 * Description:
 */
@Entity
public class DownloadEntity {
    @Id
    private Long Id;
    private String threadId;
    private String start;
    private String end;
    public String getEnd() {
        return this.end;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    public String getStart() {
        return this.start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getThreadId() {
        return this.threadId;
    }
    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    @Generated(hash = 737027990)
    public DownloadEntity(Long Id, String threadId, String start, String end) {
        this.Id = Id;
        this.threadId = threadId;
        this.start = start;
        this.end = end;
    }
    @Generated(hash = 1671715506)
    public DownloadEntity() {
    }
}

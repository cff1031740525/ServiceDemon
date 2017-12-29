package test.bwei.com.servicedemon;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import test.bwei.com.servicedemon.bean.DownloadBean;
import test.bwei.com.servicedemon.entity.DownloadEntity;

/**
 * Author:Chen
 * E-mail:1031740525@qq.com
 * Time: 2017/12/29
 * Description:
 */

public class DownloadThread extends Thread{
    private DownloadBean downloadBean;
    private File file;
    private DownloadEntityDao userDao;
    private boolean stopFlag;

    public DownloadThread(DownloadBean downloadBean, File file, DownloadEntityDao userDao, boolean stopFlag) {
        this.downloadBean = downloadBean;
        this.file = file;
        this.userDao = userDao;
        this.stopFlag = stopFlag;
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }

    @Override
    public void run() {
        RandomAccessFile raf;
        try {

            URL url = new URL(downloadBean.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            raf = new RandomAccessFile(file, "rwd");
            raf.seek(Long.parseLong(downloadBean.getStart()));
            con.setConnectTimeout(5000);
            con.setRequestMethod("GET");
            con.setRequestProperty("Range", "bytes=" + downloadBean.getStart() + "-" + downloadBean.getEnd());
            con.connect();
            long totle = Long.parseLong(downloadBean.getStart());
            if (con.getResponseCode() == 206) {
                InputStream inputStream = con.getInputStream();
                int len = 0;
                byte[] bytes = new byte[2 * 1024];

                DownloadEntity load = userDao.load(Long.parseLong(downloadBean.getThreadId()) + 1);
                if (load == null) {
                    DownloadEntity downloadEntity = new DownloadEntity(null, downloadBean.getThreadId(), totle + "", downloadBean.getEnd());
                    userDao.insert(downloadEntity);
                }
                //获取数据库的一个对象
                DownloadEntity unique  = userDao.queryBuilder().where(DownloadEntityDao.Properties.ThreadId.eq(downloadBean.getThreadId())).unique();
                while ((len = inputStream.read(bytes)) != -1) {
                    if (stopFlag) {
                        raf.write(bytes, 0, len);
                        totle = totle + len;
                        unique.setStart(totle + "");
                        userDao.update(unique);
                        DownloadEntity load1 = userDao.load(Long.parseLong(downloadBean.getThreadId()) + 1);
                        String start = load1.getStart();
                        System.out.println(start + "==============" + downloadBean.getThreadId());
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }
}

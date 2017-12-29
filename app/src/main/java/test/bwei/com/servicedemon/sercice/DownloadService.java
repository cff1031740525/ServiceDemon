package test.bwei.com.servicedemon.sercice;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import test.bwei.com.servicedemon.DaoMaster;
import test.bwei.com.servicedemon.DaoSession;
import test.bwei.com.servicedemon.DownloadEntityDao;
import test.bwei.com.servicedemon.DownloadThread;
import test.bwei.com.servicedemon.bean.DownloadBean;
import test.bwei.com.servicedemon.bean.FileInfo;
import test.bwei.com.servicedemon.entity.DownloadEntity;

/**
 * Author:Chen
 * E-mail:1031740525@qq.com
 * Time: 2017/12/27
 * Description:
 */

public class DownloadService extends Service {
    public static final String START = "START";
    public static final String STOP = "STOP";
    private DownloadEntityDao userDao = null;
    private DaoMaster.DevOpenHelper devOpenHelper = null;
    private DaoMaster daoMaster = null;
    private DaoSession daoSession = null;
    private int THREAD_COUNT = 5;
    private FileInfo fileInfo;
    private File file;
    private RandomAccessFile raf;
    private boolean stopFlag = true;
    private DownloadThread downloadThread;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (START.equals(intent.getAction())) {
            if(downloadThread!=null){
                downloadThread.setStopFlag(true);
            }
            fileInfo = (FileInfo) intent.getSerializableExtra("fileinfo");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //首先创建一个连接拿到下载文件的大小
                    try {
                        URL url = new URL(fileInfo.getUrl());
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.connect();
                        if (con.getResponseCode() == 200) {
                            raf = new RandomAccessFile(file, "rwd");
                            int contentLength = con.getContentLength();
                            raf.setLength(contentLength);
                            int blocksize = contentLength / THREAD_COUNT;
                            List<DownloadEntity> downloadEntities = userDao.loadAll();

                            DownloadBean downloadBean = null;
                            for (int threadId = 0; threadId < THREAD_COUNT; threadId++) {
                                //现在需要用到的参数
                                System.out.println("");
                                if (downloadEntities == null || downloadEntities.size() == 0) {
                                    int startpoint = threadId * blocksize;
                                    int endpoint = blocksize * (threadId + 1);
                                    if (threadId == THREAD_COUNT - 1) {
                                        endpoint = contentLength - 1;
                                    }
                                    downloadBean = new DownloadBean(fileInfo.getUrl(), threadId + "", startpoint + "", endpoint + "");
                                } else {
                                    System.out.println(downloadEntities.size() + "++++++++++++++++++++++++");
                                    DownloadEntity downloadEntity = downloadEntities.get(threadId);
                                    String start = downloadEntity.getStart();
                                    int endpoint = blocksize * (threadId + 1);
                                    if (threadId == THREAD_COUNT - 1) {
                                        endpoint = contentLength - 1;
                                    }
                                    downloadBean = new DownloadBean(fileInfo.getUrl(), threadId + "", start + "", endpoint + "");
                                }
                                downloadThread = new DownloadThread(downloadBean, file, userDao, stopFlag);
                                downloadThread.start();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //下载方法  可以让多个子线程去运行这个方法   也就是多线程下载

                }
            }).start();

        } else {
            stopFlag = false;
            if(downloadThread!=null){
                downloadThread.setStopFlag(stopFlag);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        devOpenHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        userDao = daoSession.getDownloadEntityDao();
        file = new File(Environment.getExternalStorageDirectory() + "/download/ss.apk");
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

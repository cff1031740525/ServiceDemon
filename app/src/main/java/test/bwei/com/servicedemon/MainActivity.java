package test.bwei.com.servicedemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import test.bwei.com.servicedemon.bean.FileInfo;
import test.bwei.com.servicedemon.sercice.DownloadService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_apkName;
    private ProgressBar prob;
    private Button start;
    private Button stop;
    private FileInfo fileInfo=null;
    private static final String url="http://wap.apk.anzhi.com/data1/apk/201712/20/6aac2ed152b3e3f004141d954b31b4d8_63285200.apk";
    private static final String apkName="火山小视屏";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        tv_apkName = findViewById(R.id.tv_fileName);
        prob = findViewById(R.id.prob);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                intent = new Intent(MainActivity.this,DownloadService.class);
                fileInfo=new FileInfo(url,apkName);
                intent.putExtra("fileinfo",fileInfo);
                intent.setAction(DownloadService.START);
                startService(intent);
                break;
            case R.id.stop:
           intent=new Intent(MainActivity.this,DownloadService.class);
           /// intent.putExtra("flag",false);
                intent.setAction(DownloadService.START);
                startService(intent);
                break;
        }
    }
}

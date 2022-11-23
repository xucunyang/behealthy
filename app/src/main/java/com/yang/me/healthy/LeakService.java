package com.yang.me.healthy;

import android.os.Environment;
import android.util.Log;

import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.HeapDump;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LeakService extends DisplayLeakService {
    @Override
    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
        Log.d("LeakUploadService", "内存泄漏信息:leakInfo->" + leakInfo);
        String filePath = Environment.getExternalStorageDirectory() + "/leak.txt";
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file, true);
            fileWriter.write(leakInfo);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e2) {
                }
            }
        }

        new ThreadPoolExecutor(2, 4, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
                , new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
    }

}

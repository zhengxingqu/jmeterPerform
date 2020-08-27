package com.example.demo.xnservice;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class test {
    //    private long lastTimeFileSize = 0;  //上次文件大小

    /**
     * 实时输出日志信息
     *
     * @throws IOException
     */
    public String realtimeShowLog(String scriptName) throws IOException, InterruptedException {

        String scriptPath = "C:\\Users\\Administrator\\Desktop\\demo3\\" + scriptName;
        //指定文件可读可写
        final RandomAccessFile randomFile = new RandomAccessFile(scriptPath, "rw");
        StringBuffer t1 = new StringBuffer();
        Thread t = new Thread(() -> {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("file"));
                Long lastTimeFileSize = Long.valueOf(bufferedReader.readLine());
                bufferedReader.close();
                String tmp = "";
                //获得变化部分的
                randomFile.seek(lastTimeFileSize);
                while ((tmp = randomFile.readLine()) != null) {
                    t1.append(tmp + "\n");
                }
                if(t1.toString().contains("generating dashboard")){
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("file"));
                    bufferedWriter.write(String.valueOf("0"));
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }else {
                    lastTimeFileSize = randomFile.length();
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("file"));
                    bufferedWriter.write(String.valueOf(lastTimeFileSize));
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        t.join();
        return t1.toString();
    }

    public static void main(String[] args) throws Exception {
        test view = new test();
        System.out.println(view.realtimeShowLog("jmeter.log"));
    }

}
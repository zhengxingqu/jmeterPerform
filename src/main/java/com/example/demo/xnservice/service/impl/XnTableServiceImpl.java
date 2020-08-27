package com.example.demo.xnservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.xnservice.entity.ScriptInfo;
import com.example.demo.xnservice.entity.XnTable;
import com.example.demo.xnservice.mapper.XnTableMapper;
import com.example.demo.xnservice.service.ScriptInfoService;
import com.example.demo.xnservice.service.XnTableService;
import com.example.demo.xnservice.vo.ScriptModifyVo;
import com.example.demo.xnservice.vo.XnVo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhengxingqu
 * @since 2020-08-13
 */
@Service
public class XnTableServiceImpl extends ServiceImpl<XnTableMapper, XnTable> implements XnTableService {
    private final Logger logger = LoggerFactory.getLogger(XnTableServiceImpl.class);

    @Autowired
    private ScriptInfoService scriptInfoService;

    @Override
    public String saveXn(XnVo xnVo) {
        XnTable xnTable = new XnTable();
        BeanUtils.copyProperties(xnVo, xnTable);
        this.save(xnTable);
        return xnTable.getId();
    }

    @Override
    public void updateXn(XnVo xnVo) {
        XnTable xnTable = new XnTable();
        BeanUtils.copyProperties(xnVo, xnTable);
        this.updateById(xnTable);
    }

    @Override
    public void uploadFile(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        String fileName = multipartFile.getOriginalFilename();
        try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(fileName))) {
            Integer len = 0;
            byte[] r = new byte[1024];
            while ((len = inputStream.read(r)) != -1) {
                output.write(r, 0, len);
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        Map map = new HashMap();
        map.put("scriptName", fileName);
        String content = readScriptContent(map);
        List<XnTable> list = searchXnByName(fileName);
        XnTable xnTable = new XnTable();
        if (list.size() > 0) {
            xnTable.setId(list.get(0).getId());
            xnTable.setScriptName(fileName);
            xnTable.setRampTime(setRampTime(content));
            xnTable.setRunDurationTime(setDurionTime(content));
            xnTable.setThreads(setThreads(content));
            this.updateById(xnTable);
        } else {
            xnTable.setScriptName(fileName);
            xnTable.setRampTime(setRampTime(content));
            xnTable.setRunDurationTime(setDurionTime(content));
            xnTable.setThreads(setThreads(content));
            this.save(xnTable);
        }


    }

    public String realtimeShowLog(String id) throws IOException, InterruptedException {
        String logName = this.getById(id).getLogName();
        String scriptPath = "C:\\Users\\Administrator\\Desktop\\demo3\\" + logName;
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
                if (t1.toString().contains("generating dashboard") || t1.toString().contains("Dashboard generated")) {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("file"));
                    bufferedWriter.write(String.valueOf("0"));
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } else {
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

    public String setThreads(String content) {
        String pattern = "<stringProp name=\"ThreadGroup.num_threads\">(\\d+).*";
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        XnTable xnTable = new XnTable();
        if (m.find()) {
            xnTable.setThreads(m.group(1));
            return m.group(1);
        }
//        this.save(xnTable);
        return null;
    }

    public String setRampTime(String content) {
        String pattern = "<stringProp name=\"ThreadGroup.ramp_time\">(\\d+).*";
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        XnTable xnTable = new XnTable();
        if (m.find()) {
            xnTable.setRampTime(m.group(1));
            return m.group(1);
        }
        return null;
    }

    public String setDurionTime(String content) {
        String pattern = "<stringProp name=\"ThreadGroup.duration\">(\\d+).*";
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        XnTable xnTable = new XnTable();
        if (m.find()) {
            xnTable.setRunDurationTime(m.group(1));
            return m.group(1);
        }
        return null;

    }

    @Override
    public List<XnTable> searchXnByName(String scriptName) {
        QueryWrapper<XnTable> queryWrapper = new QueryWrapper<XnTable>();
        queryWrapper.like("script_name", scriptName);
        ArrayList<XnTable> arrayList = new ArrayList<>();
        if (!StringUtils.isEmpty(scriptName)) {
            arrayList = (ArrayList<XnTable>) this.list(queryWrapper);
        }
        return arrayList;
    }

    @Override
    public void runSingleScript(XnTable xnTable) throws IOException, InterruptedException {
        String scriptName = xnTable.getScriptName();
        String resultFileName = scriptName.split("\\.")[0] + "_result.jtl";
        String scriptName1 = "C:/Users/Administrator/Desktop/demo3/" + scriptName;
        Random random = new Random();
        String reportName = Integer.valueOf(random.nextInt()).toString() + "report";
        String logName = scriptName + "jmeter.log";
        String command = String.format("cmd /c jmeter -n -t %s -l %s -e -o %s -j %s", scriptName1, resultFileName, reportName, logName);
        try {
            Runtime.getRuntime().exec(command).waitFor();
            xnTable.setRunResult("成功");
            xnTable.setResultFile(resultFileName);
            xnTable.setLogName(logName);
            this.updateById(xnTable);
        } catch (Exception e) {
            logger.error(String.valueOf(e.getStackTrace()));
            xnTable.setLogName(logName);
            xnTable.setRunResult("失败");
            this.updateById(xnTable);
        }
        String reportUrl = "C:/Users/Administrator/Desktop/demo3/" + reportName + "/" + "statistics.json";
        readReportFile(reportUrl, scriptName);
    }

    @Override
    public void modifyNumThreads(Map script) throws IOException {
        this.replaceThreadsNumber(script);
        this.replaceDurionTime(script);
        this.replaceRampTime(script);
//        File file = new File()
    }

    public String readScriptContent(Map script) throws IOException {
        String scriptName1 = script.get("scriptName").toString();
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(scriptName1), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String tempString = null;
        StringBuffer stringBuffer = new StringBuffer();
        while ((tempString = bufferedReader.readLine()) != null) {
            stringBuffer.append(tempString + "\n");
        }
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }

    public void replaceThreadsNumber(Map script) throws IOException {
        String content = readScriptContent(script);
        String pattern = "(<stringProp name=\"ThreadGroup.num_threads\">)(\\d+)?";
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        if (m.find()) {
            String newContent = content.replace(m.group(0), m.group(0).replace(m.group(0), m.group(1) + script.get("threads")));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(script.get("scriptName").toString()));
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(newContent, 0, newContent.length());
            bufferedWriter.close();
        }
    }

    public void replaceRampTime(Map script) throws IOException {
        String content = readScriptContent(script);
        String pattern = "(<stringProp name=\"ThreadGroup.ramp_time\">)(\\d+)?";
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        if (m.find()) {
            String newContent = content.replace(m.group(0), m.group(0).replace(m.group(0), m.group(1) + script.get("rampTime")));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(script.get("scriptName").toString()));
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(newContent, 0, newContent.length());
            bufferedWriter.close();
        }
    }

    public void replaceScriptContent(ScriptModifyVo scriptModifyVo) throws IOException {
        String content = scriptModifyVo.getContent();
        String scriptName = scriptModifyVo.getScriptName();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("C:/Users/Administrator/Desktop/demo3/" + scriptName));
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(content, 0, content.length());
        bufferedWriter.close();
        scriptModifyVo.setRampTime(setRampTime(content));
        scriptModifyVo.setRunDurationTime(setDurionTime(content));
        scriptModifyVo.setThreads(setThreads(content));
        XnTable xnTable = new XnTable();
        BeanUtils.copyProperties(scriptModifyVo, xnTable);
        this.updateById(xnTable);


    }

    @Override
    public void downloadScript(List<String> ids) {
        List<String> urlList = new ArrayList<>();
        for (String id : ids) {
            Thread thread = new Thread(() -> {
                String scriptPath = this.getById(id).getScriptName();
                urlList.add(scriptPath);
            });
            thread.start();
        }


    }

    @Override
    public void runManyScript(List<String> ids) {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (String id : ids) {
            Thread thread = new Thread(() -> {
                XnTable xnTable = new XnTable();
                String scriptName = this.getById(id).getScriptName();
                String scriptName1 = "C:/Users/Administrator/Desktop/demo3/" + scriptName;
                Random random = new Random();
                String resultFileName = scriptName.split("\\.")[0] + "_result.jtl";
                String reportName = Integer.valueOf(random.nextInt()).toString() + "report";
                String logName = scriptName + "jmeter.log";
                String command = String.format("cmd /c jmeter -n -t %s -l %s -e -o %s -j %s", scriptName1, resultFileName, reportName, logName);
                try {
                    Process proc = Runtime.getRuntime().exec(command);
                    System.out.println(proc.getOutputStream());
                    proc.waitFor();
                    xnTable.setRunResult("成功");
                    xnTable.setResultFile(resultFileName);
                    xnTable.setId(id);
                    xnTable.setScriptName(scriptName);
                    this.updateById(xnTable);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(String.valueOf(e.getStackTrace()));
                    xnTable.setRunResult("失败");
                    this.updateById(xnTable);
                }
                String reportUrl = "C:/Users/Administrator/Desktop/demo3/" + reportName + "/" + "statistics.json";
                try {
                    readReportFile(reportUrl, scriptName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            es.submit(thread);
        }
        es.shutdown();
    }

    public void replaceDurionTime(Map script) throws IOException {
        String content = readScriptContent(script);
        String pattern = "(<stringProp name=\"ThreadGroup.duration\">)(\\d+)?";
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        if (m.find()) {
            String newContent = content.replace(m.group(0), m.group(0).replace(m.group(0), m.group(1) + script.get("runDurationTime")));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("C:/Users/Administrator/Desktop/demo3/" + script.get("scriptName").toString()));
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(newContent, 0, newContent.length());
            bufferedWriter.close();
        }
    }


    public String readReportFile(String reportUrl, String scriptName) throws IOException {
        String content = null;
        try {
            content = FileUtils.readFileToString(new File(reportUrl), "GB2312");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap hashMap = JSON.parseObject(content, HashMap.class);
        for (Object key : hashMap.keySet()) {
            ScriptInfo scriptInfo = new ScriptInfo();
            scriptInfo.setScriptName(scriptName);
            scriptInfo.setTransaction((String) key);
            HashMap hashMap1 = JSON.parseObject(hashMap.get(key).toString(), HashMap.class);
            scriptInfo.setErrorCount(hashMap1.get("errorCount").toString());
            scriptInfo.setMaxRestime(hashMap1.get("maxResTime").toString());
            scriptInfo.setMinRestime(hashMap1.get("minResTime").toString());
            scriptInfo.setMeanRestime(hashMap1.get("meanResTime").toString());
            scriptInfo.setOneRestime(hashMap1.get("pct1ResTime").toString());
            scriptInfo.setTwoRestime(hashMap1.get("pct2ResTime").toString());
            scriptInfo.setThreeRestime(hashMap1.get("pct3ResTime").toString());
            scriptInfo.setThroughput(hashMap1.get("throughput").toString());
            scriptInfo.setTransaction(hashMap1.get("transaction").toString());
            scriptInfoService.save(scriptInfo);
        }
        return content;
    }


}


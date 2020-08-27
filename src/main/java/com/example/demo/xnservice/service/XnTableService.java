package com.example.demo.xnservice.service;

//import com.example.demo.xnservice.entity.XnTable;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.xnservice.vo.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.xnservice.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengxingqu
 * @since 2020-08-13
 */
public interface XnTableService extends IService<XnTable> {
    String saveXn(XnVo xnVo);
    void updateXn(XnVo xnVo);
    void uploadFile(MultipartFile multipartFile) throws IOException;
    List<XnTable> searchXnByName(String scriptName);
    void runSingleScript(XnTable xnTable) throws IOException, InterruptedException;
    void modifyNumThreads(Map script) throws IOException;
    String readScriptContent(Map script) throws IOException;
    void replaceScriptContent(ScriptModifyVo scriptModifyVo) throws IOException;
    void downloadScript(List<String> ids);
    void runManyScript(List<String> ids);
    String realtimeShowLog(String id) throws IOException, InterruptedException;
}

package com.example.demo.xnservice.service;

import com.example.demo.xnservice.entity.ScriptInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.xnservice.vo.SearchVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengxingqu
 * @since 2020-08-15
 */
public interface ScriptInfoService extends IService<ScriptInfo> {
    List<ScriptInfo> searchScriptTarget(SearchVo searchVo);
    List<ScriptInfo> searchResultByName(String scriptName);
    List<ScriptInfo> getTopReport();
    List<ScriptInfo> selectMaxTimeData();
}

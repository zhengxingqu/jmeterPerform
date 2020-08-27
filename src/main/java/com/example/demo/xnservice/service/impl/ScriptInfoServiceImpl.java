package com.example.demo.xnservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.xnservice.entity.ScriptInfo;
import com.example.demo.xnservice.mapper.ScriptInfoMapper;
import com.example.demo.xnservice.service.ScriptInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.xnservice.vo.SearchVo;
import com.mysql.cj.protocol.a.NativeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhengxingqu
 * @since 2020-08-15
 */
@Service
public class ScriptInfoServiceImpl extends ServiceImpl<ScriptInfoMapper, ScriptInfo> implements ScriptInfoService {

    @Override
    public List<ScriptInfo> searchScriptTarget(SearchVo searchVo) {
        QueryWrapper<ScriptInfo> qw = new QueryWrapper();
        String errorCount = searchVo.getErrorCount();
        String oneRestime = searchVo.getOneRestime();
        String throughput = searchVo.getThroughput();
        String meanRestime = searchVo.getMeanRestime();
        String condition = searchVo.getCondition();
        if (!StringUtils.isEmpty(errorCount)) {
            if (condition.equals(">=")) {
                qw.ge("error_count", Integer.parseInt(errorCount));
            } else {
                qw.le("error_count", Integer.parseInt(errorCount));
            }
        }
        System.out.println(oneRestime);
        if (!StringUtils.isEmpty(oneRestime)) {
            if (condition.equals(">=")) {
                System.out.println("**");
                qw.ge("one_restime", Double.parseDouble(oneRestime));
            } else {
                System.out.println("***");
                qw.le("one_restime", Double.parseDouble(oneRestime));
            }
        }

        if (!StringUtils.isEmpty(throughput)) {
            if (condition.equals(">=")) {
                System.out.println("****");
                qw.ge("throughput", Double.parseDouble(throughput));
            } else {
                System.out.println("*****");
                qw.le("throughput", Double.parseDouble(throughput));
            }
        }

        if (!StringUtils.isEmpty(meanRestime)) {
            if (condition.equals(">=")) {
                System.out.println("******");
                qw.ge("mean_restime", Double.parseDouble(meanRestime));
            } else {
                System.out.println("*******");
                qw.le("mean_restime", Double.parseDouble(meanRestime));
            }
        }

        return this.baseMapper.selectList(qw);
    }

    @Override
    public List<ScriptInfo> searchResultByName(String scriptName) {
        QueryWrapper<ScriptInfo> queryWrapper = new QueryWrapper<ScriptInfo>();
        queryWrapper.like("script_name", scriptName);
        ArrayList<ScriptInfo> arrayList = new ArrayList<>();
        if (!StringUtils.isEmpty(scriptName)) {
            arrayList = (ArrayList<ScriptInfo>) this.list(queryWrapper);
        }
        return arrayList;
    }

    @Override
    public List<ScriptInfo> getTopReport() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        this.baseMapper.selectList(queryWrapper);
        return null;
    }

    @Override
    public List<ScriptInfo> selectMaxTimeData() {
        List<ScriptInfo> list = (List<ScriptInfo>) this.baseMapper.selectMaxTimeData();
        return list;
    }

}

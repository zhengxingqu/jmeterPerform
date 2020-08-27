package com.example.demo.xnservice.mapper;

import com.example.demo.xnservice.entity.ScriptInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.xnservice.entity.XnTable;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengxingqu
 * @since 2020-08-15
 */
public interface ScriptInfoMapper extends BaseMapper<ScriptInfo> {
    @Select("select * from script_info where create_time=(select max(create_time) from script_info)")
    ScriptInfo selectMaxTimeData();

}

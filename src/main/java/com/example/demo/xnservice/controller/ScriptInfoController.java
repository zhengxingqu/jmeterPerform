package com.example.demo.xnservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commons.R;
import com.example.demo.xnservice.entity.ScriptInfo;
import com.example.demo.xnservice.service.ScriptInfoService;
import com.example.demo.xnservice.vo.SearchVo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhengxingqu
 * @since 2020-08-15
 */
@RestController
@CrossOrigin
@RequestMapping("/xnservice/script-info")
public class ScriptInfoController {
    @Autowired
    private ScriptInfoService scriptInfoService;

    @DeleteMapping("{id}")
    public R deleteScriptInfo(@PathVariable String id) {
        scriptInfoService.removeById(id);
        return R.success();
    }

    @PostMapping("search")
    public R searchScriptTarget(@RequestBody SearchVo searchVo) {
        List<ScriptInfo> list = scriptInfoService.searchScriptTarget(searchVo);
        return R.success().data("data", list);
    }

    @GetMapping("{current}/{limit}")
    public R paginatResult(@PathVariable Long current, @PathVariable Long limit) {
        Page<ScriptInfo> page = new Page<>(current, limit);
        scriptInfoService.page(page, null);
        return R.success().total("total", (int) page.getTotal()).data("rows", page.getRecords());
    }

    @GetMapping("/getName")
    public R getXnByName(@RequestParam String scriptName) {
        List<ScriptInfo> xnTable = scriptInfoService.searchResultByName(scriptName);
        return R.success().data("data", xnTable);
    }



    @DeleteMapping("")
    public R deleteScripts(@RequestBody Map ids) {
        scriptInfoService.removeByIds((Collection<? extends Serializable>) ids.get("ids"));
        return R.success().message("删除成功");
    }
}


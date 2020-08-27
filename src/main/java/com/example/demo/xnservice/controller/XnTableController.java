package com.example.demo.xnservice.controller;

import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commons.R;
import com.example.demo.xnservice.entity.XnTable;
import com.example.demo.xnservice.service.XnTableService;
import com.example.demo.xnservice.vo.ScriptModifyVo;
import com.example.demo.xnservice.vo.XnVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
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
 * @since 2020-08-13
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/xn-table")
public class XnTableController {
    @Autowired
    private XnTableService xnTableService;

    @GetMapping("")
    public R getList() {
        List list = xnTableService.list(null);
        return R.success().data("data", list);
    }

    @GetMapping("{current}/{limit}")
    public R paginatXn(@PathVariable Long current, @PathVariable Long limit) {
        Page<XnTable> page = new Page<>(current, limit);
        xnTableService.page(page, null);
        return R.success().total("total", (int) page.getTotal()).data("rows", page.getRecords());
    }

    @DeleteMapping("{id}")
    public R deleteXn(@PathVariable Long id) {
        xnTableService.removeById(id);
        return R.success();
    }

    @PostMapping("")
    public R addXn(@RequestBody XnVo xnVo) {
        System.out.println(xnVo.getScriptName());
        String id = xnTableService.saveXn(xnVo);
        return R.success().message("新增成功").data("id", id);
    }

    @PutMapping("")
    public R updateXn(@RequestBody XnVo xnVo) {
        xnTableService.updateXn(xnVo);
        return R.success().message("修改成功");
    }

    @GetMapping("{id}")
    public R getXnById(@PathVariable String id) {
        XnTable xnTable = xnTableService.getById(id);
        return R.success().data("data", xnTable);
    }

    @PostMapping("add")
    public R addXn1(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        xnTableService.uploadFile(multipartFile);
        return R.success();
    }

    @GetMapping("/getName")
    public R getXnByName(@RequestParam String scriptName) {
        List<XnTable> xnTable = xnTableService.searchXnByName(scriptName);
        return R.success().data("data", xnTable);
    }

    @PostMapping("/run")
    public R runSingleScript(@RequestBody XnTable xnTable) throws IOException, InterruptedException {
        xnTableService.runSingleScript(xnTable);
        return R.success();
    }

    @DeleteMapping("")
    public R deleteScripts(@RequestBody Map ids) {
        xnTableService.removeByIds((Collection<? extends Serializable>) ids.get("ids"));
        return R.success().message("删除成功");
    }

    @PostMapping("/modifyThreads")
    public R modifyNumThreads(@RequestBody Map script) throws IOException {
        xnTableService.modifyNumThreads(script);
        return R.success();
    }

    @PostMapping("/getScriptContent")
    public R readScriptContent(@RequestBody Map script) throws IOException {
        String content = xnTableService.readScriptContent(script);
        return R.success().data("content", content);
    }

    @PutMapping("/modify")
    public R replaceScriptContent(@RequestBody ScriptModifyVo scriptModifyVo) throws IOException {
        xnTableService.replaceScriptContent(scriptModifyVo);
        return R.success();
    }

    @PostMapping("/download")
    public R downloadScript(@RequestBody List<String> list){
        xnTableService.downloadScript(list);
        return R.success();
    }

    @PostMapping("/runs")
    public R runManyScript(@RequestBody List<String> ids){
        xnTableService.runManyScript(ids);
        return R.success();
    }

    @GetMapping("/getLog/{id}")
    public R realtimeShowLog(@PathVariable String id) throws IOException, InterruptedException {
        String content = xnTableService.realtimeShowLog(id);
        return R.success().data("data", content);
    }

}


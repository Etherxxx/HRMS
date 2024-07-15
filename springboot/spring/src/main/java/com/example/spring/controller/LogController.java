package com.example.spring.controller;

import com.example.spring.common.Result;
import com.example.spring.entity.Log;
import com.example.spring.entity.Params;
import com.example.spring.service.LogService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/log")
public class LogController {
    @Resource
    private LogService logService;

    @PostMapping
    public Result save(@RequestBody Log log) {
        logService.add(log);
        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Log> adminPageInfo = logService.findBySearch(params);
        return Result.success(adminPageInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        logService.delete(id);
        return Result.success();
    }
}

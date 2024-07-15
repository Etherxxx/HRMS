package com.example.spring.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.spring.common.AutoLog;
import com.example.spring.common.Result;
import com.example.spring.entity.Admin;
import com.example.spring.entity.Params;
import com.example.spring.entity.Salary;
import com.example.spring.entity.Sign;
import com.example.spring.service.AdminService;
import com.example.spring.service.SalaryService;
import com.example.spring.service.SignService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/salary")
public class SalaryController {
    @Resource
    private SalaryService salaryService;

    @PostMapping
    public Result update(@RequestBody Salary salary){
        if (ObjectUtil.isEmpty(salary.getId())) {
            salaryService.add(salary);
        } else {
            salaryService.update(salary);
        }
        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Salary> salaryPageInfo = salaryService.findBySearch(params);
        return Result.success(salaryPageInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        salaryService.delete(id);
        return Result.success();
    }
}

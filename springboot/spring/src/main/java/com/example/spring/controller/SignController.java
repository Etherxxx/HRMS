package com.example.spring.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.spring.common.AutoLog;
import com.example.spring.common.Result;
import com.example.spring.entity.Admin;
import com.example.spring.entity.Notice;
import com.example.spring.entity.Params;
import com.example.spring.entity.Sign;
import com.example.spring.service.AdminService;
import com.example.spring.service.SignService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/sign")
public class SignController {
    @Resource
    private SignService signService;
    @Resource
    private AdminService adminService;

    @PostMapping
    public Result update(@RequestBody Sign sign){
        if (ObjectUtil.isEmpty(sign.getId())) {
            System.out.println(sign);
            signService.add(sign);
        } else {
            signService.update(sign);
        }
        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Sign> adminPageInfo = signService.findBySearch(params);
        return Result.success(adminPageInfo);
    }

    @DeleteMapping("/{id}")
    @AutoLog("删除考勤记录")
    public Result delete(@PathVariable Integer id) {
        signService.delete(id);
        return Result.success();
    }

    @GetMapping("/getUserName")
    public Result findUserNameById(@RequestParam("userId") Integer userId) {
        Admin admin=adminService.findById(userId);
        return Result.success(admin);
    }
}

package com.example.spring.controller;

import com.example.spring.common.JwtTokenUtils;
import com.example.spring.common.Result;
import com.example.spring.entity.Admin;
import com.example.spring.entity.Audit;
import com.example.spring.entity.Params;
import com.example.spring.exception.CustomException;
import com.example.spring.service.AdminService;
import com.example.spring.service.AuditService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/audit")
public class AuditController {
    @Resource
    private AuditService auditService;

    @PostMapping
    public Result save(@RequestBody Audit audit) {
        if (audit.getId() == null) {
            auditService.add(audit);
        } else {
            auditService.update(audit);
        }
        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Audit> adminPageInfo = auditService.findBySearch(params);
        return Result.success(adminPageInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        auditService.delete(id);
        return Result.success();
    }
}

package com.example.spring.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.spring.common.AutoLog;
import com.example.spring.common.Result;
import com.example.spring.entity.Params;
import com.example.spring.entity.Role;
import com.example.spring.entity.Sign;
import com.example.spring.service.RoleService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @PostMapping
    public Result update(@RequestBody Role role) {
        if (ObjectUtil.isEmpty(role.getId())) {
            roleService.add(role);
        } else {
            roleService.update(role);
        }

        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Role> adminPageInfo = roleService.findBySearch(params);
        return Result.success(adminPageInfo);
    }

    @DeleteMapping("/{id}")
    @AutoLog("删除角色")
    public Result delete(@PathVariable Integer id) {
        roleService.delete(id);
        return Result.success();
    }
}

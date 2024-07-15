package com.example.spring.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.spring.common.AutoLog;
import com.example.spring.common.Result;
import com.example.spring.entity.Admin;
import com.example.spring.entity.Dept;
import com.example.spring.entity.Params;
import com.example.spring.service.AdminService;
import com.example.spring.service.DeptService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private AdminService adminService;
    @Resource
    private DeptService deptService;

    @PostMapping
    @AutoLog("修改部门信息")
    public Result save(@RequestBody Dept dept) {
        if (ObjectUtil.isNotEmpty(dept.getId())) {
            Dept originDept = deptService.findById(dept.getId());
            Admin originManager = adminService.findById(originDept.getManagerId());
            deptService.update(dept);

            //如果部门负责人出现变化，则更新用户角色为Employee
            if (!originDept.getManagerId().equals(dept.getManagerId())) {
                originManager.setRole("ROLE_EMPLOYEE");
                adminService.update(originManager);

                //通过输入姓名查找新负责人
                Admin newManger = adminService.findByUserName(dept.getManagerName());
                if (newManger == null) {
                    return Result.error("未查询到该用户，请输入有效名称");
                }

                newManger.setRole("ROLE_MANAGER");
                newManger.setDeptId(dept.getId());
                adminService.update(newManger);
            }
        } else {
            Admin manager = adminService.findByUserName(dept.getManagerName());
            if (manager == null) {
                return Result.error("未查询到该用户，请输入有效名称");
            }

            dept.setManagerId(manager.getId());
            deptService.add(dept);
            if (manager.getRole().equals("ROLE_EMPLOYEE")) {
                manager.setRole("ROLE_MANAGER");
                manager.setDeptId(dept.getId());
                adminService.update(manager);
            }
        }
        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Dept> adminPageInfo = deptService.findBySearch(params);
        return Result.success(adminPageInfo);
    }

    @DeleteMapping("/{id}")
    @AutoLog("删除部门信息")
    public Result delete(@PathVariable Integer id) {
        deptService.delete(id);
        return Result.success();
    }
}

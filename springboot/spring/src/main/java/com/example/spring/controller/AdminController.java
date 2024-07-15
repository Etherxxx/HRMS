package com.example.spring.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.spring.common.AutoLog;
import com.example.spring.common.Result;
import com.example.spring.entity.Admin;
import com.example.spring.entity.Params;
import com.example.spring.service.AdminService;
import com.example.spring.service.DeptService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;
    @Resource
    private DeptService deptService;

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Admin loginUser = adminService.login(admin);
        return Result.success(loginUser);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success();
    }

    @PostMapping
    @AutoLog("修改用户信息")
    public Result save(@RequestBody Admin admin) {
        if (admin.getId() != null) {
            adminService.update(admin);
        } else {
            adminService.add(admin);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Admin> list = adminService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Admin> adminPageInfo = adminService.findBySearch(params);
        return Result.success(adminPageInfo);
    }

    @DeleteMapping("/{id}")
    @AutoLog("删除用户信息")
    public Result delete(@PathVariable Integer id) {
        adminService.delete(id);
        return Result.success();
    }

    @GetMapping("/echarts/pie")
    public Result echartsPie() {
//        //查询出所有人员
//        List<Admin> list = adminService.findAll();
//        //将用户中role值为空得先筛选掉，再通过collect进行用户角色的计数分组
//        Map<String, Long> collect = list.stream()
//                .filter(x -> ObjectUtil.isNotEmpty(x.getDeptName()))
//                .collect(Collectors.groupingBy(Admin::getRole, Collectors.counting()));
//        //最后返回给前端{key,value}形式的数据结构
//        List<Map<String,Object>> mapList=new ArrayList<>();
//        if(CollectionUtil.isNotEmpty(collect)){
//            for (String key : collect.keySet()) {
//                Map<String,Object> map=new HashMap<>();
//                map.put("name",key);
//                map.put("value",collect.get(key));
//                mapList.add(map);
//            }
//        }
//        return Result.success(mapList);
        // 查询出所有人员
        List<Admin> list = adminService.findAll();

        // 统计每个部门的人数
        Map<String, Long> deptCountMap = new HashMap<>();
        for (Admin admin : list) {
            if (admin.getDeptId() != null) {
                String deptName = deptService.findDeptNameById(admin.getDeptId());
                deptCountMap.put(deptName, deptCountMap.getOrDefault(deptName, 0L) + 1);
            }
        }

        // 将部门名称和对应的人数封装到一个 List 中
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (String deptName : deptCountMap.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", deptName);
            map.put("value", deptCountMap.get(deptName));
            mapList.add(map);
        }

        return Result.success(mapList);
    }
    @GetMapping("/echarts/bar")
    public Result echartsBar() {
        //查询出所有人员
        List<Admin> list = adminService.findAll();
        //将用户中role值为空得先筛选掉，再通过collect进行用户角色的计数分组
        Map<String, Long> collect = list.stream()
                .filter(x -> ObjectUtil.isNotEmpty(x.getRole()))
                .collect(Collectors.groupingBy(Admin::getRole, Collectors.counting()));
        //最后返回给前端的数据结构
        List<String> xAxis=new ArrayList<>();
        List<Long> yAxis=new ArrayList<>();
        if(CollectionUtil.isNotEmpty(collect)){
            for (String key : collect.keySet()) {
                xAxis.add(key);
                yAxis.add(collect.get(key));
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("xAxis",xAxis);
        map.put("yAxis",yAxis);
        return Result.success(map);
    }

    @GetMapping("/echarts/line")
    public Result echartsLine() {
        //查询出所有人员
        List<Admin> list = adminService.findAll();
        //将用户中role值为空得先筛选掉，再通过collect进行用户角色的计数分组
        Map<String, Long> collect = list.stream()
                .filter(x -> ObjectUtil.isNotEmpty(x.getRole()))
                .collect(Collectors.groupingBy(Admin::getRole, Collectors.counting()));
        //最后返回给前端的数据结构
        List<String> xAxis=new ArrayList<>();
        List<Long> yAxis=new ArrayList<>();
        if(CollectionUtil.isNotEmpty(collect)){
            for (String key : collect.keySet()) {
                xAxis.add(key);
                yAxis.add(collect.get(key));
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("xAxis",xAxis);
        map.put("yAxis",yAxis);
        return Result.success(map);
    }
}

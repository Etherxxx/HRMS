package com.example.spring.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.spring.common.JwtTokenUtils;
import com.example.spring.dao.AdminDao;
import com.example.spring.entity.Admin;
import com.example.spring.entity.Params;
import com.example.spring.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Resource
    private AdminDao adminDao;

    public List<Admin> findAll() {
        //使用tk.mybatis包自带方法
        return adminDao.selectAll();
//        return userDao.getUser();
    }

    public PageInfo<Admin> findBySearch(Params params) {
        Admin user =JwtTokenUtils.getCurrentUser();
        if(ObjectUtil.isNull(user)){
            throw new CustomException("未从token中获取到用户信息，请重新登陆");
        }
        //如果当前用户是部门经理，则只查询属于这个部门的信息
        if("ROLE_MANAGER".equals(user.getRole())){
            params.setDeptId(user.getDeptId());
            PageHelper.startPage(params.getPageNum(), params.getPageSize());
            //接下来的查询会按照开启的设置来查询
            List<Admin> list = adminDao.findByDeptId(user.getDeptId());
            return PageInfo.of(list);
        }else {
            //开启分页查询
            PageHelper.startPage(params.getPageNum(), params.getPageSize());
            //接下来的查询会按照开启的设置来查询
            List<Admin> list = adminDao.findBySearch(params);
            return PageInfo.of(list);
        }
    }

    public void add(Admin admin) {
        //进行新增操作的非空判断
        if (admin.getName() == null || "".equals(admin.getName())) {
            throw new CustomException("用户名为空");
        }
        //设置重复名称不允许继续添加
        Admin user = adminDao.findByName(admin.getName());
        if (user != null) {
            //说明存在重复名用户不允许添加
            throw new CustomException("该用户已存在，请勿重复添加");
        }
        //新增信息字段初始化默认密码
        if (admin.getPassword() == null) {
            admin.setPassword("000000");
        }
        if (admin.getPhone() == null) {
            admin.setPhone("无联系方式");
        }
        if (admin.getRole() == null) {
            admin.setRole("ROLE_EMPLOYEE");
        }
        if(admin.getDeptId()==null){
            admin.setDeptId(10);
        }
        adminDao.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminDao.updateByPrimaryKey(admin);
    }

    public void delete(Integer id) {
        adminDao.deleteByPrimaryKey(id);
    }

    public Admin login(Admin admin) {
        if (admin.getName() == null || "".equals(admin.getName())) {
            throw new CustomException("用户名不能为空");
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            throw new CustomException("密码为空");
        }
        Admin user = adminDao.findByNameAndPassword(admin.getName(), admin.getPassword());
        if (user == null) {
            //说明不存在用户
            throw new CustomException("用户名或密码错误");
        }
        //查询过后若存在，则说明输入信息正确
        //生成该登录用户对应token，跟着user一起返回到前端
        String token = JwtTokenUtils.genToken(user.getId().toString(), user.getPassword());
        user.setToken(token);
        return user;
    }

    public Admin findById(Integer id) {
        return adminDao.selectByPrimaryKey(id);
    }

    public Admin findByUserName(String managerName) {
        return adminDao.findByName(managerName);
    }
}

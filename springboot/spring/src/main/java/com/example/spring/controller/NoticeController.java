package com.example.spring.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.spring.common.AutoLog;
import com.example.spring.common.Result;
import com.example.spring.entity.Notice;
import com.example.spring.entity.Params;
import com.example.spring.service.NoticeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    @PostMapping
    public Result update(@RequestBody Notice notice) {
        if (ObjectUtil.isEmpty(notice.getId())) {
            noticeService.add(notice);
        } else {
            noticeService.update(notice);
        }

        return Result.success();
    }

    @GetMapping
    public Result findNewNotice() {
        List<Notice> list = noticeService.findNewNotice();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Notice> adminPageInfo = noticeService.findBySearch(params);
        return Result.success(adminPageInfo);
    }

    @DeleteMapping("/{id}")
    @AutoLog("删除公告信息")
    public Result delete(@PathVariable Integer id) {
        noticeService.delete(id);
        return Result.success();
    }

}

package com.y2w.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y2w.demo.domain.Bill;
import com.y2w.demo.domain.Member;
import com.y2w.demo.mapper.BillMapper;
import com.y2w.demo.mapper.MemberMapper;
import com.y2w.demo.service.BillService;
import com.y2w.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录
 */
@RestController
public class LoginController{
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private BillService billService;
    @Autowired
    private BillMapper billMapper;

    @PostMapping("/login")
    public String checkUser(@RequestBody Member data){
        String name= data.getName();//前端传过来的姓名

        Member member = memberMapper.selectByName(name);
        if(member!=null){
            if(data.getPassward().equals(member.getPassward())){ //用户名存在时比较密码
                return "登陆成功";
            }else return "密码错误";
        }
        return "用户不存在";
    }





}

package com.y2w.demo.service;

import com.y2w.demo.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author DELL
* @description 针对表【member】的数据库操作Service
* @createDate 2022-08-09 23:11:02
*/
public interface MemberService extends IService<Member> {

    int removeAll(Integer id);
}

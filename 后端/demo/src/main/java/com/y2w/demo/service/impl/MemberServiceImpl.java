package com.y2w.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.y2w.demo.domain.Bill;
import com.y2w.demo.domain.Member;
import com.y2w.demo.mapper.BillMapper;
import com.y2w.demo.service.MemberService;
import com.y2w.demo.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author DELL
* @description 针对表【member】的数据库操作Service实现
* @createDate 2022-08-09 23:11:02
*/
@Service
@Transactional
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member>
    implements MemberService{

    @Autowired
    private BillMapper billMapper;
    @Autowired
    private  MemberMapper memberMapper;

    @Override
    public int removeAll(Integer id) {
        int i=0;
        QueryWrapper<Bill> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("consumerid",id);
        int delete = billMapper.delete(queryWrapper);
        Integer integer = billMapper.selectCount(queryWrapper);
        if(delete>0||integer==0){
            i = memberMapper.deleteById(id);
        }
        return i;
    }
}





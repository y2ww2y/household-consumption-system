package com.y2w.demo.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.y2w.demo.domain.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author DELL
* @description 针对表【member】的数据库操作Mapper
* @createDate 2022-08-09 23:11:02
* @Entity com.y2w.demo.domain.Member
*/
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    Member selectByName(@Param("name") String name);//根据名字查询账号信息，为了登录
}





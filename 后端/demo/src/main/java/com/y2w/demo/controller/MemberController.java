package com.y2w.demo.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.y2w.demo.domain.Bill;
import com.y2w.demo.domain.Member;
import com.y2w.demo.mapper.MemberMapper;
import com.y2w.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

/**
 * 人员方面
 */
@RestController
public class MemberController {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberService memberService;


    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/selectUser")
    public List doSelectUser() {
        List<Member> members = memberMapper.selectList(null);
        return members;
    }

    /**
     * 添加
     *
     * @param
     * @return
     */
    @PostMapping("/addUser")
    public String addRecords(@RequestBody Map<String, Object> map) {
        Member member = new Member();
        member.setName((String) map.get("name"));
        member.setPassward((String) map.get("password"));
        member.setRole(0);

        int insert = memberMapper.insert(member);
        if (insert > 0) return "添加成功";
        return "添加失败";
    }

    /**
     * 根据id删除用户及其消费记录。
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Integer id) {
        int i = memberService.removeAll(id);
        if (i > 0) return "注销成功";
        return "注销失败";
    }

    /**
     * 根据map获得一个member对象
     *修改信息
     * @param map
     * @return
     */
    @PostMapping("/modifyUser")
    public String modifyUser(@RequestBody Map<String, Object> map) {
        Bill bill = new Bill();
        Member member = new Member();
        member.setId((Integer) map.get("id"));
        if (StringUtils.isNotBlank((String) map.get("name"))) {
            member.setName((String) map.get("name"));
        }
        if (StringUtils.isNotBlank((String) map.get("password"))) {
            member.setPassward((String) map.get("password"));
        }
        int i = memberMapper.updateById(member);
        if (i > 0) return "修改成功";
        return "修改失败";
    }

    /**
     * 改变权限
     * @param name1
     * @param name2
     * @return
     */
    @GetMapping("/changeRole/{name1}/{name2}")
    @Transactional
    public String changeRole(@PathVariable String name1, @PathVariable String name2) {
        UpdateWrapper<Member> updateWrapper1 = new UpdateWrapper<>();
        UpdateWrapper<Member> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper1.eq("name", name1).set("role", 0);
        boolean update1 = memberService.update(updateWrapper1);
        if (update1) {
            updateWrapper2.eq("name", name2).set("role", 1);
            boolean update2 = memberService.update(updateWrapper2);
            if (update2) return "权限转让成功";
        }
        return "权限转让失败";
    }
}

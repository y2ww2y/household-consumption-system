package com.y2w.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.y2w.demo.domain.Bill;
import com.y2w.demo.mapper.BillMapper;
import com.y2w.demo.mapper.MemberMapper;
import com.y2w.demo.service.BillService;
import com.y2w.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 消息记录方面的增删改查
 */
@RestController
public class BillController {

    @Autowired
    private BillService billService;
    @Autowired
    private BillMapper billMapper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化时间向数据库靠近
    /**
     *查询所有
     * @return
     */
    //@GetMapping("/selectAll") //后面条件刷新可以覆盖掉该功能
    //public Map doSelectAll(){
    //    Page<Map<String,Object>> page=new Page<>(1,5);
    //    billMapper.selectMaps(page, null);
    //    List<Map<String, Object>> records = page.getRecords();
    //    long count = page.getTotal(); //获取总页码数
    //    Map map=new HashMap();
    //    map.put("list",records);
    //    map.put("count",count);
    //    return map;
    //}

    /**
     * 添加
     * @param
     * @return
     */
    @PostMapping("/addRecords")
    public String addRecords(@RequestBody Map<String,Object> map){
        int i = billService.addRecords(map);
        if(i>0) return "添加成功";
        return "添加失败";
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteRecords/{id}")
    public String deleteRecords(@PathVariable String id){
        //List<Long> list= Arrays.asList(id);
        //int i = billMapper.deleteBatchIds(list);
        int i = billMapper.deleteById(id);
        if (i>0) return "删除成功";
        return "删除失败";
    }

    @PostMapping("/deleteList")
    public String deleteSelected(@RequestBody Map<String,Object> map){
        List list = (List) map.get("list");
        int i = billMapper.deleteBatchIds(list);
        if (i>0) return "删除成功";
        return "删除失败";
    }


    /**
     * 根据map获得一个bill对象,前端获取了id，不用姓名转换，直接在controller操作
     * @param map
     * @return
     */
    @PutMapping("/modifyRecords")
    public String modifyRecords(@RequestBody Map<String,Object> map){
        String date1= (String) map.get("date1");
        String date2= (String) map.get("date2");
        String date=null;
        if(date1!=null&&date2!=null){
            date=date1+" "+date2;
        }
        Date parse=null;
        if(StringUtils.isNotBlank(date)){
            try {
                parse = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        UpdateWrapper<Bill> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .set(map.get("money")!=null,"amout",map.get("money"))
                .set(parse!=null,"bdate",parse)
                .set(StringUtils.isNotBlank((String)map.get("type")),"type",map.get("type"))
                .set(StringUtils.isNotBlank((String)map.get("descr")),"descr",map.get("descr"))
                .eq("bid",map.get("bid"));
        boolean update = billService.update(updateWrapper);
        if(update) return "修改成功";
        return "修改失败";
    }

    /**
     * 根据条件返回查询结果
     * @return
     */
    @PostMapping("/select")
    public Map doSelect(@RequestBody Map<String,Object> map){
        int current= (int) map.get("pages");

        String name= (String) map.get("name");
        String type= (String) map.get("type");
        Date beginDate=null;
        Date endDate=null;
        if((map.get("beginDate") != null) && (map.get("endDate")!=null)){
        try {
             beginDate=sdf.parse((String) map.get("beginDate"));
             endDate= sdf.parse((String) map.get("endDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        }
        Page<Map<String,Object>> page=new Page<>(current,5);
        billMapper.selectByBdateAndTypeMapPage(page,name,type,beginDate,endDate);
        List<Map<String, Object>> records = page.getRecords();
        int count = (int)page.getTotal();
        Map map1=new HashMap();
        map1.put("list",records);
        map1.put("count",count);
        return map1;
    }
}

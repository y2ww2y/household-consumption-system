package com.y2w.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y2w.demo.domain.Bill;
import com.y2w.demo.domain.Member;
import com.y2w.demo.mapper.BillMapper;
import com.y2w.demo.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 统计方面
 */
@RestController
public class CensusController {

    @Autowired
    private BillService billService;
    @Autowired
    private BillMapper billMapper;


    /**
     * 查询该年份对应月份消费
     * @param year
     * @return
     */
    @GetMapping("/census/{year}/{month}")
    public List<Map<String,Object>> doCensus(@PathVariable String year, @PathVariable String month){
        List<Map<String, Object>> mapList = billService.queryToCensus(year,month);
        return mapList;
    }

    /**
     * 查询本年所有消费
     * @return
     */
    @GetMapping("/census/{year}")
    public List<Map<String,Object>> doCensusAll(@PathVariable String year){
        List<Map<String, Object>> mapList = billService.queryToCensusAll(year);
        return mapList;
    }

    @GetMapping("/getYears")
    public List<String> getYears(){

        List<String> years = billMapper.selectYears();
        return years;
    }

}

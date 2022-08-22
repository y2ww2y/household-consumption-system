package com.y2w.demo.service;

import com.y2w.demo.domain.Bill;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author DELL
* @description 针对表【bill】的数据库操作Service
* @createDate 2022-08-09 22:26:10
*/
public interface BillService extends IService<Bill> {

    int addRecords(Map<String, Object> map);

    List<Map<String,Object>> queryToCensus( String year,String month);

    List<Map<String, Object>> queryToCensusAll(String year);

}

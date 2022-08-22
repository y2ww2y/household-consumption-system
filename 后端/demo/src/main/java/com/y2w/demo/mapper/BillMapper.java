package com.y2w.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.y2w.demo.domain.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author DELL
* @description 针对表【bill】的数据库操作Mapper
* @createDate 2022-08-09 22:26:10
* @Entity generator.domain.Bill
*/
@Repository
public interface BillMapper extends BaseMapper<Bill> {

    //Map<String,Object> selectAllBills();

    Page<Map<String,Object>> selectMaps(@Param("page") Page<Map<String,Object>> page,Wrapper<Bill> queryWrapper);

    Page<Map<String,Object>> selectByBdateAndTypeMapPage(@Param("page")Page<Map<String,Object>> page, String name, String type, Date
                                                         beginDate,Date endDate);

    List<Map<String,Object>> selectToCensusByMonth(String year,String month);

    List<Map<String, Object>> selectToCensusByYear(String year);

    List<String> selectYears();
}






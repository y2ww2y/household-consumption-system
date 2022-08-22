package com.y2w.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.y2w.demo.domain.Member;
import com.y2w.demo.mapper.MemberMapper;
import com.y2w.demo.service.BillService;
import com.y2w.demo.domain.Bill;
import com.y2w.demo.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author DELL
* @description 针对表【bill】的数据库操作Service实现
* @createDate 2022-08-09 22:26:10
*/
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill>
    implements BillService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private BillMapper billMapper;

    /**
     * 添加记录
     * @param map
     * @return
     */
    @Override
    public int addRecords(Map<String, Object> map) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        Member member = memberMapper.selectByName((String) map.get("name"));
        Bill bill=new Bill();
        bill.setAmout(Integer.valueOf((String) map.get("money")));
        bill.setBdate(parse);
        bill.setType((String) map.get("type"));
        bill.setConsumerid(member.getId());
        bill.setDescr((String) map.get("descr"));
        int insert = billMapper.insert(bill);
        return insert;

    }

    /**
     * 按类型分组，
     * @param year
     */
    @Override
    public List<Map<String, Object>> queryToCensus( String year,String month) {
        List<Map<String, Object>> mapList = billMapper.selectToCensusByMonth(year,month);
        return mapList;

    }

    @Override
    public List<Map<String, Object>> queryToCensusAll(String year) {
        List<Map<String, Object>> mapList = billMapper.selectToCensusByYear(year);
        return mapList;
    }
}





package com.coolcloud.sacw.car.analyzer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.coolcloud.sacw.car.entity.CarConserveHistory;
import com.coolcloud.sacw.car.entity.CarConservePeriod;

/**
 * 
 * @author xyz
 *
 * @date 2018年4月11日 下午4:30:32
 */
public class CarConserveAnalyzer {

    /**
     * 默认养护周期
     */
    private static final int DEFAULT_PERIOD = 7;

    /**
     * 一天毫秒数：1000 * 60 * 60 * 24
     */
    private static final long MS_PER_DAY = 86400000L;

    /**
     * <h2>养护项及周期设置</h2>
     * <p>
     * 该对象在整个分析中复用，请勿更改该列表中的值
     * </p>
     */
    private List<CarConservePeriod> periods;

    public CarConserveAnalyzer() {

    }

    public CarConserveAnalyzer(List<CarConservePeriod> periods) {
        if (periods == null || periods.size() == 0) {
            throw new IllegalArgumentException("请提供养护周期设置");
        }
        this.periods = periods;
    }

    /**
     * 根据最近养护历史分析下次需要养护的内容
     * 
     * @param histories
     *            最近养护历史记录
     * @return
     * @throws ParseException 
     */
    public List<CarConservePeriod> analyze(List<CarConserveHistory> histories)  {

        Map<String, Date> map = new HashMap<>();
        Date today = new Date();
        long interval = 0;
        for (CarConserveHistory history : histories) {
            map.put(history.getConserveContentCode(), history.getConserveTime());
        }

        List<CarConservePeriod> needContents = new ArrayList<>();
 
        
        
        for (CarConservePeriod period : periods) {
            String content = period.getConserveContentCode();
            Date latestConserveTime = map.get(content);
            Integer i = period.getConservePeriod();
            // 养护周期，没有取默认值7
            int days = i == null ? DEFAULT_PERIOD : i.intValue();
            //处理时间数据
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            if(latestConserveTime != null) {
            	// 最近养护一次养护距今天数，若是养护历史中没有记录则取一个足够大的值
            	 interval = daysBetween(today,latestConserveTime);
            }else {
            	 interval =  Long.MAX_VALUE;
            }
            // 最近养护一次养护距今天数，若是养护历史中没有记录则取一个足够大的值
           //   long interval = latestConserveTime == null ? Long.MAX_VALUE : (today - latestConserveTime.getTime()) / MS_PER_DAY;
            // 若最后一次养护到现在天数大于周期值，则需要养护
            if (interval >= days&&!"car_06".equals(content)) {
                needContents.add(period);
            }
        }
        return needContents;
    }

    public List<CarConservePeriod> getPeriods() {
        return periods;
    }

    public void setPeriods(List<CarConservePeriod> periods) {
        this.periods = periods;
    }
    
    /**
     * 求两个日期之间的天数
     */
    public static long daysBetween(Date smdate,Date bdate)   
    {  
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	try {
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Calendar cal = Calendar.getInstance();  
        cal.setTime(smdate);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(bdate);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time1-time2)/(1000*3600*24);
          
       return between_days;         
    }  

}

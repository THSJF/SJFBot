package com.meng.modules;

import com.google.gson.reflect.TypeToken;
import com.meng.SJFInterfaces.BaseGroupModule;
import com.meng.SJFInterfaces.IPersistentData;
import com.meng.adapter.BotWrapperEntity;
import com.meng.config.DataPersistenter;
import com.meng.tools.SJFExecutors;
import com.meng.tools.TimeFormater;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * @Description: 群消息统计图
 * @author: 司徒灵羽
 **/

public class MGroupCounterChart extends BaseGroupModule implements IPersistentData {

	public HashMap<Long,GroupSpeak> groupsMap = new HashMap<>(32);

    public MGroupCounterChart(BotWrapperEntity bw) {
        super(bw);
    }

    @Override
    public String getModuleName() {
        return "GroupCountChart";
    }

	@Override
	public MGroupCounterChart load() {
		DataPersistenter.read(this);
		SJFExecutors.executeAtFixedRate(new Runnable() {
				@Override
				public void run() {
					saveData();
				}
			}, 1, 1, TimeUnit.MINUTES);
		return this;
	}
	public static class GroupSpeak {
		public int all = 0;
		public HashMap<String,HashMap<Integer,Integer>> hour=new HashMap<>(16);		
	}

	@Override
	public boolean onGroupMessage(GroupMessageEvent gme) {
		long fromGroup = gme.getGroup().getId();
        if (!entity.configManager.getGroupConfig(fromGroup).isGroupCountChartEnable()) {
			return false;
		}
        String msg = gme.getMessage().contentToString();
		GroupSpeak gs = groupsMap.get(fromGroup);
		if (gs == null) {
			gs = new GroupSpeak();
			groupsMap.put(fromGroup, gs);
		}
		++gs.all;
        String date = TimeFormater.getDate();
        gs.hour.putIfAbsent(date, new HashMap<Integer,Integer>());
        HashMap<Integer,Integer> everyHourHashMap = gs.hour.get(date);
        Date da=new Date();
        int nowHour=da.getHours();
        everyHourHashMap.putIfAbsent(nowHour, 1);
        everyHourHashMap.put(nowHour, everyHourHashMap.get(nowHour) + 1);
		if (msg.equals("-发言统计")) {
            StringBuilder sb=new StringBuilder(String.format("群内共有%d条消息,今日消息情况:\n", groupsMap.get(fromGroup).all));
			for (int i=0;i < 24;++i) {
				if (everyHourHashMap.get(i) == null) {
					continue;
				}
				sb.append(String.format("%d:00-%d:00  共%d条消息\n", i, i + 1, everyHourHashMap.get(i)));
			}
			entity.sjfTx.sendGroupMessage(fromGroup, sb.toString());
			TimeSeries dtimeseries = new TimeSeries("你群发言");
			Calendar dc = Calendar.getInstance();
			dc.add(Calendar.HOUR_OF_DAY, -24);
			HashMap<Integer,Integer> deveryHour=gs.hour.get(TimeFormater.getDate(dc.getTimeInMillis()));
			for (int i=dc.get(Calendar.HOUR_OF_DAY);i < 24;++i) {
				dtimeseries.add(new Hour(i, dc.get(Calendar.DATE), dc.get(Calendar.MONTH) + 1, dc.get(Calendar.YEAR)), deveryHour.get(i) == null ?0: deveryHour.get(i));
			}
			dc = Calendar.getInstance();
			deveryHour = gs.hour.get(TimeFormater.getDate(dc.getTimeInMillis()));
			for (int i=0;i <= dc.get(Calendar.HOUR_OF_DAY);++i) {
				dtimeseries.add(new Hour(i, dc.get(Calendar.DATE), dc.get(Calendar.MONTH) + 1, dc.get(Calendar.YEAR)), deveryHour.get(i) == null ?0: deveryHour.get(i));
			}
			TimeSeriesCollection dtimeseriescollection = new TimeSeriesCollection();  
			dtimeseriescollection.addSeries(dtimeseries);
			JFreeChart djfreechart = ChartFactory.createTimeSeriesChart("你群24小时发言", "时间", "", dtimeseriescollection, true, true, true);  
			XYPlot dxyplot = (XYPlot) djfreechart.getPlot();  
			DateAxis ddateaxis = (DateAxis) dxyplot.getDomainAxis();  
			ddateaxis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));  
			ChartPanel dframe1 = new ChartPanel(djfreechart, true);  
			ddateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));  		
			ddateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));  
			djfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));  
			djfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));  
			File pic=null;
			try {
				pic = new File(entity.appDirectory + "downloadImages/" + System.currentTimeMillis() + ".jpg");
				ChartUtils.saveChartAsJPEG(pic, 1.0f, dframe1.getChart(), 800, 480);
			} catch (IOException e) {
                throw new RuntimeException(e); 
            }
			entity.sjfTx.sendGroupMessage(fromGroup, entity.image(pic, fromGroup));
			TimeSeries timeseries = new TimeSeries("你群发言");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -30);
			for (int i=0;i <= 30;++i) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				HashMap<Integer,Integer> everyHour=gs.hour.get(TimeFormater.getDate(cal.getTimeInMillis()));
				int oneDay=0;
				if (everyHour == null) {
					oneDay = 0;
				} else {
					for (int oneHour:everyHour.values()) {
						oneDay += oneHour;
					}
				}
				timeseries.add(new Day(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR)), oneDay);
			}
			TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();  
			timeseriescollection.addSeries(timeseries);
			JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("你群30天发言", "时间", "", timeseriescollection, true, true, true);  
			XYPlot xyplot = (XYPlot) jfreechart.getPlot();  
			DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();  
			dateaxis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));  
			ChartPanel frame1 = new ChartPanel(jfreechart, true);  
			dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14)); 
			dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));
			jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));  
			jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));
			File pic2=null;
			try {
				pic2 = new File(entity.appDirectory + "downloadImages/" + System.currentTimeMillis() + ".jpg");
				ChartUtils.saveChartAsJPEG(pic, 1.0f, frame1.getChart(), 800, 480);
			} catch (IOException e) {
                throw new RuntimeException(e);
            }
			entity.sjfTx.sendGroupMessage(fromGroup, entity.image(pic2, fromGroup));
			return true;
		}	
		return false;
	}

	public HashMap<Integer,Integer> getSpeak(long group, String date) {
		GroupSpeak gs = groupsMap.get(group);
		if (gs == null) {
			return null;
		}
		HashMap<Integer,Integer> hr = gs.hour.get(date);
		if (hr == null) {
			return null;
		}
		return hr;
	}

	private void saveData() {
        DataPersistenter.save(this);
    }

	@Override
	public String getPersistentName() {
		return "GroupCount2.json";
	}

	@Override
	public Type getDataType() {
		return new TypeToken<HashMap<Long, GroupSpeak>>() {}.getType();
	}

	@Override
	public Object getDataBean() {
		return groupsMap;
	}

    @Override
    public BotWrapperEntity getWrapper() {
        return entity;
    }

	@Override
	public void setDataBean(Object o) {
		groupsMap = (HashMap) o;
	}
}



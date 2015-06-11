package com.crontab;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import com.db.service.ZdcNewsTotalUpdateService;
import com.db.service.ZdcNewsUpdateService;
import com.system.SpringApplicationContextFactory;
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CronAutoNewsTruncate implements Job {
    private static ZdcNewsTotalUpdateService zdcNewsTotalUpdateService = SpringApplicationContextFactory
	    .newInstance().getBean(ZdcNewsTotalUpdateService.class);
    private static ZdcNewsUpdateService zdcNewsUpdateService = SpringApplicationContextFactory
	    .newInstance().getBean(ZdcNewsUpdateService.class);

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
	zdcNewsTotalUpdateService.clearDataByTime();
	System.out.println("全部新闻数据库清空");
	zdcNewsUpdateService.clearDataByTime();
	System.out.println("滚动新闻数据库清空");
    }

}

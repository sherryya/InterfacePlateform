package com.crontab;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;

import com.crontab.bussiness.GainNewBusiness;
import com.db.service.ZdcNewsTotalUpdateService;
import com.system.SpringApplicationContextFactory;
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CronAutoNewsTotalGainer implements Job {
    private static final Logger log = Logger.getLogger(CronAutoNewsTotalGainer.class.getName());
    private static ApplicationContext ctx = SpringApplicationContextFactory.newInstance();
    private static ZdcNewsTotalUpdateService zdcNewsTotalUpdateService;
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
	     new GainNewBusiness(0);
    }
    public static void main(String[] args) {
	     new GainNewBusiness(0);
    }
}

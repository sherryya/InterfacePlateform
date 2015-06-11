package com.crontab;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;

import com.crontab.bussiness.GainNewBusiness;
import com.db.service.ZdcNewsUpdateService;
import com.system.SpringApplicationContextFactory;
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CronAutoNewsGainer implements Job {
    private static final Logger log = Logger.getLogger(CronAutoNewsGainer.class
	    .getName());
    private static ApplicationContext ctx = SpringApplicationContextFactory
	    .newInstance();
    private static ZdcNewsUpdateService zdcNewsUpdateService;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
	   new GainNewBusiness(1);
    }
    public static void main(String[] args) {
	new GainNewBusiness(1);
    }
}

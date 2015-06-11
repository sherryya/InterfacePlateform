package com.test;

import java.util.Date;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class CppBolt extends BaseBasicBolt {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Tuple tuple, BasicOutputCollector collector) {
		System.out.println(new Date() + "AddDeviceBolt start");
		String input = tuple.getString(1);
		System.out.println("~~~~~~~~~~~~~~drpc id~~~~~~~~~~~~~"+tuple.getValue(0));
		System.out.println("~~~~~~~~~~~~~~drpc result~~~~~~~~~~~~~"+tuple.getValue(0));
		collector.emit(new Values(tuple.getValue(0), input + "!"));	
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer
				.declare(new Fields("id", "result"));
	}
}

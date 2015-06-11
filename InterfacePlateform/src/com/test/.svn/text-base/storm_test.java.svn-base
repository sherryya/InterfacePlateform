package com.test;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.drpc.LinearDRPCTopologyBuilder;

public class storm_test {

	private static void get( String expression)
	{
		LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("exclamation");
		builder.addBolt(new CppBolt(), 1);
		Config conf = new Config();
		LocalDRPC drpc = new LocalDRPC();
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("drpc-demo", conf, builder.createLocalTopology(drpc));
		// for (String word : new String[]{ "hello", "goodbye" }) {
		System.out.println("Result for \"" + expression + "\": "+ drpc.execute("exclamation", expression));
		// }
	
		cluster.shutdown();
		drpc.shutdown();
	}
	public static void main(String[] args) {
		storm_test.get("世功");
	}
}

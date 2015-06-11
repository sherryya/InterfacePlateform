package com.crontab.util;



public class SysPathUtil {

	/**
	 * linux 下得到路径
	 * 
	 * @return
	 */
	public String getPath(String filepath) {
		//String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
		//filePath = "/" + filePath+"temp/can/";
		System.out.println("得到CAN路径");
		//String filePath = this.getClass().getClassLoader().getResource("").getPath().toString().substring(1);
		//filePath ="/"+ filePath+"temp/can/";
		//System.out.println(filePath);
		//eturn filePath;
		//return "F:\\tools\\java-svn\\apache-tomcat-7.0.47\\webapps\\InterfacePlateform\\WEB-INF\\classes\\temp\\"+filepath+"\\";
		return "/home/tomcatapp/InterfacePlateform_auto/WebRoot/WebRoot/WEB-INF/classes/temp/"+filepath+"/";
	}
	public String getPathForWin() {
		String relativelyPath ="F:\\tools\\java-svn\\apache-tomcat-7.0.47\\webapps\\InterfacePlateform\\WEB-INF\\classes\\";// System.getProperty("user.dir")+"\\WebRoot\\WEB-INF\\classes\\";
		return relativelyPath;
	}
	public static void main(String[] args) {
		//System.out.println(new SysPathUtil().getPath());
	}
}

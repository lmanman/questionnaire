package com.visionet.letsdesk.app.common.thread;

import java.util.Date;

public class TestThread {



	public static void test1() {
		try{
			System.out.println("---111--------");
			TestThread tt = new TestThread();

			for(int i=0;i<98;i++){
//				System.out.println("i="+i);
				String msg= "Customer"+i;
//				if(i>9){
//					msg= "Customer"+(i/7);
//				}
				try{
					ThreadManage.ThreadMessageQueueAssign(TheadConstants.Thread_Allocate_Free, tt, "allocate", msg, "" + i);
//					ThreadManage.threadAssign("BIZ_JOB", tt, "test",xx,""+i);
				}catch(Exception ex){
					System.err.println("======="+i+"=======");
					ex.printStackTrace();
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}




	public static void testAdd(String flag) throws Exception{
		TestThread tt = new TestThread();
		ThreadManage.ThreadMessageQueueAssign(TheadConstants.Thread_Allocate_Pay, tt, "allocate", "Customer"+flag,flag  );
	}

//	private String setInfo;
	public void allocate(String msg,String flag) throws Exception {
//		setInfo = flag;

//		System.out.println(this.getClass().getName());
		if(msg.equals("Customer1") || msg.equals("Org1")){
			Thread.sleep(2500);
//		}else{
//			Thread.sleep(2500);
		}
//		if(flag.equals("7")){
//			System.out.println("======7======"+new Date());
//			ThreadManage.ThreadWaitingQueueAssignTail(1L,2L, this, "allocate", msg, flag);
//		}

		if(flag.equals("2")){
			System.out.println("======2======"+new Date());
		}
		if(flag.equals("33")){
			System.out.println("=======33====="+new Date());
		}

		if(flag.equals("97")){
			System.out.println("=======97====="+new Date());
		}
		if(flag.equals("100")){
			System.out.println("=======100====="+new Date());
		}
//		System.out.println(msg+"-------"+flag+"----setInfo="+setInfo+"  time:"+new Date());
//		System.out.println(msg+" End!"+flag);
	}



	public static void main(String[] args) throws Exception {
		new ThreadManage().initThread();
		DaemonServer service = new DaemonServer();
		//放入测试任务
		TestThread.test1();
//		initTalks();

		service.init();

		Thread.sleep(2000);
//		System.out.println("-------clear1-------");
//		DaemonServer.TalkList.clear();

		System.out.println("-------add-------");
		TestThread.testAdd("100");
		TestThread.testAdd("101");
//		initTalks();

		Thread.sleep(16000);
		System.out.println("-------clear2-------");
//		TalkList.clear();

//		Thread.sleep(6000);
//		System.out.println("-------destory1-------");
//
//		service.destroy();
		System.out.println("-------end-------");
	}
}

package com.itdragon.keyword;

public class ITDragonClassLock {
	
	/**
	 * ������static + synchronized �������ʾ��������ӡ�Ľ���� thread1 �߳���ִ���꣬Ȼ����ִ��thread2�̡߳�
	 * ��û�б�static ���Σ���thread1 �� thread2 ����ͬʱִ�У�ͬʱ����
	 */
	private synchronized void classLock(String args) {
		System.out.println(args + "start......");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(args + "end......");
	}
	
	public static void main(String[] args) {
		ITDragonClassLock classLock1 = new ITDragonClassLock();
		ITDragonClassLock classLock2 = new ITDragonClassLock();
		
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				classLock1.classLock("classLock1");
			}
		});
		thread1.start();
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				classLock2.classLock("classLock2");
			}
		});
		thread2.start();
	}
	
}

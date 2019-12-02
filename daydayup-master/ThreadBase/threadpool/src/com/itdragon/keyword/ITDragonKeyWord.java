package com.itdragon.keyword;

import org.junit.Test;


public class ITDragonKeyWord {
	
	private Integer count = 5;
	
	/**
	 * �����÷�
	 * synchronized��������������󼰷����ϼ���������������δ����Ϊ"������"��"�ٽ���"
	 * ������ synchronized �ؼ��֣�����߳�ͬʱ�޸����ݣ����ܳ��ֲ���ȷ��ֵ�����ִ�г��򼴿ɿ��������
	 * ��֮�������÷���������ÿ���߳�����ִ�С�
	 */
	private synchronized void methodLock() {
		System.out.println("count = " + count--);
	}
	
	@Test
	public void synchronizedMethodLock() {
		for(int i = 1; i <= 5; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					methodLock();
				}
			});
			thread.start();
		}
	}
	
	public synchronized void synchronizedMethod(){
		try {
			System.out.println("ͬ�� : " + Thread.currentThread().getName());
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void asynchronizedMethod(){
		System.out.println("�첽 : " + Thread.currentThread().getName());
	}

	@Test
	public void main2() {
		
		final ITDragonKeyWord mo = new ITDragonKeyWord();
		
		/**
		 * ������
		 * t1�߳��ȳ���object�����Lock����t2�߳̿������첽�ķ�ʽ���ö����еķ�synchronized���εķ���
		 * t1�߳��ȳ���object�����Lock����t2�߳���������ʱ����ö����е�ͬ����synchronized����������ȴ���Ҳ����ͬ��
		 */
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				mo.synchronizedMethod();
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				mo.asynchronizedMethod();
			}
		},"t2");
		
		t1.start();
		t2.start();
		
	}
}

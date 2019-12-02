package com.itdragon.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * �̳߳�
 * ���ƣ�������ݿ�����ӳ�
 * 1. Ƶ���Ĵ����������̻߳�������������ܴ��ѹ��
 * 2. ���������̲߳����ٶ��������̳߳��еȴ��´�ʹ�ã����ܴ�����Ч��Ҳ�����˷�������ѹ��
 * 
 * workQueue ���ֲ���
 * ֱ���ύ SynchronousQueue
 * �޽���� LinkedBlockingQueue
 * �н���� ArrayBlockingQueue
 * 
 * ���־ܾ�����
 * AbortPolicy : JDKĬ�ϣ����� MAXIMUM_POOL_SIZE �����������쳣 RejectedExecutionException
 * CallerRunsPolicy : ����ֱ�ӵ��ñ��ܾ����������̳߳ر��رգ���������
 * DiscardOldestPolicy : ����������ǰ�������Ȼ�����³���ִ���ܾ����������̳߳ر��رգ���������
 * DiscardPolicy : ��������ִ�е����񵫲����쳣
 */
public class ThreadPoolExecutorStu {
	
	// �̳߳��г�ʼ�̸߳���
	private final static Integer CORE_POOL_SIZE = 3;
	// �̳߳������������߳���
	private final static Integer MAXIMUM_POOL_SIZE = 8;
	// ���߳������ڳ�ʼ�߳�ʱ����ֹ����Ŀ����̵߳ȴ���������ʱ��
	private final static Long KEEP_ALIVE_TIME = 10L;
	// ���񻺴���� �����߳������ڳ�ʼ�߳���ʱ�Ƚ�������еȴ��������ֿ�����΢���ô�㣬�����߳�����������߳���ʱ��������ֱ�����޽����
	private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<Runnable>(5);
	
	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		/**
		 * ITDragonThreadPoolExecutor ��ʱ 1503
		 * ITDragonFixedThreadPool ��ʱ 505
		 * ITDragonSingleThreadExecutor ����
		 * ITDragonCachedThreadPool ��ʱ506
		 * �Ƽ�ʹ���Զ����̳߳أ�
		 */
		ThreadPoolExecutor threadPoolExecutor = ITDragonThreadPoolExecutor();
		for (int i = 0; i < 8; i++) {	// ִ��8������������MAXIMUM_POOL_SIZE��ᱨ�� RejectedExecutionException
			MyRunnableTest myRunnable = new MyRunnableTest(i);
			threadPoolExecutor.execute(myRunnable);
			System.out.println("�̳߳������ڵ��߳���Ŀ�ǣ�"+threadPoolExecutor.getPoolSize()+",  ���������ڵȴ�ִ�е���������Ϊ��"+  
					threadPoolExecutor.getQueue().size());
		}
		// �ص��̳߳� ������������ֹͣ(ֹͣ�����ⲿ��submit���񣬵ȴ��ڲ�������ɺ��ֹͣ)���Ƽ�ʹ�á� ��֮��Ӧ����shutdownNow�����Ƽ�ʹ��
		threadPoolExecutor.shutdown();	
		try {
			// �����ȴ�30��ص��̳߳أ�����true��ʾ�Ѿ��رա���shutdown��ͬ�������Խ����ⲿ���񣬲��һ�����������Ϊ�˷���ͳ��ʱ�䣬����ѡ�������ȴ��رա�
			threadPoolExecutor.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("��ʱ : " + (System.currentTimeMillis() - start));
	}
	
	// �Զ����̳߳أ������Ƽ�ʹ��
	public static ThreadPoolExecutor ITDragonThreadPoolExecutor() {
		// ����һ������ʼ�߳�����Ϊ3������߳�����Ϊ8���ȴ�ʱ��10���� �����г���Ϊ5 ���̳߳�
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
				CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MINUTES, WORK_QUEUE);
		return threadPoolExecutor;
	}
	
	/**
	 * �̶���С�̳߳�
	 * corePoolSize��ʼ�߳�����maximumPoolSize����߳���һ����keepAliveTime�����������ã�workQueue�õ����޽���������
	 */
	public static ThreadPoolExecutor ITDragonFixedThreadPool() {
		ExecutorService executor = Executors.newFixedThreadPool(8);
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
		return threadPoolExecutor;
	}
	
	/**
	 * ���߳��̳߳�
	 * �ȼ���Executors.newFixedThreadPool(1);
	 * FIXME ��֪��������ڣ�
	 */
	public static ThreadPoolExecutor ITDragonSingleThreadExecutor() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
		return threadPoolExecutor;
	}
	
	/**
	 * �޽��̳߳�
	 * corePoolSize ��ʼ�߳���Ϊ��
	 * maximumPoolSize ����߳��������
	 * keepAliveTime 60���ཫû�б��õ����߳���ֹ
	 * workQueue SynchronousQueue ���У����������������ֱ�������߳�
	 * ���Ƽ�ʹ��
	 */
	public static ThreadPoolExecutor ITDragonCachedThreadPool() {
		ExecutorService executor = Executors.newCachedThreadPool();
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
		return threadPoolExecutor;
	}
	
}

class MyRunnableTest implements Runnable {
	private Integer num;	// ����ִ�е�������
	public MyRunnableTest(Integer num) {
		this.num = num;
	}
	public void run() {
		System.out.println("����ִ�е�MyRunnable " + num);
		try {
			Thread.sleep(500);// ģ��ִ��������Ҫ��ʱ
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("MyRunnable " + num + "ִ�����");
	}
}

/**
����ִ�е�MyRunnable 0
�̳߳������ڵ��߳���Ŀ�ǣ�1,  ���������ڵȴ�ִ�е���������Ϊ��0
�̳߳������ڵ��߳���Ŀ�ǣ�2,  ���������ڵȴ�ִ�е���������Ϊ��0
�̳߳������ڵ��߳���Ŀ�ǣ�3,  ���������ڵȴ�ִ�е���������Ϊ��0
�̳߳������ڵ��߳���Ŀ�ǣ�3,  ���������ڵȴ�ִ�е���������Ϊ��1
�̳߳������ڵ��߳���Ŀ�ǣ�3,  ���������ڵȴ�ִ�е���������Ϊ��2
�̳߳������ڵ��߳���Ŀ�ǣ�3,  ���������ڵȴ�ִ�е���������Ϊ��3
�̳߳������ڵ��߳���Ŀ�ǣ�3,  ���������ڵȴ�ִ�е���������Ϊ��4
�̳߳������ڵ��߳���Ŀ�ǣ�3,  ���������ڵȴ�ִ�е���������Ϊ��5
����ִ�е�MyRunnable 1
����ִ�е�MyRunnable 2
MyRunnable 2ִ�����
MyRunnable 0ִ�����
����ִ�е�MyRunnable 3
MyRunnable 1ִ�����
����ִ�е�MyRunnable 5
����ִ�е�MyRunnable 4
MyRunnable 3ִ�����
MyRunnable 4ִ�����
����ִ�е�MyRunnable 7
MyRunnable 5ִ�����
����ִ�е�MyRunnable 6
 */

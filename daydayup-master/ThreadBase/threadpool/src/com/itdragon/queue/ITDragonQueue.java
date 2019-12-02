package com.itdragon.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * ��������
 * ArrayBlockingQueue		���н�
 * LinkedBlockingQueue		���޽�
 * SynchronousQueue			�����ڴ�ֱ����
 * ����������
 * ConcurrentLinkedQueue	��������
 */
public class ITDragonQueue {
	
	/**
	 * ArrayBlockingQueue : �����������������ʵ�֣����ڲ�ά����һ���������飬�Ա㻺������е����ݶ���
	 * �ڲ�û��ʵ�ֶ�д���룬���������Ѳ�����ȫ���У�
	 * ��������Ҫ����ģ�
	 * ����ָ���Ƚ��ȳ������Ƚ������
	 * ��һ���н���С�
	 */
	@Test
	public void ITDragonArrayBlockingQueue() throws Exception {  
        ArrayBlockingQueue<String> array = new ArrayBlockingQueue<String>(5); // ���Գ��� ���г�����3�ĵ�5  
        array.offer("offer �������ݷ���---�ɹ�����true ���򷵻�false");  
        array.offer("offer 3���������ݷ���", 3, TimeUnit.SECONDS);  
        array.put("put �������ݷ���---���������г����������ȴ���û�з���ֵ");  
        array.add("add �������ݷ���---���������г�������ʾ java.lang.IllegalStateException"); //  java.lang.IllegalStateException: Queue full  
        System.out.println(array);
        System.out.println(array.take() + " \t��ʣԪ�� : " + array);   // ��ͷ��ȡ��Ԫ�أ����Ӷ�����ɾ����������Ϊnull��һֱ�ȴ�
        System.out.println(array.poll() + " \t��ʣԪ�� : " + array);   // ��ͷ��ȡ��Ԫ�أ����Ӷ�����ɾ����ִ��poll �� Ԫ�ؼ���һ��
        System.out.println(array.peek() + " \t��ʣԪ�� : " + array);   // ��ͷ��ȡ��Ԫ�أ�ִ��peek ���Ƴ�Ԫ��
    }
	
	/**
	 * LinkedBlockingQueue�������б���������У����ڲ�ά����һ�����ݻ�����У��ö�����һ�������ɣ���
	 * ���ڲ�ʵ�ֲ��ö�д���������ܸ�Ч�Ĵ��������ݣ������ߺ������߲�������ȫ��������
	 * ���Բ�ָ�����ȣ�
	 * ��һ���޽���С�
	 */
	@Test
	public void ITDragonLinkedBlockingQueue() throws Exception {
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		queue.offer("1.�޽����");
		queue.add("2.�﷨��ArrayBlockingQueue���");
		queue.put("3.ʵ�ֲ��ö�д����");
		List<String> list = new ArrayList<String>();
		System.out.println("���ؽ�ȡ�ĳ��� : " + queue.drainTo(list, 2));
		System.out.println("list : " + list);
	}
	
	/**
	 * SynchronousQueue��û�л���Ķ��У�����������������ֱ�ӻᱻ�����߻�ȡ�����ѡ�
	 */
	@Test
	public void ITDragonSynchronousQueue() throws Exception {
		final SynchronousQueue<String> queue = new SynchronousQueue<String>();
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("take , ��û��ȡ��ֵ֮ǰһֱ��������  : " + queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread1.start();
		Thread.sleep(2000);
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				queue.add("��ֵ!!!");
			}
		});
		thread2.start();	
	}

	/**
	 * ConcurrentLinkedQueue����һ���ʺϸ߲��������µĶ��У�ͨ�������ķ�ʽ��ʵ���˸߲���״̬�µĸ����ܣ����ܺ���BlockingQueue��
	 * ����һ���������ӽڵ���޽����̰߳�ȫ���С��ö��е�Ԫ����ѭ�Ƚ��ȳ���ԭ��ͷ�����ȼ���ģ�β��������ģ�������nullԪ�ء�
	 * ���������У�û�� put �� take ����
	 */
	@Test
	public void ITDragonConcurrentLinkedQueue() throws Exception {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();  
        queue.offer("1.������������");
		queue.add("2.�޽����");
		System.out.println(queue);
        System.out.println(queue.poll() + " \t  : " + queue);   // ��ͷ��ȡ��Ԫ�أ����Ӷ�����ɾ����ִ��poll �� Ԫ�ؼ���һ��
        System.out.println(queue.peek() + " \t  : " + queue);   // ��ͷ��ȡ��Ԫ�أ�ִ��peek ���Ƴ�Ԫ��
	}
	
}

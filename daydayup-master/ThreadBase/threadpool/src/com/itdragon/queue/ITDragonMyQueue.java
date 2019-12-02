package com.itdragon.queue;

import java.util.LinkedList;  
import java.util.concurrent.atomic.AtomicInteger;

/**
 * synchronized ������������󼰷����ϼ���������������δ����Ϊ"������"��"�ٽ���"��һ�������������ͨ����С�������ȴӶ�������ܡ�
 * 
 * Atomic* ��Ϊ���ֲ�volatile�ؼ��ֲ��߱�ԭ���Ե����⡣��Ȼһ��Atomic*�����Ǿ߱�ԭ���Եģ�������ȷ�����Atomic*����Ҳ�߱�ԭ���ԡ�
 * 
 * volatile �ؼ��ֲ��߱�synchronized�ؼ��ֵ�ԭ��������Ҫ���þ���ʹ�����ڶ���߳��пɼ���
 * 
 * wait / notify
 * wait() ʹ�߳��������У�notify() ������ѵȴ������еȴ�ͬһ������Դ��һ���̼߳������У�notifyAll() �������еȴ������еȴ�ͬһ������Դ���̼߳������С�
 * 1��wait �� notify ����Ҫ��� synchronized �ؼ���ʹ��
 * 2��wait�������ͷ����ģ� notify�������ͷ���
 */
public class ITDragonMyQueue {
	
	//1 ��Ҫһ����װԪ�صļ���   
    private LinkedList<Object> list = new LinkedList<Object>();  
    //2 ��Ҫһ�������� AtomicInteger (��֤ԭ���ԺͿɼ���)
    private AtomicInteger count = new AtomicInteger(0);  
    //3 ��Ҫ�ƶ����޺�����  
    private final Integer minSize = 0;  
    private final Integer maxSize ;  
      
    //4 ���췽��  
    public ITDragonMyQueue(Integer size){  
        this.maxSize = size;  
    }  
      
    //5 ��ʼ��һ������ ���ڼ���  
    private final Object lock = new Object();  
      
    //put(anObject): ��anObject�ӵ�BlockingQueue��,���BlockQueueû�пռ�,����ô˷������̱߳���ϣ�ֱ��BlockingQueue�����пռ��ټ���.  
    public void put(Object obj){  
        synchronized (lock) {  
            while(count.get() == this.maxSize){  
                try {  
                    lock.wait();  		// ��Queueû�пռ�ʱ���̱߳����� ������Ϊ�����֣�����Ϊwait1
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }  
            list.add(obj);  			//1 ����Ԫ��  
            count.incrementAndGet();  	//2 �������ۼ�  
            lock.notify();  			//3 ����Ԫ�غ�֪ͨ����һ���߳�wait2�����ж���һ��Ԫ�أ��������Ƴ������ˡ� 
            System.out.println("�¼����Ԫ��Ϊ: " + obj);  
        }  
    }  
      
    //take: ȡ��BlockingQueue��������λ�Ķ���,��BlockingQueueΪ��,��Ͻ���ȴ�״ֱ̬��BlockingQueue���µ����ݱ�����.  
    public Object take(){  
        Object ret = null;  
        synchronized (lock) {  
            while(count.get() == this.minSize){  
                try {  
                    lock.wait();  		// ��Queueû��ֵʱ���̱߳����� ������Ϊ�����֣�����Ϊwait2
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }  
            ret = list.removeFirst();  	//1 ���Ƴ�Ԫ�ز���  
            count.decrementAndGet();  	//2 �������ݼ�  
            lock.notify();  			//3 �Ƴ�Ԫ�غ󣬻�������һ���߳�wait1��������Ԫ���ˣ���������Ӳ�����  
        }  
        return ret;  
    }  
      
    public int getSize(){  
        return this.count.get();  
    }  
      
    public static void main(String[] args) throws Exception{  
        final ITDragonMyQueue queue = new ITDragonMyQueue(5);  
        queue.put("a");  
        queue.put("b");  
        queue.put("c");  
        queue.put("d");  
        queue.put("e");  
        System.out.println("��ǰ�����ĳ���: " + queue.getSize());  
        Thread thread1 = new Thread(new Runnable() {  
            @Override  
            public void run() {  
                queue.put("f");  
                queue.put("g");  
            }  
        },"thread1");  
        Thread thread2 = new Thread(new Runnable() {  
            @Override  
            public void run() {  
                System.out.println("�Ƴ���Ԫ��Ϊ:" + queue.take());  // �Ƴ�һ��Ԫ�غ��ٽ�һ����������ͬʱ�Ƴ���������������Ԫ�ء�
                System.out.println("�Ƴ���Ԫ��Ϊ:" + queue.take());  
            }  
        },"thread2");  
        thread1.start();  
        Thread.sleep(2000);
        thread2.start();  
    }  
}
/*
�¼����Ԫ��Ϊ: a
�¼����Ԫ��Ϊ: b
�¼����Ԫ��Ϊ: c
�¼����Ԫ��Ϊ: d
�¼����Ԫ��Ϊ: e
��ǰ�����ĳ���: 5
�Ƴ���Ԫ��Ϊ:a
�¼����Ԫ��Ϊ: f
�Ƴ���Ԫ��Ϊ:b
�¼����Ԫ��Ϊ: g
 */

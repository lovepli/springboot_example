package com.itdragon.keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile �ؼ�����Ҫ���þ���ʹ�����ڶ���߳��пɼ���
 * volatile �ؼ��ֲ��߱�ԭ���ԣ���Atomic���Ǿ߱�ԭ���ԺͿɼ��ԡ�
 * ���в�����Ƕ��Atomic�಻�߱�ԭ���ԣ�������Ҫsynchronized �ؼ��ְ�æ��
 */
public class ITDragonVolatile{
	
    private volatile boolean flag = true;  
    private static volatile int count;   
    private static AtomicInteger atomicCount = new AtomicInteger(0); // �� static ��Ϊ�˱���ÿ��ʵ��������ʱ��ʼֵΪ��
    
    //	����volatile �ؼ��ֵĿɼ���
    private void volatileMethod() {
    	System.out.println("thread start !");  
        while (flag) {  // ���flagΪtrue��һֱ���������У�
        }  
        System.out.println("thread end !");
    }
    
    //	��֤volatile �ؼ��ֲ��߱�ԭ����
    private int volatileCountMethod() {
    	for (int i = 0; i < 10; i++) {
    		// ��һ���̻߳�δ��count�ӵ�10��ʱ�򣬾Ϳ��ܱ���һ���߳̿�ʼ�޸ġ����ܻᵼ�����һ�δ�ӡ��ֵ����1000
        	count++ ;	
        }  
        return count;
    }
    
    //	��֤Atomic�����ԭ����
    private int atomicCountMethod() {
    	for (int i = 0; i < 10; i++) {  
    		atomicCount.incrementAndGet();  
    	}  
    	// �����һ�δ�ӡΪ1000���ʾ�߱�ԭ���ԣ��м��ӡ����Ϣ��������println�ӳ�Ӱ�졣
    	return atomicCount.get();// �����һ�δ�ӡΪ1000���ʾ�߱�ԭ����
    }
    
    // ��֤��� Atomic��������߱�ԭ���ԣ���synchronized�ؼ������μ���
    private synchronized int multiAtomicMethod(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		atomicCount.addAndGet(1);
		atomicCount.addAndGet(2);
		atomicCount.addAndGet(3);
		atomicCount.addAndGet(4); 
		return atomicCount.get(); //���߱�ԭ���ԣ��򷵻صĽ��һ������10�ı������������в��ܿ������
    }
  
    /**
     * volatile �ؼ��ֽ��
     * �����������߳�	��һ����main�����̣߳�һ����thread�����߳�
     * jdk�̹߳�������	��Ϊ�����Ч�ʣ�ÿ���̶߳���һ�������ڴ棬�����ڴ�ı�������һ�ݵ������ڴ��С��̵߳�ִ�������ֱ�Ӵӹ����ڴ��л�ȡ������
     * So ��������		��thread�߳��õ����Լ��Ĺ����ڴ棬���߳̽������޸ĺ�thread�̲߳�֪������������ݲ��ɼ������⡣
     * �������		��������volatile �ؼ������κ��̵߳�ִ�������ֱ�Ӵ����ڴ��л�ȡ������
     * 
     */
    public static void main(String[] args) throws InterruptedException {  
//    	����volatile �ؼ��ֵĿɼ���
    	/*ITDragonVolatile itDragonVolatile = new ITDragonVolatile();  
    	Thread thread = new Thread(itDragonVolatile);
    	thread.start();
    	Thread.sleep(1000);  // ���߳������ˣ�������ֵ
    	itDragonVolatile.setFlag(false);  
    	System.out.println("flag : " + itDragonVolatile.isFlag());*/  
    	
//    	��֤volatile �ؼ��ֲ��߱�ԭ���� �� Atomic�����ԭ����
    	final ITDragonVolatile itDragonVolatile = new ITDragonVolatile();
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 100; i++) {
			threads.add(new Thread(new Runnable() {
				@Override
				public void run() {
					// �м��ӡ����Ϣ��������println�ӳ�Ӱ�죬�뿴���һ�δ�ӡ�Ľ��
					System.out.println(itDragonVolatile.multiAtomicMethod());
				}
			}));
		}
		for(Thread thread : threads){
			thread.start();
		}
    }  
      
    public boolean isFlag() {  
        return flag;  
    }  
  
    public void setFlag(boolean flag) {  
        this.flag = flag;  
    }  

}

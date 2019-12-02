package com.itdragon.keyword;

/**
 * ����: �߳�Aӵ����һ��ȴ����Ҫ�������߳�Bӵ��������ȴ����Ҫ��һ�������̻߳������ã������߳̽���Զ�ȴ���
 * ����: ����ƽ׶Σ��˽������Ⱥ�˳�򣬼������Ľ���������
 * �Ų�: 
 * ��һ��������̨���� jps ���ڻ�õ�ǰJVM���̵�pid
 * �ڶ�����jstack pid ���ڴ�ӡ��ջ��Ϣ 
 * "Thread-1" #11 prio=5 os_prio=0 tid=0x0000000055ff1800 nid=0x1bd4 waiting for monitor entry [0x0000000056e2e000]
 * - waiting to lock <0x00000000ecfdf9d0> - locked <0x00000000ecfdf9e0> 
 * "Thread-0" #10 prio=5 os_prio=0 tid=0x0000000055ff0800 nid=0x1b14 waiting for monitor entry [0x0000000056c7f000]
 * - waiting to lock <0x00000000ecfdf9e0> - locked <0x00000000ecfdf9d0> 
 * ���Կ����������̳߳��е������ǶԷ���Ҫ�õ�����(�ò�������Զ��ɧ��)���������һ��Ҳ��ӡ�� Found 1 deadlock.
 */
public class ITDragonDeadLock {
	
	private final Object left = new Object();
    private final Object right = new Object();
    
    public void leftRight(){
        synchronized (left) {
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            synchronized (right) {
                System.out.println("leftRight end!");
            }
        }
    }
    
    public void rightLeft(){
        synchronized (right) {
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            synchronized (left) {
                System.out.println("rightLeft end!");
            }
        }
    }
    
    public static void main(String[] args) {
    	ITDragonDeadLock itDragonDeadLock = new ITDragonDeadLock();
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				itDragonDeadLock.leftRight();
			}
		});
		thread1.start();
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				itDragonDeadLock.rightLeft();
			}
		});
		thread2.start();
	}

}

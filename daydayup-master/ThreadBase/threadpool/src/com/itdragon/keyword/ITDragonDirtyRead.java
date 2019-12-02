package com.itdragon.keyword;

/**
 * synchronized ͬ���첽
 * 
 * @author itdragon
 *
 */
public class ITDragonDirtyRead {
	
	private String username = "ITDragon";
	private Double amount = 100.0;
	
	public synchronized void setAmount(String username, Double amount){
		this.username = username;
		try {
			Thread.sleep(2000); // ������Ԥ�ƺ�ʱ����
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.amount = amount;
		System.out.println("setValue : username = " + username + " , amount = " + amount);
	}
	
	public synchronized void getAmount(){
		System.out.println("getValue : username = " + this.username + " , amount = " + this.amount);
	}
	
	/**
	 * ������1.������Ԥ�ƺ�ʱ���� �� 2.һ����ѯ���
	 * ���� setValue �� getValue �����������synchronized�ؼ������Σ���setValue ����ִ�н�������ִ��getValue�����������ݵĲ�һ�¡�
	 */
	public static void main(String[] args) throws Exception{
		final ITDragonDirtyRead dirtyRead = new ITDragonDirtyRead();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				dirtyRead.setAmount("ITDragon", 50.0); // ������50 RMB		
			}
		});
		thread.start();
		Thread.sleep(1000); // ��δ�������������鿴���
		dirtyRead.getAmount();
	}

}

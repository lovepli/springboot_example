package com.itdragon.threadpool;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ����Thread1��Thread2��Thread3��Thread4�����̷ֱ߳�ͳ��C��D��E��F�ĸ��̵Ĵ�С�������̶߳�ͳ����Ͻ���Thread5�߳�ȥ�����ܣ�Ӧ�����ʵ�֣�
 * ˼�������ܣ�˵��Ҫ���ĸ��̵߳Ľ�����ظ�������̣߳���Ҫ�߳��з���ֵ���Ƽ�ʹ��callable��Thread��Runnable��û����ֵ
 */
public class ITDragonThreads {  
	
    public static void main(String[] args) throws Exception {  
    	// �޻����޽��̳߳�
        ExecutorService executor = Executors.newFixedThreadPool(8); 
        // ���ExecutorService��CompletionService���Ը���ȷ�ͼ�������첽�����ִ��
        CompletionService<Long> completion = new ExecutorCompletionService<Long>(executor);  
        CountWorker countWorker = null;  
		for (int i = 0; i < 4; i++) { // �ĸ��̸߳���ͳ��
            countWorker = new CountWorker(i+1);  
            completion.submit(countWorker);  
        }  
		// �ر��̳߳�
        executor.shutdown();  
        // ���߳��൱�ڵ�����̣߳����ڻ�������
        long total = 0;  
		for (int i = 0; i < 4; i++) { 
			total += completion.take().get(); 
        }  
        System.out.println(total / 1024 / 1024 / 1024 +"G");  
    }  
}  
  
class CountWorker implements Callable<Long>{  
	
    private Integer type;  
    
    public CountWorker() {
	}

	public CountWorker(Integer type) {
		this.type = type;
	}

    @Override  
    public Long call() throws Exception {  
    	ArrayList<String> paths = new ArrayList<>(Arrays.asList("c:", "d:", "e:", "f:"));
        return countDiskSpace(paths.get(type - 1));  
    }  
    
    // ͳ�ƴ��̴�С
    private Long countDiskSpace (String path) {  
        File file = new File(path);  
        long totalSpace = file.getTotalSpace();  
        long freeSpace = file.getFreeSpace();  
        long usedSpace = totalSpace - freeSpace;  
        System.out.println(path + " �ܿռ��С : " + totalSpace / 1024 / 1024 / 1024 + "G");  
//        System.out.println(path + " ʣ��ռ��С : " + freeSpace / 1024 / 1024 / 1024 + "G");  
//        System.out.println(path + " ���ÿռ��С : " + usedSpace / 1024 / 1024 / 1024 + "G");  
        return totalSpace;
    }  
    
}  
package com.shenma.top.imagecopy.util.auto;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Counter extends Thread {
    static Random random = new Random();
    ConcurrentMapCounter cc;
    CountDownLatch latch;
    final String[] pages = {"a","b", "c"};

    public Counter(ConcurrentMapCounter cc, CountDownLatch latch) {
        this.cc = cc;
        this.latch = latch;
    }

    public void run() {
        String view = pages[random.nextInt(3)];
        int i = cc.increment(view);
        System.out.println(Thread.currentThread().getName()
                + "第" + i + "个访问页面:" + view);
        latch.countDown();
    }


    public static void main(String[] args) throws InterruptedException {
        ConcurrentMapCounter cc = new ConcurrentMapCounter();
        int count = 1000;
        CountDownLatch latch = new CountDownLatch(count);
        ExecutorService service = Executors.newFixedThreadPool(30);
        for (int i = 0; i < count; i++) {// 1000 个线程
            service.execute(new Counter(cc, latch));
        }
        latch.await();

        long result = 0;
        Map<String, Integer> map = cc.getMap();
        for(Map.Entry<String, Integer> s : map.entrySet()) {
            System.out.println("页面:" + s.getKey() + "被访问:" + s.getValue());
            result += s.getValue();
        }
        System.out.println("result:" + result);
        service.shutdown();
    }
}
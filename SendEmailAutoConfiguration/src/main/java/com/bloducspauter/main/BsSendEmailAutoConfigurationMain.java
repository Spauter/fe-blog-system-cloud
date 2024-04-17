package com.bloducspauter.main;

import java.util.concurrent.CountDownLatch;

/**
 * @author liukai
 * @since 2016/6/9.
 */
public class BsSendEmailAutoConfigurationMain {

    static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        start();
    }

    static void start() {
        System.out.println("开始");
        try {
            new Thread(new Service(countDownLatch)).start();
            countDownLatch.await();
            System.out.println("程序退出");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Service implements Runnable {
    private CountDownLatch countDownLatch;

    public Service(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            try {
                Thread.sleep(200);
                if (i == 50) {
                    this.countDownLatch.countDown();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
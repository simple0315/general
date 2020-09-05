package com.simple.general.thread;

/**
 * runnable
 *
 * @author Mr.Wu
 * @date 2020/9/5 23:02
 */
public class RunnableDemo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("RunnableDemo:" + i);
        }
    }

    public static void main(String[] args) {
        RunnableDemo runnableDemo = new RunnableDemo();
        Thread thread = new Thread(runnableDemo);
        Thread thread1 = new Thread(runnableDemo);
        thread.start();
        System.out.println(thread.getName());
        thread1.start();
        System.out.println(thread1.getName());
        for (int i = 0; i < 10; i++) {
            System.out.println("主线程:" + i);
        }
    }
}

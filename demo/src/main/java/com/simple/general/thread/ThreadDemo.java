package com.simple.general.thread;

/**
 * ThreadDemo
 *
 * @author Mr.Wu
 * @date 2020/9/5 21:39
 */
public class ThreadDemo extends Thread {

    public ThreadDemo(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 10; i < 20; i++) {
            System.out.println(getName() + "正在执行:" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 20; i < 30; i++) {
            System.out.println("main线程:" + i);
        }
        ThreadDemo threadDemo = new ThreadDemo("线程demo");
        threadDemo.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("main线程:" + i);
        }
    }
}

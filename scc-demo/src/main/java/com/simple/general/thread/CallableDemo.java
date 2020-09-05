package com.simple.general.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Callable
 *
 * @author Mr.Wu
 * @date 2020/9/5 23:15
 */
public class CallableDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("学习使我快乐!" + i);
            Thread.sleep(500);
            System.out.println("学习ing:" + Thread.currentThread().getName() + i);
            System.out.println("over!" + i);
        }
        return 1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CallableDemo callableDemo = new CallableDemo();
        Future<Integer> submit = executorService.submit(callableDemo);
        Future<Integer> submit1 = executorService.submit(callableDemo);
        System.out.println(submit.get());
        System.out.println(submit1.get());
        System.out.println(submit.get() + submit1.get());
        for (int i = 0; i < 10; i++) {
            System.out.println("主线程:" + i);
        }
        executorService.shutdown();
    }
}

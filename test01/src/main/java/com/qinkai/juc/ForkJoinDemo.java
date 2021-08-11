package com.qinkai.juc;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {

    /**
     * 生成一个计算任务，计算1+2+3.........+1000
     * @param args
     */
    public static void main(String[] args) {
        //定义任务
        TaskExample taskExample = new TaskExample(1, 1000);
        //定义执行对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //加入任务执行
        ForkJoinTask<Long> result = forkJoinPool.submit(taskExample);
        //输出结果
        try {
            System.out.println(result.get());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            forkJoinPool.shutdown();
        }
    }

}

class TaskExample extends RecursiveTask<Long> {
    private int start;
    private int end;
    private long sum;

    /**
     * 构造函数
     *
     * @param start * @param end
     */
    public TaskExample(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        System.out.println(Thread.currentThread().getName()+"执行任务" + start + "=========" + end + "累加开始");
        //大于100 个数相加切分,小于直接加
        if (end - start <= 100) {
            for (int i = start; i <= end; i++) {
                //累加
                sum += i;
            }
        } else {
            //切分为2 块
            int middle = start + 100;
            //递归调用,切分为2 个小任务
            TaskExample taskExample1 = new TaskExample(start, middle);
            TaskExample taskExample2 = new TaskExample(middle + 1, end);
            //执行:异步
            taskExample1.fork();
            taskExample2.fork();
            //同步阻塞获取执行结果
            sum = taskExample1.join() + taskExample2.join();
        }
        //加完返回
        return sum;
    }
}

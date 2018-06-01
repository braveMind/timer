package com.jun.timer;

import java.io.IOException;

/**
 * @author jun
 * @Date 18/4/11 .
 * @des:
 */
public class ThradA {
    /**
     * 计算输出其他线程锁计算的数据
     *
     */

        public static void main(String[] args) throws InterruptedException, IOException {
            ThreadB b = new ThreadB();
            b.setName("b thread");
            ThreadC c = new ThreadC();
            c.setName("c thread");
            //启动计算线程
            b.start();
            c.start();
            //线程A拥有b对象上的锁。线程为了调用wait()或notify()方法，该线程必须是那个对象锁的拥有者
            synchronized (b) {
                System.out.println("等待对象b完成计算。。。");
                //当前线程A等待
                b.wait();
                System.out.println("---bb----");
                synchronized (c){
                    System.out.println("cc--lll---");
                    c.wait();
                    System.out.println("ccc");
                    System.out.println(c.total);
                }

                System.out.println("b对象计算的总和是：" + b.total);
            }
            System.in.read();
        }




    /**
     * 计算1+2+3 ... +100的和
     *
     */
    static class ThreadB extends Thread {
        int total;

        public void run() {
            System.out.println("线程a--");

            synchronized (this) {
                for (int i = 0; i < 101; i++) {
                    total += i;
                }
                //（完成计算了）唤醒在此对象监视器上等待的单个线程，在本例中线程A被唤醒
               notify();
                try {
                    Thread.sleep(3000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("计算完成");
            }
        }
    }

    static class ThreadC extends Thread {
        int total;

        public void run() {
            synchronized (this) {
                for (int i = 0; i < 101; i++) {
                    total += i;
                }
                //（完成计算了）唤醒在此对象监视器上等待的单个线程，在本例中线程A被唤醒
                notifyAll();

                try {
                    System.out.println("线程C--");
                    Thread.sleep(4000l);
                    System.out.println("aaa");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("计算完成");
            }
        }
    }
}

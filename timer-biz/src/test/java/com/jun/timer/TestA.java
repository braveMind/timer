package com.jun.timer;

import java.util.Vector;

/**
 * @author jun
 * @Date 18/4/12 .
 * @des:
 */
public class TestA {
    public static void main(String[] args) {
        TestA t=new TestA();
        TestA b=t;
        t=null;
        System.out.println(b);

        /* Vector v = new Vector();
        for (int i = 1; i<100; i++)
        {
            Object o = new Object();
            v.add(o);
            o = null;
        }
        System.out.println(v);*/
    }
}

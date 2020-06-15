package com.rustli.androidlearning;

import android.os.Looper;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class MainActivityTest {

    @Test
    public void test111() {
        Looper.prepare();
        MainActivity calculator = new MainActivity();
        String sum = calculator.jUnitTest();
        Assert.assertEquals("jUnitTest", sum);
        Looper.loop();
    }
}
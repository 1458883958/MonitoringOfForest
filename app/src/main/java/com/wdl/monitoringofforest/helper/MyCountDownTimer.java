package com.wdl.monitoringofforest.helper;

import android.os.CountDownTimer;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.monitoringofforest.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/4 16:29
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class MyCountDownTimer extends CountDownTimer {
    public static long currentMillis = 0;
    public static boolean FLAG_FIRST_IN = true;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
    }

    @Override
    public void onFinish() {
    }

    public void timeStart(boolean flag) {
        if (flag) {
            MyCountDownTimer.currentMillis = System.currentTimeMillis();
        }
        MyCountDownTimer.FLAG_FIRST_IN = false;
        this.start();
    }
}
package com.wdl.monitoringofforest.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.alipay.AlipayConstance;
import com.wdl.monitoringofforest.alipay.OrderInfoUtil2_0;
import com.wdl.monitoringofforest.alipay.PayResult;
import com.wdl.utils.LogUtils;
import java.util.Map;
import butterknife.OnClick;


public class ShopActivity extends ToolbarActivity {

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ShopActivity.this, R.string.data_pay_succeed, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.equals(resultStatus, "4006")) {
                        Toast.makeText(ShopActivity.this, R.string.data_pay_isv_insufficient_isv_permissions, Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ShopActivity.this, R.string.data_pay_failed, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 支付宝支付业务
     */
    public void payV2() {
        if (TextUtils.isEmpty(AlipayConstance.APPID) || (TextUtils.isEmpty(AlipayConstance.RSA2_PRIVATE) && TextUtils.isEmpty(AlipayConstance.RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        //秘钥验证的类型 true:RSA2 false:RSA
        final boolean rsa = true;
        //构造支付订单参数列表
        Map<String, String> paramMap = OrderInfoUtil2_0
                .buildOrderParamMap(AlipayConstance.APPID, rsa);
        //构造支付订单参数信息
        String orderParam = OrderInfoUtil2_0.buildOrderParam(paramMap);
        //对支付参数信息进行签名
        String sign = OrderInfoUtil2_0.getSign(paramMap, AlipayConstance.RSA2_PRIVATE, rsa);
        //订单信息
        final String orderIn = orderParam + "&" + sign;
        //异步处理支付情况
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                //新建支付任务
                PayTask payTask = new PayTask(ShopActivity.this);
                //支付结果
                Map<String, String> result = payTask.payV2(orderIn, true);
                LogUtils.e(result.toString());
                Message message = new Message();
                message.what = SDK_PAY_FLAG;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    @OnClick(R.id.pay)
    void aliPay() {
        payV2();
    }

    /**
     * 显示入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, ShopActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_shop;
    }

}

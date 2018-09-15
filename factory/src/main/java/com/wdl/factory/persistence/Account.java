package com.wdl.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wdl.factory.Factory;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.model.db.UserDb_Table;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.persistence
 * 创建者：   wdl
 * 创建时间： 2018/8/5 11:18
 * 描述：    SharedPreferences 持久化数据
 */
@SuppressWarnings("unused")
public class Account {
    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String REQUEST_TIME = "REQUEST_TIME";
    //设备的推送id
    private static String pushId;
    //设备是否绑定到服务器
    private static boolean bind;
    //登录的账号
    private static String account;
    //百度token
    private static String token;
    //用户id
    private static Integer userId;
    //请求时间
    private static long requestTime;

    public static boolean isBind() {
        return bind;
    }

    public static void setBind(boolean bind) {
        Account.bind = bind;
        Account.save(Factory.application());
    }

    public static long getRequestTime() {
        return requestTime;
    }

    public static void setRequestTime(long requestTime) {
        Account.requestTime = requestTime;
        Account.save(Factory.application());
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Account.token = token;
        Account.save(Factory.application());
    }

    public static String getPushId() {
        return pushId;
    }

    public static void setPushId(String pushId) {
        Account.pushId = pushId;
        Account.save(Factory.application());
    }

    /**
     * @return 登录的账号
     */
    public static String getAccount() {
        return account;
    }

    /**
     * 重新设置account信息并同步到xml
     *
     * @param account 登录的账号
     */
    public static void setAccount(String account) {
        Account.account = account;
        Account.save(Factory.application());
    }

    /**
     * @return userId
     */
    public static Integer getUserId() {
        return userId;
    }

    /**
     * 重新设置userId信息并同步到xml
     *
     * @param userId userId
     */
    public static void setUserId(Integer userId) {
        Account.userId = userId;
        Account.save(Factory.application());
    }

    /**
     * 保存信息到xml文件
     *
     * @param user User
     */
    public static void saveUserInfo(User user) {
        Account.account = user.getuTelephone();
        Account.userId = user.getuId();
        save(Factory.application());
    }

    /**
     * 真正的存储xml方法
     *
     * @param context 上下文
     */
    private static void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        sp.edit()
                .putInt(KEY_USER_ID, userId)
                .putString(KEY_ACCOUNT, account)
                .putString(KEY_PUSH_ID, pushId)
                .putBoolean(KEY_IS_BIND,bind)
                .putString(KEY_TOKEN,token)
                .putLong(REQUEST_TIME,requestTime)
                .apply();
    }

    /**
     * 清除sharedPreferences的内容
     *
     * @param context 上下文
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        sp.edit()
                .clear()
                .apply();
    }

    /**
     * 加载用户信息
     *
     * @param context 上下文
     */
    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        userId = sp.getInt(KEY_USER_ID, -1);
        account = sp.getString(KEY_ACCOUNT, "");
        pushId = sp.getString(KEY_PUSH_ID, "");
        token = sp.getString(KEY_TOKEN,"");
        bind = sp.getBoolean(KEY_IS_BIND,false);
        requestTime = sp.getLong(REQUEST_TIME,0L);
    }

    /**
     * 判断是否已经登录
     *
     * @return true 已经登录
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(String.valueOf(userId)) && !TextUtils.isEmpty(account);
    }

    /**
     * 返回一个用户信息
     *
     * @return UserDb
     */
    public static UserDb getUserDb() {
        return userId == -1 ? new UserDb() :
                SQLite.select()
                        .from(UserDb.class)
                        .where(UserDb_Table.id.eq(userId))
                        .querySingle();
    }


}

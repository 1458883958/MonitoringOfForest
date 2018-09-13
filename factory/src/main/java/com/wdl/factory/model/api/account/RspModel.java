package com.wdl.factory.model.api.account;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.api.account
 * 创建者：   wdl
 * 创建时间： 2018/8/3 15:39
 * 描述：    返回的model
 */
@SuppressWarnings("unused")
public class RspModel<Object> {
    private int status;
    private String msg;
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RspModel{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

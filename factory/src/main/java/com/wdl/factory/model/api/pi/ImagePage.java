package com.wdl.factory.model.api.pi;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.api.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/24 11:10
 * 描述：    TODO
 */
public class ImagePage {

    /**
     * pId : 1
     * pageNum : 1
     * pageSize : 10
     */

    private int pId;
    private int pageNum;  //当前页数
    private int pageSize; //当前页面总数量

    public int getPId() {
        return pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

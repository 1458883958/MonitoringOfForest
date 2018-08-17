package com.wdl.factory.model.card;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.model.card
 * 创建者：   wdl
 * 创建时间： 2018/8/16 21:36
 * 描述：    科大讯飞语音识别实体类
 */

/**
 * 解析 语音听写返回结果Json格式字符串 的模板类（多重嵌套Json）
 *
 * 语音识别结果Json数据格式（单条数据）：
 * {"sn":1,"ls":true,"bg":0,"ed":0,"ws":[
 * {"bg":0,"cw":[{"w":"今天","sc":0}]},
 * {"bg":0,"cw":[{"w":"的","sc":0}]},
 * {"bg":0,"cw":[{"w":"天气","sc":0}]},
 * {"bg":0,"cw":[{"w":"怎么样","sc":0}]},
 * {"bg":0,"cw":[{"w":"。","sc":0}]}
 * ]}
 *
 * sn number :第几句
 * ls boolean: 是否最后一句
 * bg number :开始
 * ed number :结束
 * ws array :词
 * cw array :中文分词
 * w string :单字
 * sc number :分数
 */
@SuppressWarnings("unused")
public class DictationResult {
    private int sn;
    private boolean ls;
    private int bg;
    private int ed;
    private List<Words> ws;
    public static class Words {
        private int bg;
        private List<Cw> cw;
        public static class Cw {
            private String w;
            private int sc;

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }

            public int getSc() {
                return sc;
            }

            public void setSc(int sc) {
                this.sc = sc;
            }

            @Override
            public String toString() {
                return w;
            }
        }

        public int getBg() {
            return bg;
        }

        public void setBg(int bg) {
            this.bg = bg;
        }

        public List<Cw> getCw() {
            return cw;
        }

        public void setCw(List<Cw> cw) {
            this.cw = cw;
        }

        @Override
        public String toString() {
            String result = "";
            for (Cw cwTmp : cw) {
                result += cwTmp.toString();
            }
            return result;
        }
    }
    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public boolean isLs() {
        return ls;
    }

    public void setLs(boolean ls) {
        this.ls = ls;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getEd() {
        return ed;
    }

    public void setEd(int ed) {
        this.ed = ed;
    }


    public List<Words> getWs() {
        return ws;
    }

    public void setWs(List<Words> ws) {
        this.ws = ws;
    }

    @Override
    public String toString() {
        String result = "";
        for (Words wsTmp : ws) {
            result += wsTmp.toString();
        }
        return result;
    }
}
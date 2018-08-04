package com.been;

import java.util.List;

public class Ranking {


    /**
     * code : 1
     * data : [{"department":"企划","headImg":"http://p.qlogo.cn/bizmail/LwJTl27DkpriceXIrWNlqEcVWyeCFPazAFia5b9zLicVg5YxqVia6qsYrQ/0","id":85,"integralList":[{"countIntegral":1326}],"jobNumber":0,"listIntegral":[],"listLabel":[],"lotteryList":[],"mobile":"18569033040","nickName":"舒椰","position":"技术","reserve3":"915287336@qq.com","reserve4":"MaLiYaNaHaiGouDeDaWangWuZei","roleList":[],"username":"舒椰"},{"department":"企划","headImg":"http://p.qlogo.cn/bizmail/uDYr3R1m836MyibdoorBT8xWcicsZZ8wic560V0grSNKTAXYfjibfkOictA/0","id":74,"integralList":[{"countIntegral":1320}],"jobNumber":0,"listIntegral":[],"listLabel":[],"lotteryList":[],"mobile":"18377615701","nickName":"莫健兰","position":"平面设计","reserve3":"807075927@qq.com","reserve4":"MoJianLan","roleList":[],"username":"莫健兰"},{"department":"企划","headImg":"http://p.qlogo.cn/bizmail/CTecziaPfAlvOoiciaov7glMBYts7473MJOXlhAK1iatv3yJM3qaLibKJqQ/0","id":73,"integralList":[{"countIntegral":980}],"jobNumber":0,"listIntegral":[],"listLabel":[],"lotteryList":[],"mobile":"15915964842","nickName":"韦晓强","position":"UI设计师","reserve3":"65616@qq.com","reserve4":"WeiXiaoQiang","roleList":[],"username":"韦晓强"},{"department":"企划","headImg":"http://p.qpic.cn/wwhead/duc2TvpEgSSWiaVLaJnssacGbAnGLOroZRuaOTLsPuognyDrbVScyRPlSMWsHKBzuRz11ODkCt5U/0","id":3,"integralList":[{"countIntegral":623}],"jobNumber":1111,"listIntegral":[],"listLabel":[],"lotteryList":[],"mobile":"13002075790","nickName":"晚安","position":"positi","reserve3":"3528184926@qq.com","reserve4":"YeHaoFeng","roleList":[],"username":"叶浩锋"},{"department":"企划","headImg":"http://p.qpic.cn/wwhead/duc2TvpEgSSWiaVLaJnssaZsct7McfYJqPshA2TYQSpNia5sbp3wHNN5hv7C1OnIX8k88Cf1ZTtoM/0","id":72,"integralList":[{"countIntegral":100}],"jobNumber":0,"listIntegral":[],"listLabel":[],"lotteryList":[],"mobile":"13342850504","nickName":"周韪","position":"总   监","reserve3":"1592725704@qq.com","reserve4":"ZhouWeikey","roleList":[],"username":"周韪"}]
     * message : 查询成功
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * department : 企划
         * headImg : http://p.qlogo.cn/bizmail/LwJTl27DkpriceXIrWNlqEcVWyeCFPazAFia5b9zLicVg5YxqVia6qsYrQ/0
         * id : 85
         * integralList : [{"countIntegral":1326}]
         * jobNumber : 0
         * listIntegral : []
         * listLabel : []
         * lotteryList : []
         * mobile : 18569033040
         * nickName : 舒椰
         * position : 技术
         * reserve3 : 915287336@qq.com
         * reserve4 : MaLiYaNaHaiGouDeDaWangWuZei
         * roleList : []
         * username : 舒椰
         */

        private String headImg;
        private String username;
        private List<IntegralListBean> integralList;

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<IntegralListBean> getIntegralList() {
            return integralList;
        }

        public void setIntegralList(List<IntegralListBean> integralList) {
            this.integralList = integralList;
        }

        public static class IntegralListBean {
            /**
             * countIntegral : 1326
             */

            private int countIntegral;

            public int getCountIntegral() {
                return countIntegral;
            }

            public void setCountIntegral(int countIntegral) {
                this.countIntegral = countIntegral;
            }
        }
    }
}


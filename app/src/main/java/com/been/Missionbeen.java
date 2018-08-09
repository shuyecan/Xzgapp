package com.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class Missionbeen {


    /**
     * completeItemsItems : []
     * items : [{"award":5000,"deduct":3000,"endDate":"2018-10-30","headImg":{"headImg":"http://p.qpic.cn/wwhead/duc2TvpEgSSWiaVLaJnssaa8jHa1vcSnWfPPuEuaJBCkW8Nmdv5LBMHJ7aoIV1TrBm1mBa9tyNQ0/0"},"manNum":0,"missionDate":1540742400000,"missionId":380,"missionName":"好享记小程序开发","publisher":"莫景绘","publisherId":91,"startDate":"2018-07-30","status":1,"taskStatus":0},{"award":5000,"deduct":3000,"endDate":"2018-10-30","headImg":{"headImg":"http://p.qpic.cn/wwhead/duc2TvpEgSSWiaVLaJnssaa8jHa1vcSnWfPPuEuaJBCkW8Nmdv5LBMHJ7aoIV1TrBm1mBa9tyNQ0/0"},"manNum":0,"missionDate":1540742400000,"missionId":379,"missionName":"好享记小程序开发","publisher":"莫景绘","publisherId":91,"startDate":"2018-07-30","status":1,"taskStatus":0},{"award":5000,"deduct":3000,"endDate":"2018-10-30","headImg":{"headImg":"http://p.qpic.cn/wwhead/duc2TvpEgSSWiaVLaJnssaa8jHa1vcSnWfPPuEuaJBCkW8Nmdv5LBMHJ7aoIV1TrBm1mBa9tyNQ0/0"},"manNum":0,"missionDate":1540742400000,"missionId":378,"missionName":"好享记小程序开发","publisher":"莫景绘","publisherId":91,"startDate":"2018-07-30","status":1,"taskStatus":0},{"award":5000,"deduct":3000,"endDate":"2018-10-30","headImg":{"headImg":"http://p.qpic.cn/wwhead/duc2TvpEgSSWiaVLaJnssaa8jHa1vcSnWfPPuEuaJBCkW8Nmdv5LBMHJ7aoIV1TrBm1mBa9tyNQ0/0"},"manNum":0,"missionDate":1540742400000,"missionId":377,"missionName":"好享记小程序开发","publisher":"莫景绘","publisherId":91,"startDate":"2018-07-30","status":1,"taskStatus":0},{"award":5000,"deduct":3000,"endDate":"2018-10-30","headImg":{"headImg":"http://p.qlogo.cn/bizmail/bNUz6ejOgQbFobnS3E7pRkiaEGlRSuOPjFDSgUNiaIicD7PF1svfUdKpA/0"},"manNum":0,"missionDate":1540742400000,"missionId":376,"missionName":"好享记小程序开发","publisher":"麦立丰","publisherId":10,"startDate":"2018-07-30","status":1,"taskStatus":0}]
     * pageSize : 10
     * ranking : 0
     * startRow : 0
     * totalPage : 1
     * totalRows : 0
     */

    private int totalPage;
    private List<ItemsBean> items;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * award : 5000
         * deduct : 3000
         * endDate : 2018-10-30
         * headImg : {"headImg":"http://p.qpic.cn/wwhead/duc2TvpEgSSWiaVLaJnssaa8jHa1vcSnWfPPuEuaJBCkW8Nmdv5LBMHJ7aoIV1TrBm1mBa9tyNQ0/0"}
         * manNum : 0
         * missionDate : 1540742400000
         * missionId : 380
         * missionName : 好享记小程序开发
         * publisher : 莫景绘
         * publisherId : 91
         * startDate : 2018-07-30
         * status : 1
         * taskStatus : 0
         */

        private int award;
        private int deduct;
        private String endDate;
        private HeadImgBean headImg;
        private int missionId;
        private String missionName;
        private String publisher;

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }

        public int getDeduct() {
            return deduct;
        }

        public void setDeduct(int deduct) {
            this.deduct = deduct;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public HeadImgBean getHeadImg() {
            return headImg;
        }

        public void setHeadImg(HeadImgBean headImg) {
            this.headImg = headImg;
        }

        public int getMissionId() {
            return missionId;
        }

        public void setMissionId(int missionId) {
            this.missionId = missionId;
        }

        public String getMissionName() {
            return missionName;
        }

        public void setMissionName(String missionName) {
            this.missionName = missionName;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public static class HeadImgBean {
            /**
             * headImg : http://p.qpic.cn/wwhead/duc2TvpEgSSWiaVLaJnssaa8jHa1vcSnWfPPuEuaJBCkW8Nmdv5LBMHJ7aoIV1TrBm1mBa9tyNQ0/0
             */

            private String headImg;

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }
        }
    }
}

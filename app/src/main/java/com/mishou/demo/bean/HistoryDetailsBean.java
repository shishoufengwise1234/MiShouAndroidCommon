package com.mishou.demo.bean;

import java.util.List;

/**
 * Created by ${shishoufeng} on 17/11/18.
 * email:shishoufeng1227@126.com
 *
 * 详情数据
 */

public class HistoryDetailsBean {


    /**
     * e_id : 12873
     * title : 北宋史学家司马光诞辰
     * content :     在997年前的今天，1019年11月17日 (农历十月十八)，北宋史学家司马光诞辰。
     *     司马光是北宋陕州夏县涑水乡（今山西夏县司马营村）人，生于公元1019年11月17日（距今997年），世称涑水先生，是我国古代著名的史学家。他一生著作甚多，有20种500余卷，其中他领导编撰的《资治通鉴》一书，是中国封建社会中继司马迁《史记》以后最优秀的一部通史巨著。从它问世以来，一向为历史学者所推崇，对我国以后的史学发展起过巨大的影响。
     *     司马光从小勤奋好学，爱读史书，7岁时听老师讲《春秋左氏传》就很感兴趣，回家后能头头是道地讲给家里人听，这部历史名著对他影响很大，他能写出《资治通鉴》，是和这部书对他的影响分不开的。司马光从小聪明伶俐，有一次，他和一群小伙伴捉迷藏，一个孩子不小心掉进水缸里，其他孩子都吓跑了，只有司马光急中生智，搬起一块大石头，砸破水缸，水流出来后，那个孩子得救了。司马光的机智勇敢一直被后世传为佳话。
     *     司马光20岁那年考中了进士，他继续刻苦学习，一有时间就钻研历史。他发现自古以来历史著作虽然繁多，但是缺少一部比较系统完整的通史，于是他决心自己动手编。最初司马光用了两年时间，写成一部从战国到秦末的史书，共有8卷，名叫《通志》，宋英宗看了以后非常满意，立即下令设置书局，作为编书机构，要司马光继续写下去。宋英宗允许司马光自己挑选编写人员，并准许他借阅官府藏书。司马光成立书局时，邀请了当时著名的史学家刘恕、范祖禹等做助手，共同编写这部通史。
     *     宋神宗做了皇帝以后，也认为《通志》这部书不但可以帮助了解历代王朝的治乱兴衰，而且书中记载的历史好像一面镜子，可以常常对照借鉴，因此，他把书名改为《资治通鉴》。“资治”是帮助的意思，“通”是从古到今，“鉴”是指镜子，含有警戒和教训的意思。后来人们又把《资治通鉴》简称为《通鉴》。这部书从宋英宗治平二年（公元1065年）开始编撰，到神宗元丰七年（公元1084年）成书，历时19年。为了编写这部书，司马光付出了巨大的劳动，光是初稿就堆了两间屋子。司马光虽然出身官僚地主家庭，历任开封府推官、并州通判、龙图阁直学士、翰林院学士、御史中丞、尚书左仆射兼门下侍郎等职，政治上十分保守顽固，但在编《资治通鉴》过程中，却极为严谨负责，一丝不苟。每天很早起床工作，直到深夜就寝，他怕自己睡过了头，特地做了一个容易滚动的圆木枕头，只要一翻身枕头就会滚掉，他也就惊醒了，他把这个枕头称为“警枕”。
     *     《资治通鉴》按年代顺序排列实史，上起周威烈王23年（公元前402年），下至后周世宗显德六年（公元959年），记载了从战国到五代1361年间的历史，全书共294卷，另附目录及考异各30卷，共300多万字，是一部很有参考价值的历史巨著，至今仍受到广大读者的喜爱。司马光为《资治通鉴》耗尽毕生精力，书成后两年，于1086年逝世，终年67岁。
     * <p>
     * <p>
     * picNo : 2
     * picUrl : [{"pic_title":"司马光","id":1,"url":"http://images.juheapi.com/history/12873_1.jpg"},{"pic_title":"司马光 资治通鉴存一卷","id":2,"url":"http://images.juheapi.com/history/12873_2.jpg"}]
     */

    private String e_id;
    private String title;
    private String content;
    private String picNo;
    private List<PicUrlBean> picUrl;

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicNo() {
        return picNo;
    }

    public void setPicNo(String picNo) {
        this.picNo = picNo;
    }

    public List<PicUrlBean> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<PicUrlBean> picUrl) {
        this.picUrl = picUrl;
    }

    public static class PicUrlBean {
        /**
         * pic_title : 司马光
         * id : 1
         * url : http://images.juheapi.com/history/12873_1.jpg
         */

        private String pic_title;
        private int id;
        private String url;

        public String getPic_title() {
            return pic_title;
        }

        public void setPic_title(String pic_title) {
            this.pic_title = pic_title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}

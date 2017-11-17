package com.mishou.demo.zhihu.model;

import java.util.List;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public class ZhihuData {


    /**
     * date : 20171117
     * stories : [{"images":["https://pic3.zhimg.com/v2-8adcdb05e0f801fe356c4ee7557b1f42.jpg"],"type":0,"id":9657160,"ga_prefix":"111713","title":"- 你竟然总能抢到那种篮板\r\n- NBA 最看重这个，学着点"},{"images":["https://pic1.zhimg.com/v2-a479401c2ef2ec99711f3994f4b3efe8.jpg"],"type":0,"id":9657116,"ga_prefix":"111712","title":"大误 · 一直朝下面挖会怎样？"},{"title":"黑化与重生，新的 Taylor Swift 诞生了，但旧的她还在","ga_prefix":"111711","images":["https://pic1.zhimg.com/v2-713f0600cb15204b4dc5949f117e1448.jpg"],"multipic":true,"type":0,"id":9657264},{"images":["https://pic4.zhimg.com/v2-805330e03beb229fdb93401b51a94d2f.jpg"],"type":0,"id":9657258,"ga_prefix":"111710","title":"达 · 芬奇作品拍了 30 亿创下纪录，尽管没人敢说一定是他画的"},{"images":["https://pic1.zhimg.com/v2-a2338e17b9279c2cdd760e27ce3073bc.jpg"],"type":0,"id":9656336,"ga_prefix":"111709","title":"对不起，我们活着，并不是为了达到别人的期待"},{"images":["https://pic4.zhimg.com/v2-bc82172657c0f0b7e1c2e86b148e78d7.jpg"],"type":0,"id":9657237,"ga_prefix":"111708","title":"我是快递小哥，曾经为了应对「双十一」，我们提前 1 个月观测风云"},{"images":["https://pic4.zhimg.com/v2-4770c5831637a389f86e9ff38aa47aab.jpg"],"type":0,"id":9657164,"ga_prefix":"111707","title":"汽车的发明史，就是一个拼死拼活修 bug 的奋斗史"},{"images":["https://pic3.zhimg.com/v2-5245d6c3301cdfa40547ba18edfa95fe.jpg"],"type":0,"id":9657223,"ga_prefix":"111707","title":"关于「窦唯为何给手游献唱」，我们找到了另一个答案"},{"images":["https://pic2.zhimg.com/v2-762ce242d57774f4af11b1537c82c1e9.jpg"],"type":0,"id":9656857,"ga_prefix":"111707","title":"我真怕长大以后，自己的婚姻变成我爸妈那样"},{"images":["https://pic2.zhimg.com/v2-ead6041710fca84135819b90c87afe95.jpg"],"type":0,"id":9656994,"ga_prefix":"111706","title":"瞎扯 · 如何正确地吐槽"},{"images":["https://pic4.zhimg.com/v2-75342b25df3f48629c2f73c369605ac7.jpg"],"type":0,"id":9657256,"ga_prefix":"111706","title":"这里是广告 · 都追求性价比，教你三步选出适合的闲置数码"}]
     * top_stories : [{"image":"https://pic3.zhimg.com/v2-6d7d93d6a0d8adf0afc1879c8513c4ae.jpg","type":0,"id":9657264,"ga_prefix":"111711","title":"黑化与重生，新的 Taylor Swift 诞生了，但旧的她还在"},{"image":"https://pic3.zhimg.com/v2-5930137ca9bf49dbdff58c8bd5df3a92.jpg","type":0,"id":9657258,"ga_prefix":"111710","title":"达 · 芬奇作品拍了 30 亿创下纪录，尽管没人敢说一定是他画的"},{"image":"https://pic3.zhimg.com/v2-7a64f0194c88ba7f584516c5eea7cfaa.jpg","type":0,"id":9657223,"ga_prefix":"111707","title":"关于「窦唯为何给手游献唱」，我们找到了另一个答案"},{"image":"https://pic2.zhimg.com/v2-b97b1f3976f2c8b1f3316b5e076f76f9.jpg","type":0,"id":9656857,"ga_prefix":"111707","title":"我真怕长大以后，自己的婚姻变成我爸妈那样"},{"image":"https://pic2.zhimg.com/v2-ef9af0e2589f3124dc9813cf681bc991.jpg","type":0,"id":9657146,"ga_prefix":"111614","title":"最好骑的小蓝单车死了？没被摩拜们干掉，却倒在了找钱路上"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * images : ["https://pic3.zhimg.com/v2-8adcdb05e0f801fe356c4ee7557b1f42.jpg"]
         * type : 0
         * id : 9657160
         * ga_prefix : 111713
         * title : - 你竟然总能抢到那种篮板
         - NBA 最看重这个，学着点
         * multipic : true
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private boolean multipic;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image : https://pic3.zhimg.com/v2-6d7d93d6a0d8adf0afc1879c8513c4ae.jpg
         * type : 0
         * id : 9657264
         * ga_prefix : 111711
         * title : 黑化与重生，新的 Taylor Swift 诞生了，但旧的她还在
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

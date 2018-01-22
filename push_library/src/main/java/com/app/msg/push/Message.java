package com.app.msg.push;


/**
 * 将消息整合成Message model
 * Created by ssf
 */

public final class Message {
    private int notifyID;  //这个字段用于通知的消息类型，在透传中都是默认0
    private String messageID;
    private String title;
    private String message;
    private String extra;
    private int  target;

    //--------自定义参数-------

    //消息类型
    private String type;

    //add
    //透传消息 自定义数据 用于xmpp 离线消息
    private String transMsg;





    public int getNotifyID() {
        return notifyID;
    }

    public void setNotifyID(int notifyID) {
        this.notifyID = notifyID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(@Target int target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getTransMsg() {
        return transMsg;
    }

    public void setTransMsg(String transMsg) {
        this.transMsg = transMsg;
    }


    @Override
    public String toString() {
        return "Message{" +
                "notifyID=" + notifyID +
                ", messageID='" + messageID + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", extra='" + extra + '\'' +
                ", target=" + target +
                ", type='" + type + '\'' +
                ", transMsg='" + transMsg + '\'' +
                '}';
    }
}

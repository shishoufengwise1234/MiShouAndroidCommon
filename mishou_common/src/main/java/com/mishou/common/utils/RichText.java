package com.mishou.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2017/3/3.
 * 富文本工具类
 */

public class RichText {
    /**
     * 更改出传入的富文本，让其宽高自动适配页面
     * @param html 富文本字符串
     * @return
     */
    public static String AutoAdaptationSize(String html) {
        Document doc= Jsoup.parse(html);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
        }
        return doc.toString();

    }
}

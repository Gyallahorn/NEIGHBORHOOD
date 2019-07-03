package com.esphereal.bair.neighborhood.jsoup;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class JsoupSingleton {
    private static final String TAG = "JsoupSingleton";

    private static volatile JsoupSingleton instance;

    public static JsoupSingleton getInstance() {
        JsoupSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (JsoupSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new JsoupSingleton();
                }
            }
        }
        return localInstance;
    }


    private List<JsoupOnDataBackListener> listeners = new ArrayList<JsoupOnDataBackListener>();

    public void addListener(JsoupOnDataBackListener toAdd) {
        listeners.add(toAdd);
    }

    List<NewsItem> newsData;
    private Thread parsingThread;


    public static class NewsItem {
        public final String id;
        public final String text;
        public final String href;
        public final String imageSrc;
        public final String date;


        public NewsItem(String id, String text, String href, String imageSrc, String date) {
            this.id = id;
            this.text = text;
            this.href = href;
            this.imageSrc = imageSrc;
            this.date = date;
        }

        @Override
        public String toString() {
            return text;
        }
    }


    public void GetData() {

        if (parsingThread == null && newsData == null) {
            getData();
        } else
            sendToListeners();
    }

    private void sendToListeners() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                for (JsoupOnDataBackListener hl : listeners)
                    hl.onDataBack(newsData);
            }
        });

    }

    private void getData() {


        parsingThread = new Thread(new Runnable() {
            @Override
            public void run() {

                Document doc, doc2;
                List<NewsItem> data = new ArrayList<NewsItem>();
                List<NewsItem> data2 = new ArrayList<NewsItem>();

                try {
                    int m = 1, mCount = 1, n = 1, b = 1, v = 1, z = 1, w = 1, e = 1;
                    doc = Jsoup.connect("https://minsport.sakha.gov.ru/news").get();
                    doc2 = Jsoup.connect("http://sportyakutia.ru/novosti").get();
                    //Log.d("DEBUG",doc.data());
                    Elements elements = doc.select("div.item");
                    int i = 0;
                    int g = 0;
                    NewsItem newsItem = null;
                    NewsItem newsItem2 = null;
                    outer:
                    for (Element element : elements) {
                        String src = element.select("img").first().absUrl("src");
                        String href = element.select("a[href]").first().absUrl("href");
                        String title = element.select("img").first().attr("title");
                        String date = element.select("div.date").text();
                        int l = 109;
                        if (title.length() > l)
                            title = title.substring(0, l) + "...";
                        newsItem = new NewsItem(String.valueOf(i), title, href, src, date);
                        data.add(newsItem);
                        if (mCount <= 3) {
                            Elements parents = doc2.select("div.equal-height.equal-height-child.row-0.row");
                            for (Element parent : parents) {

                                for (Element child : parent.getElementsByClass("item col col-sm-6 column-" + z + " ")) {
                                    String src2 = child.select("img").first().absUrl("src");
                                    String href2 = child.select("a[href]").first().absUrl("href");
                                    String title2 = child.select("h2.article-title").first().text();
                                    String date2 = child.select("dd.published.hasTooltip").text();
                                    if (title2.length() > l)
                                        title2 = title2.substring(0, l) + "...";
                                    newsItem2 = new NewsItem(String.valueOf(i), title2, href2, src2, date2);
                                    data.add(newsItem2);
                                    z++;
                                    mCount++;
                                    continue outer;
                                }
                            }
                        }

                        if (mCount <= 6) {
                            Elements parents1 = doc2.select("div.equal-height.equal-height-child.row-1.row");
                            for (Element parent1 : parents1) {

                                for (Element child : parent1.getElementsByClass("item col col-sm-6 column-" + n + " ")) {
                                    String src2 = child.select("img").first().absUrl("src");
                                    String href2 = child.select("a[href]").first().absUrl("href");
                                    String title2 = child.select("h2.article-title").first().text();
                                    String date2 = child.select("dd.published.hasTooltip").text();
                                    if (title2.length() > l)
                                        title2 = title2.substring(0, l) + "...";
                                    newsItem2 = new NewsItem(String.valueOf(i), title2, href2, src2, date2);
                                    data.add(newsItem2);
                                    n++;
                                    mCount++;
                                    continue outer;
                                }

                            }
                        }
                        if (mCount <= 19) {
                            Elements parents1 = doc2.select("div.equal-height.equal-height-child.row-2.row");
                            for (Element parent1 : parents1) {

                                for (Element child : parent1.getElementsByClass("item col col-sm-6 column-" + b + " ")) {
                                    String src2 = child.select("img").first().absUrl("src");
                                    String href2 = child.select("a[href]").first().absUrl("href");
                                    String title2 = child.select("h2.article-title").first().text();
                                    String date2 = child.select("dd.published.hasTooltip").text();
                                    if (title2.length() > l)
                                        title2 = title2.substring(0, l) + "...";
                                    newsItem2 = new NewsItem(String.valueOf(i), title2, href2, src2, date2);
                                    data.add(newsItem2);
                                    b++;
                                    mCount++;
                                    continue outer;
                                }

                            }
                        }
                        if (mCount <= 12) {
                            Elements parents1 = doc2.select("div.equal-height.equal-height-child.row-3.row");
                            for (Element parent1 : parents1) {

                                for (Element child : parent1.getElementsByClass("item col col-sm-6 column-" + v + " ")) {
                                    String src2 = child.select("img").first().absUrl("src");
                                    String href2 = child.select("a[href]").first().absUrl("href");
                                    String title2 = child.select("h2.article-title").first().text();
                                    String date2 = child.select("dd.published.hasTooltip").text();
                                    if (title2.length() > l)
                                        title2 = title2.substring(0, l) + "...";
                                    newsItem2 = new NewsItem(String.valueOf(i), title2, href2, src2, date2);
                                    data.add(newsItem2);
                                    v++;
                                    mCount++;
                                    continue outer;
                                }

                            }
                        }
                        if (mCount <= 15) {
                            Elements parents1 = doc2.select("div.equal-height.equal-height-child.row-4.row");
                            for (Element parent1 : parents1) {

                                for (Element child : parent1.getElementsByClass("item col col-sm-6 column-" + w + " ")) {
                                    String src2 = child.select("img").first().absUrl("src");
                                    String href2 = child.select("a[href]").first().absUrl("href");
                                    String title2 = child.select("h2.article-title").first().text();
                                    String date2 = child.select("dd.published.hasTooltip").text();
                                    if (title2.length() > l)
                                        title2 = title2.substring(0, l) + "...";
                                    newsItem2 = new NewsItem(String.valueOf(i), title2, href2, src2, date2);
                                    data.add(newsItem2);
                                    w++;
                                    mCount++;
                                    continue outer;
                                }


                            }
                        }
                        if (mCount <= 18) {
                            Elements parents1 = doc2.select("div.equal-height.equal-height-child.row-5.row");
                            for (Element parent1 : parents1) {

                                for (Element child : parent1.getElementsByClass("item col col-sm-6 column-" + e + " ")) {
                                    String src2 = child.select("img").first().absUrl("src");
                                    String href2 = child.select("a[href]").first().absUrl("href");
                                    String title2 = child.select("h2.article-title").first().text();
                                    String date2 = child.select("dd.published.hasTooltip").text();
                                    if (title2.length() > l)
                                        title2 = title2.substring(0, l) + "...";
                                    newsItem2 = new NewsItem(String.valueOf(i), title2, href2, src2, date2);
                                    data.add(newsItem2);
                                    e++;
                                    mCount++;
                                    continue outer;
                                }

                            }
                        }

                    }


                } catch (Exception e) {
                    Log.d(TAG, "jsoup parsing error");
                }

                newsData = data;

                sendToListeners();


            }
        });
        parsingThread.start();

    }

}




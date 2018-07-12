package cn.hzbank;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.Charset;

public class govSpider {
    public static void main(String[] args) throws IOException {
        String url = "http://sousuo.gov.cn/s.htm?q=世界杯";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        String html = EntityUtils.toString(entity, Charset.forName("utf-8"));
        //解析HTML
        Document document = Jsoup.parse(html);
        //System.out.println(document);
        Elements elements = document.select("li.res-list h3.res-title");
        //System.out.println(elements);
            for(Element element : elements){
                String indexUrl = element.select("a").attr("href");
                if(indexUrl.startsWith("http")){
                   // System.out.println(indexUrl);
                    HttpGet httpGet1 = new HttpGet(indexUrl);
                    CloseableHttpClient httpClient1 = HttpClients.createDefault();
                    CloseableHttpResponse httpResponse1 = httpClient1.execute(httpGet1);
                    HttpEntity entity1 = httpResponse1.getEntity();
                    String html1 = EntityUtils.toString(entity1, Charset.forName("UTF-8"));
                    Document document1 = Jsoup.parse(html1);
                    Elements elements1 = document1.select("h1");
                    String title = elements1.get(0).text();
                    //System.out.println(title);
                    Elements elements2 = document1.select("div.pages-date");
                    String date = elements2.text().substring(0,16);
                    //System.out.println(date);
                    Elements elements3 = document1.select("div.pages_content");
                    String article = elements3.text();
                    //System.out.println(article);
                    String result = title.concat("  " + date + "  " + article);
                    System.out.println(result);


                }
        }
    }
}

package movist;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("https://nanegative.ru/internet-magaziny").get();
        var elem = document.selectXpath("//a[starts-with(@href,'/internet-magaziny?page=')]");

        HashMap<String, String> reviewsList = new HashMap<>();

        for (int i = 1; i <= elem.size() - 2; i++) {
            document = Jsoup.connect("https://nanegative.ru/internet-magaziny?page=" + i).get();
            var reviews = document.selectXpath("//a[contains(text(),'Отзывы')]");
            for (int j = 0; j < reviews.size(); j++) {
                Element el = reviews.get(j);
                String str = el.text().replace("Отзывы о ", "");
                reviewsList.put(str, el.attr("href"));
            }
        }
        for (String key : reviewsList.keySet()) {
            String value = reviewsList.get(key);
            System.out.println(key);
        }

        System.out.println("C какого сайта хотите получить отзывы?");
        Scanner sc = new Scanner(System.in);
        String website = sc.nextLine();
        /*String website = "Бусики-Колечки";*/
        String value = reviewsList.get(website);
        document = Jsoup.connect("https://nanegative.ru" + value).get();
        var pages = document.selectXpath("//a[starts-with(@href,'" + value + "?page=')]");//Бусики-Колечки
        if (pages.size() == 0) {
            for (int i = 0; i < 1; i++) {
                document = Jsoup.connect("https://nanegative.ru" + value + "?page=" + i).get();
                var reviews = document.selectXpath("//table[contains(@itemprop,'description')]");
                if (reviews.size() == 0) {
                    System.out.println("Нет отзывов на сайте");
                } else {
                    for (int j = 0; j < reviews.size(); j++) {
                        Element otziv = reviews.get(j);
                        String str = otziv.text();
                        System.out.println(str);
                    }
                }
            }
        } else {
            for (int i = 0; i <= pages.size() - 2; i++) {
                document = Jsoup.connect("https://nanegative.ru" + value + "?page=" + i).get();
                var reviews = document.selectXpath("//table[contains(@itemprop,'description')]");
                Feedback feedback = new Feedback();
                for (int j = 0; j < reviews.size(); j++) {
                    Element otziv = reviews.get(j);
                    String str = otziv.text();
                    feedback.setPluses(str.substring(str.indexOf("Плюсы:"), str.indexOf("Минусы:")));
                    feedback.setMinuses(str.substring(str.indexOf("Минусы:"), str.indexOf("Отзыв:")));
                    feedback.setComment(str.substring(str.indexOf("Отзыв:")));
                    System.out.println(feedback.toString());
                }
            }
        }
    }
}
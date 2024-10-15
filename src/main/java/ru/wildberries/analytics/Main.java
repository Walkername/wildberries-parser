package ru.wildberries.analytics;

public class Main {

    public static void main(String[] args) {
        ParserWB parserWB = new ParserWB();

        String url = "https://catalog.wb.ru/brands/v2/catalog" +
                "?ab_testing=false" +
                "&appType=1" +
                "&brand=51612560" +
                "&curr=rub" +
                "&dest=-1257786" +
                "&sort=popular" +
                "&spp=30";

        String response = parserWB.sendRequestTo(url);
        System.out.println(response);
    }

}

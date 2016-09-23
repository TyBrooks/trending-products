package com.viglink.trendingproducts.utils;

import com.google.common.collect.Lists;
import com.viglink.trendingproducts.model.Product;
import com.viglink.trendingproducts.model.Trend;
import com.viglink.trendingproducts.type.TrendType;

import java.math.BigDecimal;
import java.util.Random;


public class RandomUtils {

    private Random randomGenerator;

    public RandomUtils(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public RandomUtils() {
        this(new Random());
    }

    public Product generateRandomProduct() {
        long id = randomNumber(10);
        String imageUrl = "http://www.viglink.com/images/" + randomString(5); // TODO set up an actual image somewhere
        String title = "Amazing product # " + Integer.toString(randomNumber(5));
        BigDecimal price = randomPrice(randomGenerator.nextInt(4));
        String merchantName = "Merchant # " + randomNumber(4);
        String targetUrl = "http://www.viglink.com/merchants/" + randomNumber(3);
        String category = (randomString(2) + ( randomGenerator.nextBoolean() ? ">" + randomString(2) : "" ) + ( randomGenerator.nextBoolean() ? ">" + randomString(2) : "" )).toUpperCase();

        Trend trend = generateRandomTrend(true);

        return new Product(id, imageUrl, title, price, merchantName, targetUrl, category, Lists.newArrayList(trend));
    }

    private Trend generateRandomTrend(boolean isClick) {
        float weight = randomNumber(4);
        TrendType type = isClick ? TrendType.CLICK : TrendType.REVENUE;

        return new Trend(weight, type);
    }


    public BigDecimal randomPrice(int numDigits) {
        Double val = Double.valueOf(Integer.toString(randomNumber(3)) + "." + Integer.toString(randomNumber(2)));

        return BigDecimal.valueOf(val).setScale(2, BigDecimal.ROUND_CEILING);
    }

    public String randomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++)
            builder.append(characters.charAt(randomGenerator.nextInt(characters.length())));

        return builder.toString();
    }

    public int randomNumber(int length) {
        int number = 0;
        for (int i = 0; i < length; i++)
            number += Math.pow(10, i) * randomGenerator.nextInt(9) + 1;

        return number;
    }

}

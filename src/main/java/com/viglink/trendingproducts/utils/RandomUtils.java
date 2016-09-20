package com.viglink.trendingproducts.utils;

import com.viglink.trendingproducts.model.Product;
import com.viglink.trendingproducts.model.Trend;
import com.viglink.trendingproducts.type.TrendType;
import com.viglink.trendingproducts.model.TrendingProduct;

import java.util.Random;

/**
 * Created by tybrooks on 9/19/16.
 */
public class RandomUtils {

    private Random randomGenerator;

    public RandomUtils(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public RandomUtils() {
        this(new Random());
    }

    public TrendingProduct generateTrendingProduct(boolean isClick) {
        return new TrendingProduct(generateRandomTrend(isClick), generateRandomProduct());
    }

    public Trend generateRandomTrend(boolean isClick) {
        float weight = randomNumber(4);
        TrendType type = isClick ? TrendType.CLICK : TrendType.REVENUE;

        return new Trend(weight, type);
    }

    public Product generateRandomProduct() {
        long id = randomNumber(10);
        String imageUrl = "http://www.viglink.com/images/" + randomString(5); // TODO set up an actual image somewhere
        String title = "Amazing product # " + Integer.toString(randomNumber(5));
        Float price = Float.parseFloat(Integer.toString(randomNumber(3)) + "." + Integer.toString(randomNumber(2)));
        String merchantName = "Merchant # " + randomNumber(4);
        String targetUrl = "http://www.viglink.com/merchants/" + randomNumber(3);
        String category = randomString(2) + ( randomGenerator.nextBoolean() ? ">" + randomString(2) : "" ) + ( randomGenerator.nextBoolean() ? ">" + randomString(2) : "" );

        return new Product(id, imageUrl, title, price, merchantName, targetUrl, category);
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

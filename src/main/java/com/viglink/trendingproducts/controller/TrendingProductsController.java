package com.viglink.trendingproducts.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@Controller
public class TrendingProductsController {

    @RequestMapping("/trending-products")
    @ResponseBody
    String showTrending() throws IOException {
        InputStream is = getClass().getResourceAsStream("/sample.json");
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A"); // hack from http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
        return s.hasNext() ? s.next() : "";
    }

}
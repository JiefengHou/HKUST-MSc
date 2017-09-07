package com.s_trade.s_trade;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by JiefengHou on 2016/12/12.
 */
public class ProductTest {
    @Test
    public void getTitle() throws Exception {
        Product product = new Product();
        product.setTitle("title");
        assertEquals("title Error", "title", product.getTitle());
    }

    @Test
    public void setTitle() throws Exception {
        Product product = new Product();
        product.setTitle("title1");
        assertEquals("title Error", "title1", product.getTitle());
    }

}
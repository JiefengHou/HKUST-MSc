package com.s_trade.s_trade;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by JiefengHou on 2016/12/12.
 */
public class ItemTest {
    @Test
    public void setItemId() throws Exception {
        Item item = new Item();
        item.setItemId(100);
        assertEquals("Item ID Error", 100, item.getItemId());
    }

    @Test
    public void getItemId() throws Exception {
        Item item = new Item();
        item.setItemId(1000);
        assertEquals("Item ID Error", 1000, item.getItemId());
    }

    @Test
    public void setName() throws Exception {
        Item item = new Item();
        item.setName("name");
        assertEquals("Item name Error", "name", item.getName());
    }

    @Test
    public void getName() throws Exception {
        Item item = new Item();
        item.setName("Sam");
        assertEquals("Item name Error", "Sam", item.getName());
    }

    @Test
    public void setPrice() throws Exception {
        Item item = new Item();
        item.setPrice("100");
        assertEquals("Item price Error", "100", item.getPrice());
    }

    @Test
    public void getPrice() throws Exception {
        Item item = new Item();
        item.setPrice("1000");
        assertEquals("Item price Error", "1000", item.getPrice());
    }

    @Test
    public void setisenabled() throws Exception {
        Item item = new Item();
        item.setisenabled(1);
        assertEquals("Item enable Error", 1, item.getisenabled());
    }

    @Test
    public void getisenabled() throws Exception {
        Item item = new Item();
        item.setisenabled(1);
        assertEquals("Item enable Error", 1, item.getisenabled());
    }
}
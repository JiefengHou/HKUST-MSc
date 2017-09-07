package com.s_trade.s_trade;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by JiefengHou on 2016/12/12.
 */
public class MainActivityTest {
    @Test
    public void logIn() throws Exception {
        MainActivity mainActivity = new MainActivity();
        assertEquals("Error", 1, mainActivity.logInState);
        mainActivity.logInState = 0;
        assertEquals("Error", 0, mainActivity.logInState);
    }

    @Test
    public void logOut() throws Exception {
        MainActivity mainActivity = new MainActivity();
        mainActivity.logInState = 0;
        assertEquals("Error", 0, mainActivity.logInState);
        mainActivity.logInState = 1;
        assertEquals("Error", 1, mainActivity.logInState);
    }

}
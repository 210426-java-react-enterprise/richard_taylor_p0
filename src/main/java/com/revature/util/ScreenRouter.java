package com.revature.util;

import com.revature.screens.Screen;

public class ScreenRouter {

    private List<Screen> screens = new PoorArrayList<>();

    public void navigate(String route) {
        for(Screen screen: screens) {
            if(screen.getRoute().equals(route)) {
                screen.render();
            }
        }
    }

    public ScreenRouter addScreen(Screen screen) {
        screens.add(screen);
        return this;
    }
}

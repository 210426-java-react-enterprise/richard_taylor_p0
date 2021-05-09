package com.revature.util;

import com.revature.screens.Screen;

/**
 * ScreenRouter
 * <p>
 * Stores a list of screens, and provides a way of navigating through them.
 */
public class ScreenRouter {

    private List<Screen> screens = new PoorArrayList<>(); //make my own scuffed data structure

    /**
     * Takes in a route for the screen, and calls the render method of the specified screen.
     *
     * @param route
     */
    public void navigate(String route) {
        for (Screen screen : screens) {
            if (screen.getRoute().equals(route)) {
                screen.render();
            }
        }
    }

    /**
     * Adds a screen object to the list of screens. Returns an instance of itself to allow for method chaining.
     *
     * @param screen The screen to be added to the list
     * @return An instance of this class with the screen added
     */
    public ScreenRouter addScreen(Screen screen) {
        screens.add(screen);
        return this;
    }
}

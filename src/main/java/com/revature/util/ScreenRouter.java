package com.revature.util;

import com.revature.Exceptions.InvalidRouteException;
import com.revature.screens.Screen;

/**
 * ScreenRouter
 * <p>
 * Stores a list of screens, and provides a way of navigating through them.
 */
public class ScreenRouter {

    private List<Screen> screens = new PoorArrayList<>();
    private Screen currentScreen;

    /**
     * Takes in a route for the screen, and sets the current screen to the route specified.
     *
     * @param route the route to be passed in
     */
    public void navigate(String route) {
        currentScreen = screens.stream()
                .filter(screen -> screen.getRoute().equals(route))
                .findFirst()
                .orElseThrow(InvalidRouteException::new);
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

    public Screen getCurrentScreen() {
        return currentScreen;
    }
}

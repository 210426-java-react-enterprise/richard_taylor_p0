package com.revature.util;

import com.revature.screens.Screen;

public class ScreenRouter {

    private List<Screen> screens = new PoorArrayList<>(); //make my own scuffed data structure

    public void navigate(String route) {
        for(Screen screen: screens) { // iterate through the screen list
            if(screen.getRoute().equals(route)) { // check each screen to see if it matches the route provided
                screen.render(); // call the screen's render method
            }
        }
    }

    /*
        returns an instance of this class, this allows for this method to be called over and over again
        allows for statements like router.addScreen(someScreen).addScreen(someOtherScreen)...
     */
    public ScreenRouter addScreen(Screen screen) {
        screens.add(screen); //adds a screen to the list
        return this; //returns the instance with the new screen added
    }
}

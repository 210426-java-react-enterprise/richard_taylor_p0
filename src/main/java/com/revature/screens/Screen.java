package com.revature.screens;

/**
 * Screen
 * <p>
 * The abstract parent class of all of the screens. Has a render method that must be overridden.
 */
public abstract class Screen {

    protected String name;
    protected String route;

    public Screen(String name, String route) {
        this.name = name;
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }

    /**
     * The abstract render method, standard implementation is to show dialogue to the user.
     */
    public abstract void render();

}
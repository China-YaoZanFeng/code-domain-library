package org.domain.code.layout.drawer;

/**
 * Interface used to communicate from the v21-specific code for configuring a DrawerLayout
 * to the DrawerLayout itself.
 */
public interface DrawerLayoutImpl {
    void setChildInsets(Object insets, boolean drawStatusBar);
}


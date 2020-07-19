package org.lwjge.renderer;

import org.lwjge.Scene;
import org.lwjge.Window;

public interface Renderer {

    /**
     * Initializes the renderer
     */
    void init();

    /**
     * Renders the entire scene (Skybox, Lights, GameItems)
     * @param scene Scene instance
     * @param window Main window instance
     */
    void render(Scene scene, Window window);

    /**
     * Clears the screen
     */
    void clear();

}

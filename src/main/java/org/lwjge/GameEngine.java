package org.lwjge;

import org.lwjge.graphic.MouseInput;
import it.cubicworldsimulator.game.gui.Settings;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class GameEngine extends Thread {

    public static final int TARGET_FPS = 75; //frames per second

    public static final int TARGET_UPS = 30; //updates per second

    private final Window window;

    private  final MouseInput mouseInput;

    private final Timer timer;

    private final GameLogic gameLogic;

    /*public GameEngine(String windowTitle, GameLogic gameLogic) throws Exception {
        this(windowTitle, 0, 0, vSync, gameLogic, debug);
    }*/

    //TODO dove bisogna inserire il fullscreen????
    public GameEngine(String windowTitle,  GameLogic gameLogic, Settings mySettings) {
        Vector4f clearColor = new Vector4f(0.0f, 0.0f, 0.3f, 1.0f); //TODO Creare diversi costruttori in modo da passare il clearColor facoltativamente
        window = new Window(windowTitle, mySettings, clearColor);
        //mouseInput = new MouseInput(window);
        this.gameLogic = gameLogic;
        timer = new Timer();
        this.mouseInput = new MouseInput(this.window);
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception excp) {
            excp.printStackTrace();
        }finally {
            cleanUp();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        mouseInput.init(window);
        gameLogic.init(window);
        this.mouseInput.init(this.window);
    }

    protected void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if (!window.isvSync()) {
                sync();
            }
        }
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }finally {
                cleanUp();
            }
        }
    }

    protected void input() {
        mouseInput.input();
        gameLogic.input(window, mouseInput);
    }

    protected void update(float interval) {
        gameLogic.update(interval, mouseInput);
    }

    protected void render() {
        gameLogic.render(window);
        window.update();
    }

    protected void cleanUp(){
        gameLogic.cleanUp();
        glfwHideWindow(window.getWindowId());
        System.exit(0);
    }
}
package org.lwjge.hud;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

import org.lwjge.Window;
import org.lwjge.loader.LoaderUtility;
import it.cubicworldsimulator.game.utility.Pair;
import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.system.MemoryUtil.NULL;

public class UpperHud implements GenericHud {
    private static final String FONT_NAME = "BOLD";
    private long vg;
    private ByteBuffer fontBuffer;
    private DoubleBuffer posx;
    private DoubleBuffer posy;
    private String hour;

    /**
     * Init the NVG library
     * @throws Exception
     */
    public void init() throws Exception {
        vg = nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES);
        if (vg == NULL) {
            throw new Exception("Could not init nanovg");
        }
        fontBuffer = LoaderUtility.ioResourceToByteBuffer("/fonts/OpenSans-Bold.ttf", 150 * 1024);
        int font = nvgCreateFontMem(vg, FONT_NAME, fontBuffer, 0);
        if (font == -1) {
            throw new Exception("Could not add font");
        }
        posx = MemoryUtil.memAllocDouble(1);
        posy = MemoryUtil.memAllocDouble(1);
    }

    @Override
    public void renderHud(Window window) {
        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);
        //Upper bar
        nvgBeginPath(vg);
        nvgRect(vg, 0, 0, window.getWidth(), 50);
        nvgFillColor(vg, fromRgbToNvg(0x0, 0x0, 0x66, 100));
        nvgFill(vg);
        glfwGetCursorPos(window.getWindowId(), posx, posy);
        createText("Time: " + getText(), FONT_NAME, 35f,
                new Pair<>(0, 0), fromRgbToNvg(0xe6, 0xea, 0xed, 255));
        createText("Commands: WASD - space - shift - F/T", FONT_NAME, 35f,
                new Pair<>(window.getWidth() / 2 + window.getWidth() / 12, 0), fromRgbToNvg(0xe6, 0xea, 0xed, 255));
        nvgEndFrame(vg);
        // Restore state
        window.restoreState();
    }

    @Override
    public void createText(String text, String fontName, float fontSize, Pair<Integer, Integer> position,
                           NVGColor color) {
        nvgFontSize(vg, fontSize);
        nvgFontFace(vg, fontName);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, color);
        nvgText(vg, position.getFirstValue(), position.getSecondValue(), text);
    }

    /**
     * Cleanup all NVG stuff
     */
    public void cleanup() {
        nvgDelete(vg);
        if (posx != null) {
            MemoryUtil.memFree(posx);
        }
        if (posy != null) {
            MemoryUtil.memFree(posy);
        }
    }

    /**
     * Set the hour on left corner of the screen
     * @param hour
     */
    public void setText(String hour) {
        this.hour = hour;
    }

    /**
     * @return the actual hour
     */
    private String getText() {
        return hour;
    }
}
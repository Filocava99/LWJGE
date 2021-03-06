package org.lwjge.graphic.texture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryStack;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class TextureFactoryImpl implements TextureFactory{
    private final static Logger logger = LogManager.getLogger(TextureFactoryImpl.class);
    private int height;
    private int width;
    private ByteBuffer byteBuffer;

    @Override
    public Texture createTexture(String filename) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            OutputStream outputStream = new FileOutputStream("temp.png");
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            assert inputStream != null;
            inputStream.transferTo(outputStream);
            inputStream.close();
            outputStream.close();
            this.byteBuffer = stbi_load("temp.png", w, h, channels, 4);
            if (this.byteBuffer == null) {
                throw new FileNotFoundException("Texture file [" + filename + "] not loaded. Reason: " + stbi_failure_reason());
            }
            new File("temp.png").delete();
            //Get width and height of image
            this.width = w.get();
            this.height = h.get();
            int textureID = this.generateTexture();
            this.generateMipMap();
            this.clean();
            return new TextureImpl(textureID, this.width, this.height);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    //For scaled textures
    private void generateMipMap() {
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    private int generateTexture() {
        int textureId = glGenTextures();
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureId);
        //Tell OpenGL how to unpack RGBA. 1 byte for pixel
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
         /*Args:
              1. Type of texture;
              2. Number of colour components in the texture;
              3. Colour components in texture;
              4. Texture width;
              5. Texture height;
              6. Texture border size;
              7. Format of the pixel data (RGBA);
              8. Each pixel is represented by an unsigned int;
              9. Data to load is stored in a ByteBuffer
         */
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height,
                0, GL_RGBA, GL_UNSIGNED_BYTE, this.byteBuffer);
        return textureId;
    }

    private void clean() {
        //Free ByteBuffer
        stbi_image_free(byteBuffer);
    }
}

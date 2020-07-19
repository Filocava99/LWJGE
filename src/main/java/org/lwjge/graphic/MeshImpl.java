package org.lwjge.graphic;

import org.lwjge.loader.OpenGLComponent;

import java.util.Set;

import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL15C.glDeleteBuffers;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glDeleteVertexArrays;

public class MeshImpl implements Mesh {
    private final OpenGLComponent.Vao myVao;
    private final Set<OpenGLComponent.Vbo> vboList;
    private final Set<OpenGLComponent.Vbo> textureVboList;
    private final int vertexCount;
    private final Material material;
    private final float boundingRadius;

    public MeshImpl(Material texture, float boundingRadius, int vertexCount, OpenGLComponent.Vao myVao,
                    Set<OpenGLComponent.Vbo> vboList, Set<OpenGLComponent.Vbo> textureVboList) {
        this.material = texture;
        this.boundingRadius = boundingRadius;
        this.vertexCount = vertexCount;
        this.myVao = myVao;
        this.vboList = vboList;
        this.textureVboList = textureVboList;
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public OpenGLComponent.Vao getVao() {
        return this.myVao;
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public Material getMeshMaterial() {
        return material;
    }

    @Override
    public Set<OpenGLComponent.Vbo> getVboList() {
        return vboList;
    }

    @Override
    public Set<OpenGLComponent.Vbo> getTextureVboList() {
        return textureVboList;
    }

    @Override
    public void cleanMesh() {
        glDisableVertexAttribArray(0);
        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        getVboList().forEach(myVbo -> glDeleteBuffers(myVbo.getId()));
        // Delete the texture VBO
        getTextureVboList().forEach(myVbo -> glDeleteBuffers(myVbo.getId()));
        // Delete the VAOs
        glBindVertexArray(0);
        glDeleteVertexArrays(getVao().getId());
    }
}
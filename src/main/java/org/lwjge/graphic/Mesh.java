package org.lwjge.graphic;

import org.lwjge.loader.OpenGLComponent;

import java.util.Set;

/**
 * It models a mesh (also called raw model)
 */
public interface Mesh {
    /**
     * @return teh vertex count of the mesh
     */
    int getVertexCount();

    /**
     * @return the main VAO
     */
    OpenGLComponent.Vao getVao();

    /**
     * @return the boundingRadius mesh value
     */
    float getBoundingRadius();

    /**
     * @return the mesh material
     */
    Material getMeshMaterial();

    /**
     * @return entire vbo list
     */
    Set<OpenGLComponent.Vbo> getVboList();

    /**
     * @return only texture vbo list
     */
    Set<OpenGLComponent.Vbo> getTextureVboList();

    /**
     * Clean mesh when need to clean memory
     */
    void cleanMesh();
}

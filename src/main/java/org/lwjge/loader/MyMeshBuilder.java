package org.lwjge.loader;

import org.lwjge.graphic.Material;
import org.lwjge.graphic.Mesh;
import org.lwjge.graphic.MeshImpl;

import java.util.*;

import org.lwjge.loader.OpenGLComponent.Vao;
import org.lwjge.loader.OpenGLComponent.Vbo;

public class MyMeshBuilder implements MeshBuilder {
    private final Vao myVao;
    private float boundingRadius;
    private Material texture;
    private int indicesLength;
    private final Set<Vbo> vboList = new HashSet<>();
    private final Set<Vbo> textureVboList = new HashSet<>();
    private final Set<Vbo> normalsVboList = new HashSet<>();

    public MyMeshBuilder() {
        myVao = LoaderUtility.createVao();
    }

    @Override
    public MeshBuilder addPositions(float[] positions) {
        Vbo positionVbo = LoaderUtility.createVbo();
        vboList.add(positionVbo);
        LoaderUtility.insertPositionIntoVbo(positionVbo, positions);
        return this;
    }

    @Override
    public MeshBuilder addIndices(int[] indices) {
        Vbo indexVbo = LoaderUtility.createVbo();
        vboList.add(indexVbo);
        LoaderUtility.insertIndicesIntoVbo(indexVbo, indices);
        indicesLength = indices.length;
        return this;
    }

    @Override
    public MeshBuilder addTextCoords(float[] textCoords) {
        Vbo textureVbo = LoaderUtility.createVbo();
        textureVboList.add(textureVbo);
        LoaderUtility.insertTextureIntoVbo(textureVbo, textCoords);
        return this;
    }

    @Override
    public MeshBuilder addNormals(float[] normals) {
        Vbo normalsVbo = LoaderUtility.createVbo();
        normalsVboList.add(normalsVbo);
        LoaderUtility.insertNormalsIntoVbo(normalsVbo, normals);
        return this;
    }

    @Override
    public MeshBuilder addTexture(Material texture) {
        this.texture = texture;
        return this;
    }

    @Override
    public MeshBuilder setBoundingRadius(float value) {
        boundingRadius = value;
        return this;
    }

    @Override
    public Mesh build() throws RuntimeException {
        Mesh myMesh;
        if (boundingRadius<0 || indicesLength<0) {
            throw new IllegalStateException();
        } try {
            myMesh = new MeshImpl(Objects.requireNonNull(texture), boundingRadius, indicesLength,
                    Objects.requireNonNull(myVao), Objects.requireNonNull(vboList),
                    Objects.requireNonNull(textureVboList));
        }catch (Exception e) {
            throw new RuntimeException("Cannot create mesh.");
        }
        return myMesh;
    }
}

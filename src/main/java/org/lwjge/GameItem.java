package org.lwjge;

import org.lwjge.graphic.Mesh;
import org.joml.Vector3f;

public class GameItem {

    private Mesh mesh;

    private Vector3f position = new Vector3f();

    private float scale;

    private Vector3f rotation = new Vector3f();

    private boolean insideFrustum;

    private boolean ignoreFrustum = false;

    public GameItem(Mesh mesh) {
        this.mesh = mesh;
        scale = 1;
    }

    public GameItem(){
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public boolean isInsideFrustum() {
        return insideFrustum;
    }

    public void setInsideFrustum(boolean insideFrustum) {
        this.insideFrustum = insideFrustum;
    }

    public boolean isIgnoreFrustum() {
        return ignoreFrustum;
    }

    public void setIgnoreFrustum(boolean ignoreFrustum) {
        this.ignoreFrustum = ignoreFrustum;
    }
}

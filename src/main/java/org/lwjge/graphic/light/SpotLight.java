package org.lwjge.graphic.light;

import org.joml.Vector3f;

public class SpotLight {

    private PointLight pointLight;
    private Vector3f coneDirection;
    private float cutOff;
    
    /**
     * Creates a new spot light that represent the light emitted from a lamp but restricted at a cone
     * @param pointLight
     * @param coneDirection
     * @param cutOffAngle
     */
    public SpotLight(PointLight pointLight, Vector3f coneDirection, float cutOffAngle) {
        this.pointLight = pointLight;
        this.coneDirection = coneDirection;
        setCutOffAngle(cutOffAngle);
    }
    
    /**
     * Creates a new spot light from an existing one
     * @param spotLight
     */
    public SpotLight(SpotLight spotLight) {
        this(new PointLight(spotLight.getPointLight()),
                new Vector3f(spotLight.getConeDirection()),
                0);
        setCutOff(spotLight.getCutOff());
    }

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public Vector3f getConeDirection() {
        return coneDirection;
    }

    public void setConeDirection(Vector3f coneDirection) {
        this.coneDirection = coneDirection;
    }

    public float getCutOff() {
        return cutOff;
    }

    public void setCutOff(float cutOff) {
        this.cutOff = cutOff;
    }

    public final void setCutOffAngle(float cutOffAngle) {
        this.setCutOff((float)Math.cos(Math.toRadians(cutOffAngle)));
    }
    
    @Override
	public boolean equals(Object o) {
		if(this == o) return true;
    	if(o == null || getClass() != o.getClass()) return false;
    	SpotLight light = (SpotLight) o;
    	return this.cutOff == light.cutOff &&
    			this.coneDirection.equals(light.coneDirection.x, light.coneDirection.y, light.coneDirection.z) &&
    			this.pointLight.equals(light.pointLight);
	}
    
    @Override
    public int hashCode() {
    	return this.coneDirection.hashCode() 
    			+ this.pointLight.hashCode();
    }

}
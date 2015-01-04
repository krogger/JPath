package to.us.harha.jpath.tracer.object;

import to.us.harha.jpath.util.math.Vec3f;

public class Material
{

    private Vec3f emittance;
    private Vec3f reflectance;
    private float reflectivity;
    private float refractivity;
    private float refractivityIndex;
    private float glossiness;

    public Material(Vec3f emittance, Vec3f reflectance, float reflectivity, float refractivity, float refractivityIndex, float glossiness)
    {
        this.emittance = emittance;
        this.reflectance = reflectance;
        this.reflectivity = reflectivity;
        this.refractivity = refractivity;
        this.refractivityIndex = refractivityIndex;
        this.glossiness = glossiness;
    }

    public Material(Vec3f reflectance, float reflectivity, float refractivity, float refractivityIndex, float glossiness)
    {
        emittance = new Vec3f();
        this.reflectance = reflectance;
        this.reflectivity = reflectivity;
        this.glossiness = glossiness;
        this.refractivity = refractivity;
        this.refractivityIndex = refractivityIndex;
    }

    public Material(Vec3f emittance, Vec3f reflectance)
    {
        this.emittance = emittance;
        this.reflectance = reflectance;
        reflectivity = 0.0f;
        refractivity = 0.0f;
        refractivityIndex = 0.0f;
        glossiness = 0.0f;
    }

    public Vec3f getEmittance()
    {
        return emittance;
    }

    public Vec3f getReflectance()
    {
        return reflectance;
    }

    public float getReflectivity()
    {
        return reflectivity;
    }

    public float getRefractivity()
    {
        return refractivity;
    }

    public float getRefractivityIndex()
    {
        return refractivityIndex;
    }

    public float getGlossiness()
    {
        return glossiness;
    }

}

package to.us.harha.jpath.util.math;

public class Intersection
{
    private final Vec3f pos;
    private final Vec3f norm;
    private final float t;

    public Intersection(Vec3f pos, Vec3f norm, float t)
    {
        this.pos = pos;
        this.norm = norm;
        this.t = t;
    }

    public Intersection()
    {
        pos = new Vec3f();
        norm = new Vec3f();
        t = 0.0f;
    }

    public Vec3f getPos()
    {
        return pos;
    }

    public Vec3f getNorm()
    {
        return norm;
    }

    public float getT()
    {
        return t;
    }

}

package to.us.harha.jpath.util.math;

import to.us.harha.jpath.Main;

public class Plane implements Shape
{
    private final Vec3f norm;
    private Vec3f pos;

    public Plane(Vec3f pos, Vec3f norm)
    {
        this.pos = pos;
        this.norm = norm.normalize();
    }

    @Override
    public Intersection intersect(Ray r)
    {
        float t = - r.getPos().sub(pos).dot(norm) / r.getDir().dot(norm);

        if (t < Main.EPSILON)
            return null;

        return new Intersection(r.getDir().scale(t).add(r.getPos()), norm, t);
    }

    @Override
    public boolean contains(Vec3f point) {
        return point.sub(pos).dot(norm) < 0.0f;
    }
}

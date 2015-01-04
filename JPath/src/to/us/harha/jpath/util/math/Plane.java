package to.us.harha.jpath.util.math;

import to.us.harha.jpath.Main;

public class Plane implements Primitive
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
        Vec3f P;
        float d, t;

        P = pos.sub(r.getPos());
        d = norm.dot(r.getDir());
        t = P.dot(norm) / d;

        if (t < Main.EPSILON)
            return null;

        return new Intersection(r.getDir().scale(t).add(r.getPos()), norm, t);
    }

}

package to.us.harha.jpath.util.math;

import to.us.harha.jpath.Main;

public class Sphere implements Primitive
{

    protected Vec3f pos;
    private float radius;

    public Sphere(Vec3f pos, float radius)
    {
        this.pos = pos;
        this.radius = radius;
    }

    @Override
    public Intersection intersect(Ray r)
    {
        Vec3f S;
        float b, c, h, t;

        S = r.getPos().sub(pos);
        b = S.dot(r.getDir());
        c = S.dot(S) - (radius * radius);
        h = b * b - c;

        if (h < 0.0f)
            return null;

        t = -b - (float) Math.sqrt(h);

        if (t < Main.EPSILON)
            return null;

        Vec3f point = r.getPos().add(r.getDir().scale(t));
        return new Intersection(point, point.sub(pos).divide(radius), t);
    }
}

package to.us.harha.jpath.util.math;

import to.us.harha.jpath.Main;

public class Sphere implements Solid
{
    private final Vec3f pos;
    private final float radius;
    private final float radiusSquared;

    public Sphere(Vec3f pos, float radius)
    {
        this.pos = pos;
        this.radius = radius;
        this.radiusSquared = radius * radius;
    }

    @Override
    public Intersection intersect(Ray r)
    {
        Vec3f s = r.getPos().sub(pos);
        float b = s.dot(r.getDir());
        float c = s.dot(s) - (radiusSquared);
        float h = b * b - c;

        if (h < 0.0f)
            return null;

        float t = -b - (float) Math.sqrt(h);

        if (t < Main.EPSILON)
            return null;

        Vec3f point = r.getPos().add(r.getDir().scale(t));
        return new Intersection(point, point.sub(pos).divide(radius), t);
    }

    public boolean contains(Vec3f point) {
        return pos.sub(point).length() <= radius;
    }

    public Vec3f getPosition()
    {
        return pos;
    }
}

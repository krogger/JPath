package to.us.harha.jpath.util.math;

import java.util.Arrays;

public class Lens implements Solid
{
    private CompoundSolid shape;

    public Lens(Vec3f pos, Vec3f normal, float radius, float thickness) {
        // f = focal length, t = thickness, r = disk radius
        // f^2 = (f-t/2)^2 + r^2
        // f^2 = f^2 - ft + t^2 / 4 + r^2
        // ft = t^2 / 4 + r^2
        // f = t/4 + r^2 / t;
        float focalLength = thickness / 4 + radius * radius / thickness;
        normal = normal.normalize();
        Vec3f focalPoint = normal.normalize().scale(focalLength - thickness / 2.0f);
        Solid s1 = new Sphere(pos.add(focalPoint), focalLength);
        Solid s2 = new Sphere(pos.sub(focalPoint), focalLength);
        shape = new CompoundSolid(Arrays.asList(s1, s2));
    }

    @Override
    public boolean contains(Vec3f point) {
        return shape.contains(point);
    }

    @Override
    public Intersection intersect(Ray r) {
        return shape.intersect(r);
    }
}

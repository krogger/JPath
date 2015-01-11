package to.us.harha.jpath.util.math;

import java.util.Arrays;

public class Lens implements Shape
{
    private UnionShape shape;

    public Lens(Vec3f pos, Vec3f normal, float radius, float centerWidth, float edgeWidth) {
        // f = focal length, t = thickness, r = disk radius
        // f^2 = (f-t/2)^2 + r^2
        // f^2 = f^2 - ft + t^2 / 4 + r^2
        // ft = t^2 / 4 + r^2
        // f = t/4 + r^2 / t;
        float focalLength = (centerWidth - edgeWidth) / 4 + radius * radius / (centerWidth - edgeWidth);
        normal = normal.normalize();
        Vec3f focalPoint = normal.normalize().scale(focalLength - centerWidth / 2.0f);
        Shape s1 = new Sphere(pos.add(focalPoint), focalLength);
        Shape s2 = new Sphere(pos.sub(focalPoint), focalLength);
        Shape burr = new Sphere(pos, radius * 0.90f);
        shape = new UnionShape(Arrays.asList(s1, s2, burr));
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

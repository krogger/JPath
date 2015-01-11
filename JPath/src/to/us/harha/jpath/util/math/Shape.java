package to.us.harha.jpath.util.math;

public interface Shape
{
    Intersection intersect(Ray r);

    boolean contains(Vec3f point);
}

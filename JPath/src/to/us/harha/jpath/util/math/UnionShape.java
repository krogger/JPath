package to.us.harha.jpath.util.math;

import java.util.List;

public class UnionShape implements Shape
{
    private List<Shape> shapes;

    public UnionShape(List<Shape> shapes)
    {
        this.shapes = shapes;
    }

    @Override
    public Intersection intersect(Ray r)
    {
        Intersection nearest = null;
        for(Shape shape : shapes)
        {
            Intersection intersection = shape.intersect(r);
            if (intersection != null && containsOthers(intersection.getPos(), shape)) {
                if (nearest == null || intersection.getT() < nearest.getT())
                {
                    nearest = intersection;
                }
            }
        }
        return nearest;
    }

    private boolean containsOthers(Vec3f point, Shape exclude)
    {
        for (Shape shape : shapes)
        {
            if (shape != exclude && !shape.contains(point)) return false;
        }
        return true;
    }

    @Override
    public boolean contains(Vec3f point)
    {
        for (Shape shape : shapes)
        {
            if (!shape.contains(point)) return false;
        }
        return true;
    }
}

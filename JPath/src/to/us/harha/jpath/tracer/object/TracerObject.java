package to.us.harha.jpath.tracer.object;

import to.us.harha.jpath.util.math.Shape;

public class TracerObject
{
    private Shape shape;
    private Material material;

    public TracerObject(Shape shape, Material material)
    {
        this.shape = shape;
        this.material = material;
    }

    public Shape getShape()
    {
        return shape;
    }

    public Material getMaterial()
    {
        return material;
    }

}

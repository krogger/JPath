package to.us.harha.jpath.tracer.object;

import to.us.harha.jpath.util.math.Primitive;

public class TracerObject
{

    private Primitive primitive;
    private Material material;

    public TracerObject(Primitive primitive, Material material)
    {
        this.primitive = primitive;
        this.material = material;
    }

    public Primitive getPrimitive()
    {
        return primitive;
    }

    public Material getMaterial()
    {
        return material;
    }

}

package to.us.harha.jpath.tracer.object;

import to.us.harha.jpath.util.math.Primitive;

import java.util.ArrayList;

public class TracerObject
{

    private ArrayList<Primitive> primitives;
    private Material material;

    public TracerObject(Material material)
    {
        primitives = new ArrayList<>();
        this.material = material;
    }

    public ArrayList<Primitive> getPrimitives()
    {
        return primitives;
    }

    public Material getMaterial()
    {
        return material;
    }

    public void addPrimitive(Primitive p)
    {
        primitives.add(p);
    }

}

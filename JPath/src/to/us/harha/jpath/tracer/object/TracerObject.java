package to.us.harha.jpath.tracer.object;

import to.us.harha.jpath.util.math.Primitive;

import java.util.ArrayList;

public class TracerObject
{

    private ArrayList<Primitive> m_primitives;
    private Material m_material;

    public TracerObject(Material material)
    {
        m_primitives = new ArrayList<>();
        m_material = material;
    }

    public ArrayList<Primitive> getPrimitives()
    {
        return m_primitives;
    }

    public Material getMaterial()
    {
        return m_material;
    }

    public void addPrimitive(Primitive p)
    {
        m_primitives.add(p);
    }

}

package to.us.harha.jpath.util.math;

import java.util.List;

public class CompoundSolid implements Primitive, Solid
{
    private List<Solid> solids;

    public CompoundSolid(List<Solid> solids)
    {
        this.solids = solids;
    }

    @Override
    public Intersection intersect(Ray r)
    {
        Intersection nearest = null;
        for(Solid solid : solids)
        {
            Intersection intersection = solid.intersect(r);
            if (intersection != null && containsOthers(intersection.getPos(), solid)) {
                if (nearest == null || intersection.getT() < nearest.getT())
                {
                    nearest = intersection;
                }
            }
        }
        return nearest;
    }

    private boolean containsOthers(Vec3f point, Solid exclude)
    {
        for (Solid solid : solids)
        {
            if (solid != exclude && !solid.contains(point)) return false;
        }
        return true;
    }

    @Override
    public boolean contains(Vec3f point)
    {
        for (Solid solid : solids)
        {
            if (!solid.contains(point)) return false;
        }
        return true;
    }
}

package to.us.harha.jpath.util.math;

import to.us.harha.jpath.Main;

public class Plane extends Primitive
{
    private final Vec3f m_norm;

    public Plane(Vec3f pos, Vec3f norm)
    {
        super(pos);
        m_norm = norm.normalize();
    }

    @Override
    public Intersection intersect(Ray r)
    {
        Vec3f P;
        float d, t;

        P = m_pos.sub(r.getPos());
        d = m_norm.dot(r.getDir());
        t = P.dot(m_norm) / d;

        if (t < Main.EPSILON)
            return null;

        Intersection x = new Intersection();
        x.setPos(r.getDir().scale(t).add(r.getPos()));
        x.setNorm(m_norm);
        x.setT(t);

        return x;
    }

}

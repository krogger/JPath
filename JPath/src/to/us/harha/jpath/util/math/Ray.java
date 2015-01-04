package to.us.harha.jpath.util.math;

public class Ray
{
    private final Vec3f pos;
    private final Vec3f dir;

    public Ray(Vec3f pos, Vec3f dir)
    {
        this.pos = pos;
        this.dir = dir.normalize();
    }

    public Vec3f getPos()
    {
        return pos;
    }

    public Vec3f getDir()
    {
        return dir;
    }

}

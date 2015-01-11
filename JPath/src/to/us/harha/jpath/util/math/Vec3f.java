package to.us.harha.jpath.util.math;

import java.util.Random;

public class Vec3f
{
    public final float x;
    public final float y;
    public final float z;

    public static final Vec3f UP = new Vec3f(0, 1, 0);
    public static final Vec3f DOWN = new Vec3f(0, -1, 0);
    public static final Vec3f LEFT = new Vec3f(-1, 0, 0);
    public static final Vec3f RIGHT = new Vec3f(1, 0, 0);
    public static final Vec3f FRONT = new Vec3f(0, 0, 1);
    public static final Vec3f BACK = new Vec3f(0, 0, -1);

    public static final Vec3f BLACK = new Vec3f(0, 0, 0);

    public Vec3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /*
     * Constructor Vec3f(float f);
     * @@param f: Sets the initial value for all vector components
     */
    public Vec3f(float f)
    {
        x = f;
        y = f;
        z = f;
    }

    /*
     * Constructor Vec3f();
     * @info: Blank vector constructor, all components are equal to 0.0f
     */
    public Vec3f()
    {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    @Override
    public String toString()
    {
        return "Vec3f[" + x + ", " + y + ", " + z + "]";
    }

    public Vec3f lerp(Vec3f u, float lerpFactor)
    {
        return this.sub(u).scale(lerpFactor).add(u);
    }

    public Vec3f add(Vec3f right)
    {
        return new Vec3f(x + right.x, y + right.y, z + right.z);
    }

    public Vec3f sub(Vec3f right)
    {
        return new Vec3f(x - right.x, y - right.y, z - right.z);
    }

    public Vec3f scale(Vec3f right)
    {
        return new Vec3f(x * right.x, y * right.y, z * right.z);
    }

    public Vec3f scale(float f)
    {
        return new Vec3f(f * x, f * y, f * z);
    }

    public Vec3f divide(float f)
    {
        return new Vec3f(x / f, y / f, z / f);
    }

    public Vec3f cross(Vec3f right)
    {
        return new Vec3f(y * right.z - right.y * z, z * right.x - right.z * x, x * right.y - right.x * y);
    }

    public float dot(Vec3f right)
    {
        return x * right.x + y * right.y + z * right.z;
    }

    public Vec3f normalize()
    {
        float length = length();
        return new Vec3f(x / length, y / length, z / length);
    }

    public float length()
    {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Vec3f negate()
    {
        return new Vec3f(-x, -y, -z);
    }

    public Vec3f rotate(Vec3f axis, float angle)
    {
        float sinAngle = (float) Math.sin(-angle);
        float cosAngle = (float) Math.cos(-angle);

        return this.cross(axis.scale(sinAngle)).add(this.scale(cosAngle)).add(axis.scale(this.dot(axis.scale(1.0f - cosAngle))));
    }

    public Vec3f reflect(Vec3f normal)
    {
        return this.sub(normal.scale(normal.dot(this) * 2.0f));
    }

    public Vec3f refract(Vec3f normal, float i1)
    {
        float NdotI = normal.dot(this);
        float NdotI2 = NdotI * NdotI;
        float eta = (NdotI > 0) ? 1.0f/i1 : i1;
        float k = 1.0f - eta * (1.0f - NdotI2);

        if (k < 0.0f)
            return reflect(normal).normalize();
        if (k >= 0)
        {
            return this.scale(eta).add(normal.scale(eta * NdotI - (float) Math.sqrt(k))).normalize();
        }
        else
        {
            return this.scale(eta).add(normal.scale(eta * NdotI + (float) Math.sqrt(k))).normalize();
        }
    }

    public Vec3f randomHemisphere(Random random)
    {
        Vec3f r;
        do
        {
            // TODO: Use spherical coords
            r = new Vec3f(2.0f * random.nextFloat() - 1.0f, 2.0f * random.nextFloat() - 1.0f, 2.0f * random.nextFloat() - 1.0f).normalize();
        } while (this.dot(r) <= 0);
        return r;
    }

}
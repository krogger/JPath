package to.us.harha.jpath.tracer;

import to.us.harha.jpath.Display;
import to.us.harha.jpath.tracer.object.Material;
import to.us.harha.jpath.tracer.object.TracerObject;
import to.us.harha.jpath.util.Logger;
import to.us.harha.jpath.util.math.Intersection;
import to.us.harha.jpath.util.math.Primitive;
import to.us.harha.jpath.util.math.Ray;
import to.us.harha.jpath.util.math.Vec3f;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Tracer
{

    private int m_maxrecursion;
    private int m_cpu_cores;
    private Vec3f[] m_samples;
    private AtomicInteger m_samples_taken;
    private Scene m_scene;
    private Logger m_log;

    public Tracer(Display display, int maxrecursion, int cpu_cores)
    {
        m_maxrecursion = maxrecursion;
        m_cpu_cores = cpu_cores;
        m_samples = new Vec3f[display.getWidth() * display.getHeight()];
        m_samples_taken = new AtomicInteger();
        m_scene = new Scene();
        m_log = new Logger(this.getClass().getName());

        clearSamples();

        m_log.printMsg("Tracer instance has been started, using " + m_cpu_cores + " CPU Cores!");
    }

    // Single threaded rendering
    public void render(Display display)
    {
        float width = display.getWidth();
        float height = display.getHeight();

        Ray ray = new Ray(new Vec3f(0.0f, 2.5f, 13.0f), new Vec3f(0.0f, 0.0f, -1.0f));
        for (int y = 0; y < display.getHeight(); y++)
        {
            for (int x = 0; x < display.getWidth(); x++)
            {
                int index = x + y * display.getWidth();
                //TODO: Add gitter to x and y to anti-alias
                float x_norm = (x - width * 0.5f) / width * display.getAR();
                float y_norm = (height * 0.5f - y) / height;
                ray.setDir(new Vec3f(x_norm, y_norm, -1.0f).normalize());

                m_samples[index] = m_samples[index].add(pathTrace(ray, 0));

                display.drawPixelVec3fAveraged(index, m_samples[index], m_samples_taken.get());
            }
        }
    }

    // Multithreaded rendering
    public void renderPortion(Display display, int t1, int t2)
    {
        int t = t1 + t2 * (m_cpu_cores / 2);

        if (t1 >= (m_cpu_cores / 2))
            t1 = (m_cpu_cores / 2) - 1;
        if (t2 >= (m_cpu_cores / 2))
            t2 = (m_cpu_cores / 2) - 1;

        float width = display.getWidth();
        float height = display.getHeight();
        int width_portion = display.getWidth() / (m_cpu_cores / 2);
        int height_portion = display.getHeight() / (m_cpu_cores / 2);

        Ray ray = new Ray(new Vec3f(0.0f, 2.5f, 13.0f), new Vec3f(0.0f, 0.0f, -1.0f));

        for (int y = height_portion * t2; y < (height_portion * t2) + height_portion; y++)
        {
            for (int x = width_portion * t1; x < (width_portion * t1) + width_portion; x++)
            {
                int xx = x - width_portion * t1;
                int yy = y - height_portion * t2;
                int index_screen = x + y * display.getWidth();
                int index_sample = xx + yy * width_portion;

                if (xx == 0 || xx == width_portion || yy == 0 || yy == width_portion)
                {
                    // For debugging purposes, leave the borders of a rendered portion unrendered
                } else
                {
                    float x_norm = (x - width * 0.5f) / width * display.getAR();
                    float y_norm = (height * 0.5f - y) / height;
                    ray.setDir(new Vec3f(x_norm, y_norm, -1.0f).normalize());

                    m_samples[index_screen] = m_samples[index_screen].add(pathTrace(ray, 0));

                    display.drawPixelVec3fAveraged(index_screen, m_samples[index_screen], m_samples_taken.get());
                }
            }
        }
    }

    /*
     * Path tracing
     * n = recursion level
     */
    public Vec3f pathTrace(Ray ray, int n)
    {
        // Return black if max recursion depth has been exceeded
        if (n > m_maxrecursion)
            return new Vec3f();

        // Initialize some objects and variables
        Vec3f color_final = new Vec3f();
        Intersection iSection = null;
        Intersection iSectionFinal = null;
        TracerObject OBJECT = null;
        float t_init = Float.MAX_VALUE;

        // Intersect the initial ray against all scene objects and find the closest intersection to the ray origin
        for (TracerObject o : m_scene.getObjects())
        {
            for (Primitive p : o.getPrimitives())
            {
                iSection = p.intersect(ray);
                if (iSection != null)
                {
                    if (iSection.getT() < t_init)
                    {
                        iSectionFinal = iSection;
                        t_init = iSection.getT();
                        OBJECT = o;
                    }
                }
            }
        }

        // If no intersection happened at all, return black
        if (iSectionFinal == null)
            return new Vec3f();

        // Get the object's surface material
        Material m = OBJECT.getMaterial();

        // If the object is a light source, return it's emittance
        if (m.getEmittance().length() > 0.0f)
            return m.getEmittance();

        // If the object is reflective like a mirror, reflect a ray
        if (m.getReflectivity() > 0.0f)
        {
            Ray newRay;
            if (m.getGlossiness() > 0.0f)
                newRay = new Ray(iSectionFinal.getPos(), ray.getDir().add(iSectionFinal.getNorm()).reflect(iSectionFinal.getNorm().randomHemisphere().scale(m.getGlossiness())).normalize());
            else
                newRay = new Ray(iSectionFinal.getPos(), ray.getDir().reflect(iSectionFinal.getNorm()).normalize());
            color_final = color_final.add(pathTrace(newRay, n + 1).scale(m.getReflectivity()));
        }

        // If the object is refractive like glass, refract the ray
        if (m.getRefractivity() > 0.0f)
        {
            Ray newRay;
            if (m.getGlossiness() > 0.0f)
                newRay = new Ray(iSectionFinal.getPos(), ray.getDir().refract(iSectionFinal.getNorm(), 1.0f, m.getRefractivityIndex()).add(iSectionFinal.getNorm().randomHemisphere().scale(m.getGlossiness())).normalize());
            else
                newRay = new Ray(iSectionFinal.getPos(), ray.getDir().refract(iSectionFinal.getNorm(), 1.0f, m.getRefractivityIndex()).normalize());
            color_final = color_final.add(pathTrace(newRay, n + 1).scale(m.getRefractivity()));
        }

        // Calculate the diffuse lighting if reflectance is greater than 0.0
        // NOTE: This could be improved / changed, it isn't physically correct at all atm and it's quite simple
        if (m.getReflectance().length() > 0.0f)
        {
            Ray newRay = new Ray(iSectionFinal.getPos(), iSectionFinal.getNorm().randomHemisphere().normalize());

            float NdotD = Math.abs(iSectionFinal.getNorm().dot(newRay.getDir()));
            Vec3f BRDF = m.getReflectance().scale(1.0f * NdotD);
            Vec3f REFLECTED = pathTrace(newRay, n + 1);

            color_final = color_final.add(m.getEmittance().add(BRDF.scale(REFLECTED)));
        }

        return color_final;
    }

    public void clearSamples()
    {
        Arrays.fill(m_samples, new Vec3f());
    }

    public void incrementSampleCounter()
    {
        m_samples_taken.incrementAndGet();
    }

}

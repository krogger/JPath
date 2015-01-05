package to.us.harha.jpath.tracer;

import to.us.harha.jpath.Display;
import to.us.harha.jpath.tracer.object.Material;
import to.us.harha.jpath.tracer.object.TracerObject;
import to.us.harha.jpath.util.Logger;
import to.us.harha.jpath.util.math.Intersection;
import to.us.harha.jpath.util.math.Ray;
import to.us.harha.jpath.util.math.Vec3f;

import java.util.Random;

public class Tracer
{
    private int maxDepth;
    private int section;
    private int sections;
    private Vec3f[] samples;
    private Scene scene;
    private Logger log;
    private Random random;

    public Tracer(Vec3f[] samples, int maxDepth, int section, int sections)
    {
        this.samples = samples;
        this.maxDepth = maxDepth;
        this.section = section;
        this.sections = sections;
        scene = new Scene();
        random = new Random();
        log = new Logger(this.getClass().getName());

        log.printMsg("Tracer " + section + " of " + sections + " started");
    }

    // Multithreaded rendering
    public void renderPortion(Display display, int sampleCount)
    {
        float width = display.getWidth();
        float height = display.getHeight();

        Vec3f camera = new Vec3f(0.0f, 2.5f, 13.0f);
        float pixelSize = 1.0f / height;
        for (int y = section; y < height; y += sections)
        {
            for (int x = 0;  x < width; x++)
            {
                int index = x + y * display.getWidth();

                float x_norm = (x - width * 0.5f) / width * display.getAR() + random.nextFloat() * pixelSize;
                float y_norm = (height * 0.5f - y) / height + random.nextFloat() * pixelSize;
                Ray ray = new Ray(camera, new Vec3f(x_norm, y_norm, -1.0f).normalize());

                samples[index] = samples[index].add(pathTrace(ray, 0));

                display.drawPixelVec3fAveraged(index, samples[index], sampleCount);
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
        if (n > maxDepth)
            return new Vec3f();

        // Initialize some objects and variables
        Vec3f color_final = new Vec3f();
        Intersection intersection = null;
        TracerObject nearestObject = null;

        // Intersect the initial ray against all scene objects and find the closest intersection to the ray origin
        for (TracerObject o : scene.getObjects())
        {
            Intersection candidate = o.getPrimitive().intersect(ray);
            if (candidate != null)
            {
                if (intersection == null || candidate.getT() < intersection.getT())
                {
                    intersection = candidate;
                    nearestObject = o;
                }
            }
        }

        // If no intersection happened at all, return black
        if (intersection == null)
            return new Vec3f();

        // Get the object's surface material
        Material m = nearestObject.getMaterial();

        // If the object is a light source, return it's emittance
        if (m.getEmittance().length() > 0.0f)
            return m.getEmittance();

        // If the object is reflective like a mirror, reflect a ray
        if (m.getReflectivity() > 0.0f)
        {
            Ray newRay;
            if (m.getGlossiness() > 0.0f)
                newRay = new Ray(intersection.getPos(), ray.getDir().reflect(intersection.getNorm()).add(intersection.getNorm().randomHemisphere(random).scale(m.getGlossiness())).normalize());
            else
                newRay = new Ray(intersection.getPos(), ray.getDir().reflect(intersection.getNorm()).normalize());
            color_final = color_final.add(pathTrace(newRay, n + 1).scale(m.getReflectivity()));
        }

        // If the object is refractive like glass, refract the ray
        if (m.getRefractivity() > 0.0f)
        {
            Ray newRay;
            if (m.getGlossiness() > 0.0f)
                newRay = new Ray(intersection.getPos(), ray.getDir().refract(intersection.getNorm(), 1.0f, m.getRefractivityIndex()).add(intersection.getNorm().randomHemisphere(random).scale(m.getGlossiness())).normalize());
            else
                newRay = new Ray(intersection.getPos(), ray.getDir().refract(intersection.getNorm(), 1.0f, m.getRefractivityIndex()).normalize());
            color_final = color_final.add(pathTrace(newRay, n + 1).scale(m.getRefractivity()));
        }

        // Calculate the diffuse lighting if reflectance is greater than 0.0
        // NOTE: This could be improved / changed, it isn't physically correct at all atm and it's quite simple
        if (m.getReflectance().length() > 0.0f)
        {
            Ray newRay = new Ray(intersection.getPos(), intersection.getNorm().randomHemisphere(random).normalize());

            float NdotD = Math.abs(intersection.getNorm().dot(newRay.getDir()));
            Vec3f BRDF = m.getReflectance().scale(1.0f * NdotD);
            Vec3f REFLECTED = pathTrace(newRay, n + 1);

            color_final = color_final.add(m.getEmittance().add(BRDF.scale(REFLECTED)));
        }

        return color_final;
    }

}

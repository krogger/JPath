package to.us.harha.jpath.tracer;

import to.us.harha.jpath.tracer.object.Material;
import to.us.harha.jpath.tracer.object.TracerObject;
import to.us.harha.jpath.util.math.Lens;
import to.us.harha.jpath.util.math.Plane;
import to.us.harha.jpath.util.math.Sphere;
import to.us.harha.jpath.util.math.Vec3f;

import java.util.ArrayList;

public class Scene
{
    private ArrayList<TracerObject> objects;

    public Scene()
    {
        objects = new ArrayList<>();

        Material mat_white_diffuse = new Material(new Vec3f(0.0f), new Vec3f(1.0f), 0.0f, 0.0f, 0.0f, 0.0f);
        Material mat_white_diffuse_reflective = new Material(new Vec3f(0.0f), new Vec3f(1.0f), 0.1f, 0.0f, 0.0f, 0.0f);
        Material mat_red_diffuse = new Material(new Vec3f(0.0f), new Vec3f(0.9f, 0.3f, 0.3f));
        Material mat_blue_mirror = new Material(new Vec3f(0.0f), new Vec3f(0.375f, 0.0f, 1.0f), 1.0f, 0.0f, 0.0f, 0.375f);
        Material mat_black_mirror = new Material(new Vec3f(0.0f), new Vec3f(), 1.0f, 0.0f, 0.0f, 0.0f);

        Material mat_cyan_glass = new Material(new Vec3f(0.0f), new Vec3f(0.0f, 0.5f, 1.0f), 0.1f, 1.0f, 1.52f, 0.0f);
        Material mat_black_glass = new Material(new Vec3f(0.0f), new Vec3f(), 0.1f, 0.8f, 1.52f, 0.0f);

        Material mat_white_light = new Material(new Vec3f(1.0f, 0.9450f, 0.8784f).scale(2.0f), new Vec3f());
        Material mat_lime_light = new Material(new Vec3f(0.5f, 1.0f, 0.0f).scale(16.0f), new Vec3f());
        Material mat_cyan_light = new Material(new Vec3f(0.25f, 0.5f, 1.0f).scale(16.0f), new Vec3f());

        // Build up the room
        Plane floor = new Plane(new Vec3f(0.0f, 0.0f, 0.0f), Vec3f.UP);
        objects.add(new TracerObject(floor, mat_white_diffuse));

        Plane ceiling = new Plane(new Vec3f(0.0f, 8.0f, 0.0f), Vec3f.DOWN);
        objects.add(new TracerObject(ceiling, mat_white_diffuse));

        Plane backWall = new Plane(new Vec3f(0.0f, 0.0f, -8.0f), Vec3f.FRONT);
        objects.add(new TracerObject(backWall, mat_white_diffuse));

        Plane rightWall = new Plane(new Vec3f(8.0f, 0.0f, 0.0f), Vec3f.LEFT);
        objects.add(new TracerObject(rightWall, mat_white_diffuse));

        Plane leftWall = new Plane(new Vec3f(-8.0f, 0.0f, 0.0f), Vec3f.RIGHT);
        objects.add(new TracerObject(leftWall, mat_white_light));

        Plane frontWall = new Plane(new Vec3f(0.0f, 0.0f, 16.0f), Vec3f.BACK);
        objects.add(new TracerObject(frontWall, mat_red_diffuse));

        // Add some lights
        Sphere ceilingLight = new Sphere(new Vec3f(0.0f, 8.0f, 0.0f), 1.25f);
        objects.add(new TracerObject(ceilingLight, mat_cyan_light));

        Sphere limeLight = new Sphere(new Vec3f(8.0f, 8.0f, 2.5f), 1.0f);
        objects.add(new TracerObject(limeLight, mat_lime_light));

        // Add some balls
        Sphere largeBall = new Sphere(new Vec3f(-2.75f, 2.0f, 2.5f), 2.0f);
        objects.add(new TracerObject(largeBall, mat_black_glass));

        Sphere blackMirrorBall = new Sphere(new Vec3f(5.0f, 2.0f, 0.0f), 2.0f);
        objects.add(new TracerObject(blackMirrorBall, mat_black_mirror));

        Sphere cyanMarble = new Sphere(new Vec3f(2.5f, 1.0f, 6.0f), 0.5f);
        objects.add(new TracerObject(cyanMarble, mat_cyan_glass));

        Sphere diffuseBall = new Sphere(new Vec3f(5.0f, 1.0f, 5.0f), 1.0f);
        objects.add(new TracerObject(diffuseBall, mat_white_diffuse_reflective));

        Sphere mirrorBall = new Sphere(new Vec3f(-1.0f, 2.0f, -5.0f), 2.0f);
        objects.add(new TracerObject(mirrorBall, mat_blue_mirror));

        //Add a lens
        Vec3f lensPosition = new Vec3f(1.5f, 1.5f, 7f);
        Vec3f pointTo = largeBall.getPosition().sub(lensPosition).normalize();
        Lens lens = new Lens(lensPosition, new Vec3f(1.0f, 0.0f, -0.2f), 1.0f, 0.1f);
        objects.add(new TracerObject(lens, mat_black_glass));
    }

    public ArrayList<TracerObject> getObjects()
    {
        return objects;
    }

}

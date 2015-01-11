package to.us.harha.jpath.tracer;

import to.us.harha.jpath.tracer.object.Material;
import to.us.harha.jpath.tracer.object.TracerObject;
import to.us.harha.jpath.util.math.Cube;
import to.us.harha.jpath.util.math.Lens;
import to.us.harha.jpath.util.math.Plane;
import to.us.harha.jpath.util.math.Shape;
import to.us.harha.jpath.util.math.Sphere;
import to.us.harha.jpath.util.math.UnionShape;
import to.us.harha.jpath.util.math.Vec3f;

import java.util.ArrayList;
import java.util.List;

public class SimpleScene implements Scene {
    private ArrayList<TracerObject> objects;

    public SimpleScene()
    {
        objects = new ArrayList<>();

        Material mat_white_diffuse = new Material(Vec3f.BLACK, new Vec3f(1.0f), 0.0f, 0.0f, 0.0f, 0.0f);
        Material mat_white_diffuse_reflective = new Material(Vec3f.BLACK, new Vec3f(1.0f), 0.1f, 0.0f, 0.0f, 0.0f);
        Material mat_red_diffuse = new Material(Vec3f.BLACK, new Vec3f(0.9f, 0.3f, 0.3f));
        Material mat_blue_mirror = new Material(Vec3f.BLACK, new Vec3f(0.375f, 0.0f, 1.0f), 1.0f, 0.0f, 0.0f, 0.375f);
        Material mat_black_mirror = new Material(Vec3f.BLACK, Vec3f.BLACK, 1.0f, 0.0f, 0.0f, 0.0f);

        Material mat_cyan_glass = new Material(Vec3f.BLACK, new Vec3f(0.0f, 0.5f, 1.0f), 0.1f, 0.9f, 1.52f, 0.0f);
        Material mat_clear_glass = new Material(Vec3f.BLACK, Vec3f.BLACK, 0.0f, 1.0f, 1.0f, 0.0f);

        Material mat_white_light = new Material(new Vec3f(1.0f, 1.0f, 1.0f).scale(1.0f), Vec3f.BLACK);
        Material mat_lime_light = new Material(new Vec3f(0.5f, 1.0f, 0.0f).scale(16.0f), Vec3f.BLACK);
        Material mat_cyan_light = new Material(new Vec3f(0.25f, 0.5f, 1.0f).scale(16.0f), Vec3f.BLACK);

        // Build up the room
        Plane floor = new Plane(new Vec3f(0.0f, 0.0f, 0.0f), Vec3f.UP);
        objects.add(new TracerObject(floor, mat_white_diffuse));

        Plane ceiling = new Plane(new Vec3f(0.0f, 8.0f, 0.0f), Vec3f.DOWN);
        objects.add(new TracerObject(ceiling, mat_white_diffuse));

        Plane backWall = new Plane(new Vec3f(0.0f, 0.0f, -8.0f), Vec3f.FRONT);
        objects.add(new TracerObject(backWall,mat_white_diffuse));

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
        objects.add(new TracerObject(largeBall, mat_clear_glass));

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
        Lens lens = new Lens(lensPosition, new Vec3f(0.5f, 0.0f, -0.5f), 1.0f, 0.2f, 0.01f);
        objects.add(new TracerObject(lens, mat_clear_glass));

        // How about a cube?
        Cube cube = new Cube(new Vec3f(0.0f, 1.0f, -7.0f), 1.0f);
        //objects.add(new TracerObject(cube, mat_red_diffuse));


        // Glass window
        List<Shape> planes = new ArrayList<>();
        planes.add(new Plane(new Vec3f(0,0,-3.1f), Vec3f.BACK));
        planes.add(new Plane(new Vec3f(0,0,-3), Vec3f.FRONT));
        UnionShape window = new UnionShape(planes);
        //objects.add(new TracerObject(window, Material.IDEAL_GLASS));
    }

    @Override
    public List<TracerObject> getObjects()
    {
        return objects;
    }

}

package to.us.harha.jpath.tracer;

import to.us.harha.jpath.tracer.object.Material;
import to.us.harha.jpath.tracer.object.TracerObject;
import to.us.harha.jpath.util.math.Plane;
import to.us.harha.jpath.util.math.Primitive;
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
        Material mat_red_diffuse = new Material(new Vec3f(0.0f), new Vec3f(1.0f, 0.0f, 0.0f));
        Material mat_blue_mirror = new Material(new Vec3f(0.0f), new Vec3f(0.375f, 0.0f, 1.0f), 1.0f, 0.0f, 0.0f, 0.375f);
        Material mat_black_mirror = new Material(new Vec3f(0.0f), new Vec3f(), 1.0f, 0.0f, 0.0f, 0.0f);

        Material mat_cyan_glass = new Material(new Vec3f(0.0f), new Vec3f(0.0f, 0.5f, 1.0f), 0.1f, 1.0f, 1.52f, 0.0f);
        Material mat_black_glass = new Material(new Vec3f(0.0f), new Vec3f(), 0.1f, 1.0f, 1.52f, 0.0f);

        Material mat_white_light = new Material(new Vec3f(1.0f, 0.9450f, 0.8784f).scale(2.0f), new Vec3f());
        Material mat_lime_light = new Material(new Vec3f(0.5f, 1.0f, 0.0f).scale(16.0f), new Vec3f());
        Material mat_cyan_light = new Material(new Vec3f(0.25f, 0.5f, 1.0f).scale(16.0f), new Vec3f());


        Primitive sphere_light_0 = new Sphere(new Vec3f(0.0f, 8.0f, 0.0f), 1.25f);
        Primitive sphere_light_1 = new Sphere(new Vec3f(8.0f, 8.0f, 2.5f), 1.0f);

        Primitive sphere_0 = new Sphere(new Vec3f(5.0f, 2.0f, 0.0f), 2.0f);
        Primitive sphere_1 = new Sphere(new Vec3f(-2.75f, 2.0f, 2.5f), 2.0f);
        Primitive sphere_2 = new Sphere(new Vec3f(2.5f, 1.0f, 6.0f), 0.5f);
        Primitive sphere_3 = new Sphere(new Vec3f(-1.0f, 2.0f, -5.0f), 2.0f);
        Primitive sphere_4 = new Sphere(new Vec3f(5.0f, 1.0f, 5.0f), 1.0f);

        Primitive floor_0 = new Plane(new Vec3f(0.0f, 0.0f, 0.0f), new Vec3f(0.0f, 1.0f, 0.0f));
        Primitive ceiling_0 = new Plane(new Vec3f(0.0f, 8.0f, 0.0f), new Vec3f(0.0f, -1.0f, 0.0f));
        Primitive wall_0 = new Plane(new Vec3f(0.0f, 0.0f, -8.0f), new Vec3f(0.0f, 0.0f, 1.0f));
        Primitive wall_1 = new Plane(new Vec3f(0.0f, 0.0f, 16.0f), new Vec3f(0.0f, 0.0f, -1.0f));
        Primitive wall_2 = new Plane(new Vec3f(-8.0f, 0.0f, 0.0f), new Vec3f(1.0f, 0.0f, 0.0f));
        Primitive wall_3 = new Plane(new Vec3f(8.0f, 0.0f, 0.0f), new Vec3f(-1.0f, 0.0f, 0.0f));

        TracerObject floor = new TracerObject(floor_0, mat_white_diffuse);
        TracerObject ceiling = new TracerObject(ceiling_0, mat_white_diffuse);

        TracerObject backWall = new TracerObject(wall_0, mat_white_diffuse);
        TracerObject rightWall = new TracerObject(wall_3, mat_white_diffuse);
        TracerObject cameraWall = new TracerObject(wall_1, mat_red_diffuse);
        TracerObject leftWall = new TracerObject(wall_2, mat_white_light);

        TracerObject diffuseBall = new TracerObject(sphere_4, mat_white_diffuse_reflective);
        TracerObject blackMirrorBall = new TracerObject(sphere_0, mat_black_mirror);
        TracerObject blueMirrorBall = new TracerObject(sphere_3, mat_blue_mirror);
        TracerObject smokyGlass = new TracerObject(sphere_1, mat_black_glass);
        TracerObject cyanGlassBall = new TracerObject(sphere_2, mat_cyan_glass);
        TracerObject limeLight = new TracerObject(sphere_light_1, mat_lime_light);
        TracerObject cyanLight = new TracerObject(sphere_light_0, mat_cyan_light);

        objects.add(floor);
        objects.add(ceiling);
        objects.add(backWall);
        objects.add(rightWall);
        objects.add(cameraWall);
        objects.add(leftWall);

        objects.add(diffuseBall);
        objects.add(blackMirrorBall);
        objects.add(blueMirrorBall);
        objects.add(cyanGlassBall);
        objects.add(smokyGlass);

        objects.add(cyanLight);
        objects.add(limeLight);
    }

    public ArrayList<TracerObject> getObjects()
    {
        return objects;
    }

}

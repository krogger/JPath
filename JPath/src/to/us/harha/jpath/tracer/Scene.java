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
        Material mat_green_diffuse = new Material(new Vec3f(0.0f), new Vec3f(0.0f, 1.0f, 0.0f));
        Material mat_blue_diffuse = new Material(new Vec3f(0.0f), new Vec3f(0.0f, 0.0f, 1.0f));

        Material mat_white_mirror = new Material(new Vec3f(0.0f), new Vec3f(1.0f), 1.0f, 0.0f, 0.0f, 0.0f);
        Material mat_red_mirror = new Material(new Vec3f(0.0f), new Vec3f(1.0f, 0.0f, 0.0f), 1.0f, 0.0f, 0.0f, 0.0f);
        Material mat_green_mirror = new Material(new Vec3f(0.0f), new Vec3f(0.0f, 1.0f, 0.0f), 1.0f, 0.0f, 0.0f, 0.0f);
        Material mat_blue_mirror = new Material(new Vec3f(0.0f), new Vec3f(0.375f, 0.0f, 1.0f), 1.0f, 0.0f, 0.0f, 0.375f);
        Material mat_black_mirror = new Material(new Vec3f(0.0f), new Vec3f(), 1.0f, 0.0f, 0.0f, 0.0f);

        Material mat_white_glass = new Material(new Vec3f(0.0f), new Vec3f(1.0f), 0.25f, 1.0f, 1.52f, 0.0f);
        Material mat_red_glass = new Material(new Vec3f(0.0f), new Vec3f(1.0f, 0.0f, 0.0f), 0.25f, 1.0f, 1.52f, 0.0f);
        Material mat_green_glass = new Material(new Vec3f(0.0f), new Vec3f(0.0f, 1.0f, 0.0f), 0.25f, 1.0f, 1.52f, 0.0f);
        Material mat_blue_glass = new Material(new Vec3f(0.0f), new Vec3f(0.0f, 0.0f, 1.0f), 0.25f, 1.0f, 1.52f, 0.0f);
        Material mat_cyan_glass = new Material(new Vec3f(0.0f), new Vec3f(0.0f, 0.5f, 1.0f), 0.1f, 1.0f, 1.52f, 0.0f);
        Material mat_black_glass = new Material(new Vec3f(0.0f), new Vec3f(), 0.1f, 1.0f, 1.52f, 0.0f);

        Material mat_white_light = new Material(new Vec3f(1.0f, 0.9450f, 0.8784f).scale(2.0f), new Vec3f());
        Material mat_lime_light = new Material(new Vec3f(0.5f, 1.0f, 0.0f).scale(16.0f), new Vec3f());
        Material mat_orange_light = new Material(new Vec3f(2.5f, 0.5f, 0.0f), new Vec3f());
        Material mat_cyan_light = new Material(new Vec3f(0.25f, 0.5f, 1.0f).scale(16.0f), new Vec3f());

        TracerObject obj_white_diffuse = new TracerObject(mat_white_diffuse);
        TracerObject obj_white_diffuse_reflective = new TracerObject(mat_white_diffuse_reflective);
        TracerObject obj_red_diffuse = new TracerObject(mat_red_diffuse);
        TracerObject obj_green_diffuse = new TracerObject(mat_green_diffuse);
        TracerObject obj_blue_diffuse = new TracerObject(mat_blue_diffuse);
        TracerObject obj_black_mirror = new TracerObject(mat_black_mirror);
        TracerObject obj_red_mirror = new TracerObject(mat_red_mirror);
        TracerObject obj_green_mirror = new TracerObject(mat_green_mirror);
        TracerObject obj_blue_mirror = new TracerObject(mat_blue_mirror);
        TracerObject obj_black_glass = new TracerObject(mat_black_glass);
        TracerObject obj_cyan_glass = new TracerObject(mat_cyan_glass);
        TracerObject obj_white_light = new TracerObject(mat_white_light);
        TracerObject obj_lime_light = new TracerObject(mat_lime_light);
        TracerObject obj_orange_light = new TracerObject(mat_orange_light);
        TracerObject obj_cyan_light = new TracerObject(mat_cyan_light);

        Primitive sphere_light_0 = new Sphere(new Vec3f(0.0f, 8.0f, 0.0f), 1.25f);
        Primitive sphere_light_1 = new Sphere(new Vec3f(8.0f, 8.0f, 2.5f), 1.0f);
        Primitive sphere_light_2 = new Sphere(new Vec3f(-5.0f, 8.0f, -5.0f), 1.0f);
        Primitive sphere_light_3 = new Sphere(new Vec3f(-5.0f, 8.0f, 5.0f), 1.0f);
        Primitive sphere_light_4 = new Sphere(new Vec3f(5.0f, 8.0f, -5.0f), 1.0f);
        Primitive sphere_light_5 = new Sphere(new Vec3f(3.75f, 0.0f, 7.5f), 1.0f);

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

        Primitive wall_light_0 = new Plane(new Vec3f(-13.0f, 0.0f, -13.0f), new Vec3f(0.5f, 0.0f, 0.5f));
        Primitive wall_light_1 = new Plane(new Vec3f(-13.0f, 0.0f, 13.0f), new Vec3f(0.5f, 0.0f, -0.5f));

        obj_white_diffuse.addPrimitive(floor_0);
        obj_red_diffuse.addPrimitive(wall_1);
        obj_white_diffuse.addPrimitive(wall_0);
        obj_white_light.addPrimitive(wall_2);
        obj_white_diffuse_reflective.addPrimitive(sphere_4);
        obj_white_diffuse.addPrimitive(wall_3);
        // obj_white_diffuse.addPrimitive(ceiling_0);
        obj_white_diffuse.addPrimitive(ceiling_0);
        obj_cyan_light.addPrimitive(sphere_light_0);
        obj_lime_light.addPrimitive(sphere_light_1);
        // obj_white_light.addPrimitive(sphere_light_2);
        // obj_white_light.addPrimitive(sphere_light_3);
        // obj_white_light.addPrimitive(sphere_light_4);
        obj_blue_mirror.addPrimitive(sphere_3);
        obj_black_mirror.addPrimitive(sphere_0);
        obj_black_glass.addPrimitive(sphere_1);
        obj_cyan_glass.addPrimitive(sphere_2);
        // obj_black_mirror.addPrimitive(sphere_1);
        // obj_red_mirror.addPrimitive(sphere_5);
        // obj_green_mirror.addPrimitive(sphere_3);
        // obj_blue_mirror.addPrimitive(sphere_4);
        // obj_black_glass.addPrimitive(sphere_2);
        // obj_white_light.addPrimitive(sphere_6);
        // obj_cyan_light.addPrimitive(sphere_light_5);
        // obj_orange_light.addPrimitive(sphere_8);
        // obj_cyan_light.addPrimitive(sphere_9);

        objects.add(obj_white_diffuse_reflective);
        objects.add(obj_white_diffuse);
        objects.add(obj_green_diffuse);
        //objects.add(obj_blue_diffuse);
        objects.add(obj_red_diffuse);

        objects.add(obj_black_mirror);
        objects.add(obj_red_mirror);
        objects.add(obj_green_mirror);
        objects.add(obj_blue_mirror);

        objects.add(obj_cyan_glass);
        objects.add(obj_black_glass);

        objects.add(obj_white_light);
        objects.add(obj_cyan_light);
        objects.add(obj_lime_light);
    }

    public ArrayList<TracerObject> getObjects()
    {
        return objects;
    }

}

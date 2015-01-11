package to.us.harha.jpath.util.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static to.us.harha.jpath.util.math.Vec3f.*;

public class Cube implements Shape {

    private Shape shape;

    public Cube(Vec3f center, float length) {
        List<Shape> faces = new ArrayList<>();

        for (Vec3f dir : Arrays.asList(UP, DOWN, LEFT, RIGHT, FRONT, BACK))
        {
            faces.add(new Plane(center.add(dir.scale(length / 2.0f)), dir));
        }
        shape = new UnionShape(faces);
    }

    @Override
    public boolean contains(Vec3f point) {
        return shape.contains(point);
    }

    @Override
    public Intersection intersect(Ray r) {
        return shape.intersect(r);
    }
}

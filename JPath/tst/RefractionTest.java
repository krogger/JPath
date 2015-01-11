import org.junit.Test;
import to.us.harha.jpath.tracer.Scene;
import to.us.harha.jpath.tracer.Tracer;
import to.us.harha.jpath.tracer.object.Material;
import to.us.harha.jpath.tracer.object.TracerObject;
import to.us.harha.jpath.util.math.Plane;
import to.us.harha.jpath.util.math.Ray;
import to.us.harha.jpath.util.math.Shape;
import to.us.harha.jpath.util.math.UnionShape;
import to.us.harha.jpath.util.math.Vec3f;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RefractionTest {
    @Test
    public void canLookThroughGlass() {

        Scene scene = new Scene() {
            @Override
            public List<TracerObject> getObjects() {
                List<TracerObject> objects = new ArrayList<>();

                // Light Wall in Back
                objects.add(new TracerObject(new Plane(new Vec3f(0, 0, -10), Vec3f.FRONT), Material.WHITE_LIGHT));

                List<Shape> planes = new ArrayList<>();
                planes.add(new Plane(new Vec3f(0,0,-4), Vec3f.BACK));
                planes.add(new Plane(new Vec3f(0,0,-3), Vec3f.FRONT));
                UnionShape window = new UnionShape(planes);
                objects.add(new TracerObject(window, Material.IDEAL_GLASS));
                return objects;
            }
        };

        Tracer tracer = new Tracer(scene, null, 5, 0, 1);
        Vec3f camera = new Vec3f(0.0f, 2.5f, 13.0f);
        Ray ray = new Ray(camera, new Vec3f(0, 0.2f, -1));
        Vec3f color = tracer.pathTrace(ray, 0);
        assertEquals(1.0f, color.x, 0.001);



    }
}

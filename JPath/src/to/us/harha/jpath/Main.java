package to.us.harha.jpath;

import java.util.Random;

public class Main
{
    public static final String TITLE = "Java Path Tracer";
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static final float EPSILON = 1e-3f;

    public static void main(String[] args)
    {
        Display display = new Display(WIDTH, HEIGHT, TITLE);
        display.create();
        Engine engine = new Engine(display);
        engine.run();
    }

}

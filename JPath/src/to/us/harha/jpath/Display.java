package to.us.harha.jpath;

import to.us.harha.jpath.util.MathUtils;
import to.us.harha.jpath.util.math.Vec3f;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Display extends Canvas
{
    private String title;
    private int[] pixels;
    private BufferedImage image;
    private JFrame jframe;
    private float aspectRatio;

    public Display(int width, int height, String title)
    {
        setSize(width, height);
        this.title = title;
        aspectRatio = (float) width / (float) height;
    }

    public void create()
    {
        // Create the bitmap
        if (image == null)
        {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        }

        // Create the jframe
        if (jframe == null)
        {
            Dimension dimension = new Dimension(getWidth(), getHeight());
            setPreferredSize(dimension);
            jframe = new JFrame();
            jframe.setResizable(false);
            jframe.setTitle(title);
            jframe.add(this);
            jframe.pack();
            jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            jframe.setLocationRelativeTo(null);
            jframe.setVisible(true);
        }
    }

    public void drawPixelVec3fAveraged(int index, Vec3f v, int factor)
    {
        Vec3f average = MathUtils.clamp(v.divide(factor), 0.0f, 1.0f);

        // Calculate the hexadecimal color from the vector parameters
        long red = (long) (average.x * 255.0f);
        long green = (long) (average.y * 255.0f);
        long blue = (long) (average.z * 255.0f);
        long hex_value = ((red << 16) | (green << 8) | blue);

        pixels[index] = (int) hex_value;
    }

    public void drawPixelVec3f(int x, int y, Vec3f v)
    {
        if (x < 0 || x > getWidth() || y < 0 || y > getHeight())
            return;

        // Get the 2D index in the 1D array
        int index = x + y * getWidth();

        // Calculate the hexadecimal color from the vector parameters
        long red = (long) (v.x * 255.0f);
        long green = (long) (v.y * 255.0f);
        long blue = (long) (v.z * 255.0f);
        long hex_value = ((red << 16) | (green << 8) | blue);

        pixels[index] = (int) hex_value;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public float getAR()
    {
        return aspectRatio;
    }

}

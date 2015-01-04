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
    private String m_title;
    private int m_width;
    private int m_height;
    private int[] m_pixels;
    private BufferedImage m_image;
    private JFrame m_jframe;

    public Display(int width, int height, String title)
    {
        m_title = title;
        m_width = width;
        m_height = height;
    }

    public void create()
    {
        // Create the bitmap
        if (m_image == null)
        {
            m_image = new BufferedImage(m_width, m_height, BufferedImage.TYPE_INT_RGB);
            m_pixels = ((DataBufferInt) m_image.getRaster().getDataBuffer()).getData();
        }

        // Create the jframe
        if (m_jframe == null)
        {
            Dimension dimension = new Dimension(m_width, m_height);
            setPreferredSize(dimension);
            m_jframe = new JFrame();
            m_jframe.setResizable(false);
            m_jframe.setTitle(m_title);
            m_jframe.add(this);
            m_jframe.pack();
            m_jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            m_jframe.setLocationRelativeTo(null);
            m_jframe.setVisible(true);
        }
    }

    public void drawPixelVec3fAveraged(int index, Vec3f v, int factor)
    {
        if (index < 0 || index > m_width * m_height)
            return;

        Vec3f average = MathUtils.clamp(v.divide(factor), 0.0f, 1.0f);

        // Calculate the hexadecimal color from the vector parameters
        long red = (long) (average.x * 255.0f);
        long green = (long) (average.y * 255.0f);
        long blue = (long) (average.z * 255.0f);
        long hex_value = ((red << 16) | (green << 8) | blue);

        m_pixels[index] = (int) hex_value;
    }

    public void drawPixelVec3f(int x, int y, Vec3f v)
    {
        if (x < 0 || x > m_width || y < 0 || y > m_height)
            return;

        // Get the 2D index in the 1D array
        int index = x + y * m_width;

        // Calculate the hexadecimal color from the vector parameters
        long red = (long) (v.x * 255.0f);
        long green = (long) (v.y * 255.0f);
        long blue = (long) (v.z * 255.0f);
        long hex_value = ((red << 16) | (green << 8) | blue);

        m_pixels[index] = (int) hex_value;
    }

    public int getWidth()
    {
        return m_width;
    }

    public int getHeight()
    {
        return m_height;
    }

    public BufferedImage getImage()
    {
        return m_image;
    }

    public float getAR()
    {
        return (float) m_width / (float) m_height;
    }

}

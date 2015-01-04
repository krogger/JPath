package to.us.harha.jpath;

import to.us.harha.jpath.tracer.Tracer;
import to.us.harha.jpath.util.Logger;
import to.us.harha.jpath.util.TimeUtils;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Engine
{
    private int m_cpu_cores;
    private Display m_display;
    private Tracer m_tracer;
    private Logger m_log;
    private ExecutorService m_eService;

    public Engine(Display display)
    {
        m_cpu_cores = Runtime.getRuntime().availableProcessors();

        m_log = new Logger(this.getClass().getName());
        m_log.printMsg("Engine instance has been started! # of Available CPU Cores: " + m_cpu_cores);

        m_display = display;
        m_tracer = new Tracer(m_display, 4, m_cpu_cores);
        m_eService = Executors.newFixedThreadPool(m_cpu_cores);
        m_display.createBufferStrategy(2);
    }

    public void run()
    {
        double start = TimeUtils.getTime();
        int frames = 0;
        while (true)
        {
            render();
            frames++;
            m_log.printMsg("Frame: " + frames + " Elapsed Time: " + (TimeUtils.getTime() - start));
        }
    }

    private void render()
    {
        BufferStrategy bs = m_display.getBufferStrategy();
        m_tracer.incrementSampleCounter();

        // This is just some quick code, everything will be changed and fixed, it's just really late atm
        // and I want to get something that works at least in some way
        if (m_cpu_cores < 4)
        {
            m_tracer.render(m_display);
        } else
        {
            for (int j = 0; j < m_cpu_cores / 2; j++)
            {
                for (int i = 0; i < m_cpu_cores / 2; i++)
                {
                    m_tracer.renderPortion(m_display, i, j);
                }
            }
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(m_display.getImage(), 0, 0, m_display.getWidth(), m_display.getHeight(), null);
        g.dispose();
        bs.show();
    }

}
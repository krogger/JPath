package to.us.harha.jpath;

import to.us.harha.jpath.tracer.Tracer;
import to.us.harha.jpath.util.Logger;
import to.us.harha.jpath.util.TimeUtils;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Engine
{
    private int m_cpu_cores;
    private Display m_display;
    private Tracer m_tracer;
    private Logger m_log;
    private Executor m_executor;

    public Engine(Display display)
    {
        m_cpu_cores = Runtime.getRuntime().availableProcessors() - 1;

        m_log = new Logger(this.getClass().getName());
        m_log.printMsg("Engine instance has been started! # of Available CPU Cores: " + m_cpu_cores);

        m_display = display;
        m_tracer = new Tracer(m_display, 4, m_cpu_cores);
        m_executor = Executors.newFixedThreadPool(m_cpu_cores);
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

        if (m_cpu_cores < 2)
        {
            m_tracer.render(m_display);
        } else
        {
            renderParallel();
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(m_display.getImage(), 0, 0, m_display.getWidth(), m_display.getHeight(), null);
        g.dispose();
        bs.show();
    }

    private void renderParallel() {
        final CountDownLatch latch = new CountDownLatch(m_cpu_cores);

        for (int i = 0; i < m_cpu_cores ; i++)
        {
            final int section = i;
            new Thread() {
                @Override
                public void run()
                {
                    m_tracer.renderPortion(m_display, section);
                    latch.countDown();
                }
            }.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
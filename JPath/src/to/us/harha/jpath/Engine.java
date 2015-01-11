package to.us.harha.jpath;

import to.us.harha.jpath.tracer.LensFocusScene;
import to.us.harha.jpath.tracer.SimpleScene;
import to.us.harha.jpath.tracer.Tracer;
import to.us.harha.jpath.util.Logger;
import to.us.harha.jpath.util.TimeUtils;
import to.us.harha.jpath.util.math.Vec3f;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Engine
{
    private int sections;
    private Display display;
    private Tracer[] tracers;
    private Logger log;

    public Engine(Display display)
    {
        this.display = display;

        sections = Runtime.getRuntime().availableProcessors();
        //sections = 1;
        log = new Logger(this.getClass().getName());
        log.printMsg("Engine instance has been started! # of Available CPU Cores: " + sections);

        Vec3f[] samples = new Vec3f[display.getWidth() * display.getHeight()];
        Arrays.fill(samples, new Vec3f());

        tracers = new Tracer[sections];
        for (int section = 0; section < sections; section++)
        {
            tracers[section] = new Tracer(new SimpleScene(), samples, 20, section, sections);
        }
        this.display.createBufferStrategy(2);
    }

    public void run()
    {
        double start = TimeUtils.getTime();
        int frames = 0;
        while (true)
        {
            frames++;
            render(frames);
            log.printMsg("Frame: " + frames + " Elapsed Time: " + (TimeUtils.getTime() - start));
        }
    }

    private void render(final int sampleCount)
    {
        final CountDownLatch latch = new CountDownLatch(sections);

        for (final Tracer tracer : tracers)
        {
            new Thread() {
                @Override
                public void run()
                {
                    tracer.renderPortion(display, sampleCount);
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

        BufferStrategy bs = display.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.drawImage(display.getImage(), 0, 0, display.getWidth(), display.getHeight(), null);
        g.dispose();
        bs.show();
    }

    private void renderParallel() {
    }
}
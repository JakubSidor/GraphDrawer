import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window extends JFrame {

    private Render renderer;
    private Pattern pattern;
    private final int border_size = 28;

    public Window()
    {
        setTitle("GraphDrawer");
        setResizable( Boolean.parseBoolean(Config.getProperty("RESIZABLE")) );

        setSize(
                Integer.parseInt(Config.getProperty("WIDTH")) - border_size,
                Integer.parseInt(Config.getProperty("HEIGHT"))
        );

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        renderer = new Render();

        add(renderer);



        pattern = new Pattern()
        {
            @Override
            public int pattern(int x) {
                return x;
            }
        };
        setVisible(true);
    }

    public void render()
    {
        renderer.render();
    }

    private class Render extends Canvas
    {

        public void render()
        {
            BufferStrategy bs = this.getBufferStrategy();

            if (bs == null) {
                this.createBufferStrategy(3);
                return;
            }

            Graphics graphics = bs.getDrawGraphics();

            graphics.setColor(Color.DARK_GRAY);
            graphics.fillRect(0, 0, getSize().width, getSize().height);

            pattern.draw(graphics);

            graphics.dispose();
            bs.show();
        }

    }
}

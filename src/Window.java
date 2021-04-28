import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.net.http.WebSocket;
import java.util.EventListener;
import java.util.Optional;

public class Window extends JFrame {

    private Render renderer;
    private Pattern pattern;
    private final int border_size = 35;

    public Window()
    {
        createFrame();
        createMenu();

        renderer = new Render();

        add(renderer);



        pattern = new Pattern(Pattern.Dynamic.Dynamic)
        {
            @Override
            public int pattern(int x) {
                return x;
            }
        };
        setVisible(true);
    }

    public void createFrame()
    {
        setTitle("GraphDrawer");
        setResizable( Boolean.parseBoolean(Config.getProperty("RESIZABLE")) );

        setSize(
                Integer.parseInt(Config.getProperty("WIDTH")) - border_size,
                Integer.parseInt(Config.getProperty("HEIGHT"))
        );

        System.out.println(Config.getProperty("OS"));
        if(Config.getProperty("OS").equals("MAC OS X"))
        {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Creates and adds JMenu component
    public void createMenu()
    {
        JMenuBar jMenuBar = new JMenuBar();

        //FILE
        {
            JMenu jMenuFile = new JMenu("File");
            JMenuItem jMIFile = new JMenuItem("Export");

            jMenuFile.add(jMIFile);
            jMenuBar.add(jMenuFile);


        }
        //PREFERENCES
        {
            JMenu jMenuPref = new JMenu("Preferences");
            JMenuItem jMIPref = new JMenuItem("Preferences");
            jMenuPref.add(jMIPref);

            jMenuBar.add(jMenuPref);

        }

        //If mouse enters menu it stops dynamic drawing(freeze graph) until user exit menu
        MouseListener stopDynamic = new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                pattern.setSuspend(false);
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {
                pattern.setSuspend(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pattern.setSuspend(false);
            }
        };

        //Adds MouseListener to all objects in jMenuBar
        {
            AddListnerToAllChildren(jMenuBar, stopDynamic);
        }

        setJMenuBar(jMenuBar);


    }

    public void AddListnerToAllChildren(JComponent component, MouseListener listener)
    {
        component.addMouseListener(listener);
        System.out.println(component.getComponents().length);
        for (Component c : component.getComponents())
        {
            JComponent jc = null;
            if(c instanceof JComponent)
            {
                jc = (JComponent)c;
            }

            if(c instanceof JMenu)
            {
                for (Component jcmc : ((JMenu) c).getMenuComponents() ){
                    AddListnerToAllChildren((JComponent) jcmc, listener);
                }
            }

            try
            {
                AddListnerToAllChildren(jc, listener);
            }
            catch (NullPointerException npe)
            {
                npe.printStackTrace();
            }

        }
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

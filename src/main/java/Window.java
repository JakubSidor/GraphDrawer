import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;


public class Window extends JFrame {

    private Render renderer;
    private ArrayList<Pattern> patterns = new ArrayList<>();
    private final int border_size = 35;

    public Window()
    {
        createFrame();
        createMenu();

        renderer = new Render();

        add(renderer);
        for(double d = 0; d<5; d+=0.5) {
            patterns.add(Prefabs.constructPrefab((d)+"*x", Color.ORANGE, Pattern.Dynamic.Dynamic));
        }

        patterns.add( new Pattern(Pattern.Dynamic.Dynamic, Color.GREEN)
        {
            @Override
            public int pattern(int x)
            {
                return (int) ((Math.cos(x * 0.05) * 60) + 360) ;
            }
        });

        patterns.add( new Pattern(Pattern.Dynamic.Dynamic)
        {
            @Override
            public int pattern(int x) {
                return x;
            }
        });

        patterns.add( new Pattern(Pattern.Dynamic.Dynamic, Color.BLUE)
        {
            @Override
            public int pattern(int x) {
                return (int) ((x-360)*(x-300)*(x-450) * 0.00028) + 360;
            }
        });




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
        //PATTERNS
        {
            JMenu jMenuPatt = new JMenu("Patterns");
            JMenuItem jMIPatt = new JMenuItem("Add");
            jMenuPatt.add(jMIPatt);

            jMenuBar.add(jMenuPatt);
        }

        //If mouse enters menu it stops dynamic drawing(freeze graph) until user exit menu
        MouseListener stopDynamic = new MouseListener()
        {
            boolean suspend = false;
            //Switch state of "suspend" for every pattern from ArrayList "patterns"
            Pattern.ForEveryPattern fep = (patterns -> {
                for (Pattern p : patterns)
                {
                    p.setSuspend(suspend);
                }
            });

            @Override
            public void mouseClicked(MouseEvent e) {
                suspend = false;
                fep.forEveryPattern(patterns);
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {
                suspend = true;
                fep.forEveryPattern(patterns);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                suspend = false;
                fep.forEveryPattern(patterns);
            }
        };

        AddListnerToAllChildren(jMenuBar, stopDynamic);

        setJMenuBar(jMenuBar);
    }

    //Recursively adds MouseListener to passed JComponent and its children
    public void AddListnerToAllChildren(JComponent component, MouseListener listener)
    {
        component.addMouseListener(listener);
        for (Component c : component.getComponents())
        {
            JComponent jc = null;

            if(c instanceof JComponent)
            {
                jc = (JComponent)c;
            }

            if(c instanceof JMenu)
            {
                for (Component jcmc : ((JMenu) c).getMenuComponents())
                {
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

            Pattern.ForEveryPattern fep = (patterns -> {
                for (Pattern p : patterns)
                {
                    p.draw(graphics);
                }
            });

            graphics.setColor(Color.DARK_GRAY);
            graphics.fillRect(0, 0, getSize().width, getSize().height);

            fep.forEveryPattern(patterns);

            graphics.dispose();
            bs.show();
        }

    }
}

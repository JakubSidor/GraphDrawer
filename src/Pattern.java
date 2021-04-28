import java.awt.*;

public abstract class Pattern
{
    public Pattern(Dynamic dynamic)
    {
        this.dynamic = dynamic;
    }
    private int WIDTH = Integer.parseInt(Config.getProperty("WIDTH"));
    private boolean CROSS = Boolean.parseBoolean(Config.getProperty("CROSS"));
    public Dynamic dynamic;
    private boolean suspend = false;

    public void setSuspend(boolean suspend)
    {
        this.suspend = suspend;
    }

    public void draw(Graphics g)
    {
        if(CROSS)
        {
            g.setColor(Color.BLACK);
            g.drawLine(360, 0, 360, 720);
            g.drawLine(0, 360, 720, 360);
        }

        g.setColor(Color.RED);

        if(dynamic == Dynamic.Static) {

            for (int i = 0; i < Integer.parseInt(Config.getProperty("WIDTH")); i++) {
                g.drawLine(
                        i,
                        pattern(i),
                        i + 1,
                        pattern(i + 1)
                );
            }

        }

        if(dynamic == Dynamic.Dynamic) {
            for (int i = 0; i < CalculateDynamic() ; i++) {
                g.drawLine(
                        i,
                        pattern(i),
                        i + 1,
                        pattern(i + 1)
                );
            }

            if(!suspend) {
                actual++;
            }
        }


    }

    public abstract int pattern(int x);

    private int actual = 0;
    public int CalculateDynamic()
    {
        if(actual > WIDTH)
        {
            actual = 0;
        }

        return actual;
    }

    public enum Dynamic
    {
        Dynamic,
        Static
    }
}

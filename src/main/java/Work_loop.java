public class Work_loop
{

    private Window window;

    public void run()
    {
        window = new Window();
        loop();
    }

    private void loop()
    {
        while (true)
        {
            window.render();
            try {
                Thread.sleep(1);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}

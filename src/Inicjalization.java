public class Inicjalization {

    public static void main(String[] args)
    {

        initialize();

        Work_loop work_loop = new Work_loop();
        work_loop.run();
    }

    //call readConfig() method and setup start properties
    public static void initialize()
    {
        Config.readConfigFile();
        Config.readSystemProperties();
    }
}

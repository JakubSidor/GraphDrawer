import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Config
{
    //path to configuration file
    private static final String PATH = "/Users/jakubsidor/Downloads/GraphDrawer/src/main/java/config.ini";

    //contain all configuration data in pairs(NAME:PROPERTY)
    private static final HashMap<String, String> CONFIG_DATA = new HashMap<>();

    //loads config data from file
    public static void readConfigFile()
    {
        try
        {
            List<String> data = Files.readAllLines(Paths.get(PATH));

            for (String s : data)
            {

                CONFIG_DATA.put(
                        s.substring(0, s.indexOf(":")),
                        s.substring(s.indexOf(":") + 1)
                );

            }

        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }

    //checks some informations from system such a operating system
    public static void readSystemProperties()
    {
        CONFIG_DATA.put("OS", System.getProperty("os.name").toUpperCase(Locale.ROOT) );

        switch (Config.getProperty("OS"))
        {
            case "MAC OS X":
                //System.setProperty("apple.laf.useScreenMenuBar", "true");
                break;
            case "WINDOWS":
                break;
        }
    }

    //return specified name property from CONFIG_DATA
    public static String getProperty(String propertyName)
    {
        return CONFIG_DATA.get(propertyName);
    }

}

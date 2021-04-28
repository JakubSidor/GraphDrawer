import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config
{
    //path to configuration file
    private static final String PATH = "/Users/jakubsidor/Downloads/GraphDrawer/out/production/GraphDrawer/config.ini";

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

    }

    //return specified name property from CONFIG_DATA
    public static String getProperty(String propertyName)
    {
        String property = CONFIG_DATA.get(propertyName);
        return property;
    }

}

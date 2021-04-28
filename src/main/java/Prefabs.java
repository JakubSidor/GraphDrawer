import com.fathzer.soft.javaluator.DoubleEvaluator;
import jdk.jshell.EvalException;

import java.awt.*;
import java.util.HashMap;


public class Prefabs
{
    //Contains prefabricated sets of mathemathic functions, fractals etc. and names as keys
    private static HashMap<String, Pattern> prefabs = new HashMap<>();

    public static Pattern constructPrefab(String mathExpression, Color color, Pattern.Dynamic dynamics)
    {
        DoubleEvaluator evaluator = new DoubleEvaluator();

        Pattern pattern = new Pattern(dynamics ,color)
            {
                @Override
                public int pattern(int x) {


                    String c = Integer.toString(x);
                    String finalExpression = mathExpression.replace("x", c);
                    int finalExpressionAsInt = 0;
                    try
                    {
                        finalExpressionAsInt = evaluator.evaluate(finalExpression).intValue();
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                    }

                    return finalExpressionAsInt;

                }
            };

            return pattern;
    }


}

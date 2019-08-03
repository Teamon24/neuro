import com.home.neuro.NeuroConfigs;
import com.home.neuro.WeightsMatrix;
import com.home.utils.elements.DoubleMatrix2D;
import com.home.utils.elements.Matrix2D;
import com.home.utils.elements.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.println;

/**
 *
 */
public class Main {

    public static final Random RANDOM = new Random();

    static Short newShort(String a) {
        return new Short(a);
    }
    static Short randShort() {  return Short.valueOf(String.format("%s", RANDOM.nextInt())); }

    public static void main(String[] args) {
        NeuroConfigs configs = new NeuroConfigs(16, 5, 3);
        ArrayList<Integer> neuronsPerLayers = configs.getNeuronsPerMiddles();
        println("${configs.firstsAmount} $neuronsPerLayers ${configs.outputsAmount}");
        WeightsMatrix weights = emptyWeightsMatrix(configs);
        Integer i = 0; // 0 - входной с первым промежуточным.
        final DoubleMatrix2D doubleMatrix2D = weights.get(i);
        println(doubleMatrix2D.toString());
         // вес между первым во входном и первым в первом промежуточном слое.
    }

    public static WeightsMatrix emptyWeightsMatrix(NeuroConfigs configs)  {
        ArrayList<Integer> neuronsPerLayers = configs.getNeuronsPerLayers();
        Integer columns = neuronsPerLayers.size() - 1;

        List<DoubleMatrix2D> weights = new ArrayList<>();

        for (int i = 0; i < columns; i++) {
            Integer n = neuronsPerLayers.get(i);
            Integer m = neuronsPerLayers.get(i + 1);
            DoubleMatrix2D element = new DoubleMatrix2D(n, m);
            weights.add(i, element);
        }
        return new WeightsMatrix(weights);
    }
}

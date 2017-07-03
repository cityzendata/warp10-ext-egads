package io.warp10.script.ext.egads;

import java.util.Map;
import java.util.Properties;

import com.yahoo.egads.models.adm.AdaptiveKernelDensityChangePointDetector;
import com.yahoo.egads.models.adm.DBScanModel;
import com.yahoo.egads.models.adm.ExtremeLowDensityModel;
import com.yahoo.egads.models.adm.KSigmaModel;
import com.yahoo.egads.models.adm.NaiveModel;
import com.yahoo.egads.models.adm.SimpleThresholdModel;
import com.yahoo.egads.models.tsmm.AutoForecastModel;
import com.yahoo.egads.models.tsmm.DoubleExponentialSmoothingModel;
import com.yahoo.egads.models.tsmm.MovingAverageModel;
import com.yahoo.egads.models.tsmm.MultipleLinearRegressionModel;
import com.yahoo.egads.models.tsmm.NaiveForecastingModel;
import com.yahoo.egads.models.tsmm.NullModel;
import com.yahoo.egads.models.tsmm.OlympicModel;
import com.yahoo.egads.models.tsmm.OlympicModel2;
import com.yahoo.egads.models.tsmm.PolynomialRegressionModel;
import com.yahoo.egads.models.tsmm.RegressionModel;
import com.yahoo.egads.models.tsmm.SimpleExponentialSmoothingModel;
import com.yahoo.egads.models.tsmm.SpectralSmoother;
import com.yahoo.egads.models.tsmm.TripleExponentialSmoothingModel;

import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

/**
 * Push onto the stack an EGADS model that can be trained
 */
public class EGADSMODEL extends NamedWarpScriptFunction implements WarpScriptStackFunction {
  public EGADSMODEL(String name) {
    super(name);
  }

  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {
    //
    // Extract the model name
    //
    
    String modelName = stack.pop().toString();
    
    //
    // Extract a map of parameters
    //
    
    Object top = stack.pop();
    
    if (!(top instanceof Map)) {
      throw new WarpScriptException(getName() + " expects a map of parameters below the model name.");
    }
    
    Map<Object,Object> params = (Map<Object,Object>) top;

    Properties props = EGADSUtils.toProperties(params);
    
    SnapshotableModel model = null;
    
    //
    // Time Series Models
    //
    
    if ("AutoForecastModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new AutoForecastModel(props));
    } else if ("DoubleExponentialSmoothingModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new DoubleExponentialSmoothingModel(props));
    } else if ("MovingAverageModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new MovingAverageModel(props));
    } else if ("MultipleLinearRegressionModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new MultipleLinearRegressionModel(props));
    } else if ("NaiveForecastingModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new NaiveForecastingModel(props));
    } else if ("NullModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new NullModel(props));
    } else if ("OlympicModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new OlympicModel(props));
    } else if ("OlympicModel2".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new OlympicModel2(props));
    } else if ("PolynomialRegressionModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new PolynomialRegressionModel(props));
    } else if ("RegressionModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new RegressionModel(props));
    } else if ("SimpleExponentialSmoothingModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new SimpleExponentialSmoothingModel(props));
    } else if ("SpectralSmoother".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new SpectralSmoother(props));
    } else if ("TripleExponentialSmoothingModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new TripleExponentialSmoothingModel(props));
    } else if ("WeightedMovingAverageModel".equalsIgnoreCase(modelName)) {
    }
    
    //
    // Anomaly Detection Models
    //
    
    else if ("AdaptiveKernelDensityChangePointDetector".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new AdaptiveKernelDensityChangePointDetector(props));
    } else if ("DBScanModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new DBScanModel(props));
    } else if ("ExtremeLowDensityModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new ExtremeLowDensityModel(props));
    } else if ("KSigmaModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new KSigmaModel(props));
    } else if ("NaiveModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new NaiveModel(props));
    } else if ("SimpleThresholdModel".equalsIgnoreCase(modelName)) {
      model = new SnapshotableModel(new SimpleThresholdModel(props));
    }
          
    if (null != model) {
      stack.push(model);
    } else {
      throw new WarpScriptException(getName() + " unknown model '" + modelName + "'.");
    }
    
    return stack;
  }
}

package io.warp10.script.ext.egads;

import java.util.HashMap;
import java.util.Map;

import io.warp10.warp.sdk.WarpScriptExtension;

public class EGADSWarpScriptExtension extends WarpScriptExtension {
  
  public static final String TOEGADSMODEL = "->EGADSMODEL";
  
  private static final Map<String,Object> functions;
  
  static {
    functions = new HashMap<String,Object>();
    
    functions.put("EGADSMODEL", new EGADSMODEL("EGADSMODEL"));
    functions.put(TOEGADSMODEL, new TOEGADSMODEL(TOEGADSMODEL));
    functions.put("EGADSMODEL->", new EGADSMODELTO("EGADSMODEL->"));
    functions.put("EGADSTRAIN", new EGADSTRAIN("EGADSTRAIN")); // Train a model
    functions.put("EGADSUPDATE", new EGADSUPDATE("EGADSUPDATE")); // Update an updatable model
    functions.put("EGADSRESET", new EGADSRESET("EGADSRESET")); // Reset a model
    functions.put("EGAGSPREDICT", new EGADSPREDICT("EGADSPREDICT")); // Predict a time series   

    functions.put("EGAGSDETECT", new EGADSDETECT("EGADSDETECT")); // Detect anomalies
    functions.put("EGAGSTUNE", new EGADSTUNE("EGADSTUNE")); // Tune anomaly detection model
  }
  
  @Override
  public Map<String, Object> getFunctions() {
    return functions;
  }
}

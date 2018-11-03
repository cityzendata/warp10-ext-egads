//
//   Copyright 2018  SenX S.A.S.
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//
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

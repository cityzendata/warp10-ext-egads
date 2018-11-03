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

import com.yahoo.egads.data.Anomaly.IntervalSequence;
import com.yahoo.egads.data.TimeSeries.DataSequence;
import com.yahoo.egads.models.adm.AnomalyDetectionModel;

import io.warp10.continuum.gts.GeoTimeSerie;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

/**
 * Use model to detect anomalies in a GTS
 */
public class EGADSDETECT extends NamedWarpScriptFunction implements WarpScriptStackFunction {
  public EGADSDETECT(String name) {
    super(name);
  }

  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {
    //
    // Extract the GTS
    //
    
    Object top = stack.pop();
    
    if (!(top instanceof GeoTimeSerie)) {
      throw new WarpScriptException(getName() + " expects an expected Geo Time Series on top of the stack.");
    }
    
    GeoTimeSerie expectedGTS = (GeoTimeSerie) top;
    
    top = stack.pop();
    
    if (!(top instanceof GeoTimeSerie)) {
      throw new WarpScriptException(getName() + " expects an observed Geo Time Series below the expewcted one.");
    }
    
    GeoTimeSerie observedGTS = (GeoTimeSerie) top;
    
    if (!(top instanceof SnapshotableModel)) {
      throw new WarpScriptException(getName() + " expects an EGADS model below the observed and expected Geo Time Series.");
    }
    
    SnapshotableModel model = (SnapshotableModel) top;
    
    if (model.getModel() instanceof AnomalyDetectionModel) {
      try {
        DataSequence observed = EGADSUtils.fromGTS(observedGTS);
        DataSequence expected = EGADSUtils.fromGTS(expectedGTS);
        IntervalSequence intervals = ((AnomalyDetectionModel) model.getModel()).detect(observed, expected);
        
        stack.push(intervals);
      } catch (Exception e) {
        throw new WarpScriptException(getName() + " encountered an exception when training the model.");
      }
    } else {
      throw new WarpScriptException(getName() + " invalid model.");
    }
    
    return stack;
  }
}

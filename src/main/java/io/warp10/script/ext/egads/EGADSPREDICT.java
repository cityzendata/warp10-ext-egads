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

import com.yahoo.egads.data.TimeSeries.DataSequence;
import com.yahoo.egads.data.TimeSeries.Entry;
import com.yahoo.egads.models.tsmm.TimeSeriesModel;

import io.warp10.continuum.gts.GTSHelper;
import io.warp10.continuum.gts.GeoTimeSerie;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

/**
 * Use trained model to predict a GTS
 */
public class EGADSPREDICT extends NamedWarpScriptFunction implements WarpScriptStackFunction {
  public EGADSPREDICT(String name) {
    super(name);
  }

  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {
    //
    // Extract the GTS
    //
    
    Object top = stack.pop();
    
    if (!(top instanceof GeoTimeSerie)) {
      throw new WarpScriptException(getName() + " expects a Geo Time Series on top of the stack.");
    }
    
    GeoTimeSerie gts = (GeoTimeSerie) top;
    
    top = stack.pop();
    
    if (!(top instanceof SnapshotableModel)) {
      throw new WarpScriptException(getName() + " expects an EGADS model below the Geo Time Series.");
    }
    
    SnapshotableModel model = (SnapshotableModel) top;
    
    if (model.getModel() instanceof TimeSeriesModel) {
      try {
        DataSequence sequence = EGADSUtils.fromGTS(gts);
        ((TimeSeriesModel) model.getModel()).predict(sequence);
        
        // Create resulting GTS
        GeoTimeSerie predicted = gts.cloneEmpty(sequence.size());
        
        for (int i = 0; i < sequence.size(); i++) {
          Entry entry = sequence.get(i);
          GTSHelper.setValue(predicted, entry.time, entry.value);
        }

        stack.push(predicted);
      } catch (Exception e) {
        throw new WarpScriptException(getName() + " encountered an exception when training the model.");
      }
    }
    
    return stack;
  }
}

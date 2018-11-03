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

import com.yahoo.egads.models.adm.AnomalyDetectionModel;
import com.yahoo.egads.models.tsmm.TimeSeriesModel;

import io.warp10.continuum.gts.GeoTimeSerie;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

/**
 * Reset an EGADS model
 */
public class EGADSRESET extends NamedWarpScriptFunction implements WarpScriptStackFunction {
  public EGADSRESET(String name) {
    super(name);
  }

  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {
    Object top = stack.peek();
    
    if (!(top instanceof SnapshotableModel)) {
      throw new WarpScriptException(getName() + " expects an EGADS model on top of the stack.");
    }
    
    SnapshotableModel model = (SnapshotableModel) top;
    
    if (model.getModel() instanceof TimeSeriesModel) {
      ((TimeSeriesModel) model.getModel()).reset();
    } else if (model.getModel() instanceof AnomalyDetectionModel) {
      ((AnomalyDetectionModel) model.getModel()).reset();
    }
    
    return stack;
  }
}

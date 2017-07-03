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

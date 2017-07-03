package io.warp10.script.ext.egads;

import com.yahoo.egads.models.tsmm.TimeSeriesModel;

import io.warp10.continuum.gts.GeoTimeSerie;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

/**
 * Train an EGADS model with data from a GTS
 */
public class EGADSTRAIN extends NamedWarpScriptFunction implements WarpScriptStackFunction {
  public EGADSTRAIN(String name) {
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
    
    top = stack.peek();
    
    if (!(top instanceof SnapshotableModel)) {
      throw new WarpScriptException(getName() + " expects an EGADS model below the Geo Time Series.");
    }
    
    SnapshotableModel model = (SnapshotableModel) top;
    
    if (model.getModel() instanceof TimeSeriesModel) {
      try {
        ((TimeSeriesModel) model.getModel()).train(EGADSUtils.fromGTS(gts));
      } catch (Exception e) {
        throw new WarpScriptException(getName() + " encountered an exception when training the model.");
      }
    } else {
      throw new WarpScriptException(getName() + " invalid model, cannot train.");
    }
    
    return stack;
  }
}

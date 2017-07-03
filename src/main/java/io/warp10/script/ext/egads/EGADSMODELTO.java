package io.warp10.script.ext.egads;

import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

public class EGADSMODELTO extends NamedWarpScriptFunction implements WarpScriptStackFunction {
  public EGADSMODELTO(String name) {
    super(name);
  }

  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {
    Object top = stack.pop();
    
    if (!(top instanceof SnapshotableModel)) {
      throw new WarpScriptException(getName() + " expects an EGADS model on top of the stack.");
    }
    
    try {
      stack.push(EGADSUtils.encodeModel((SnapshotableModel) top));
    } catch (Exception e) {
      throw new WarpScriptException(getName() + " encountered an exception while encoding the model.");
    }
    
    return stack;
  }
  
}

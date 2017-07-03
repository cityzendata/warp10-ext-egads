package io.warp10.script.ext.egads;

import com.yahoo.egads.data.Model;

import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

public class TOEGADSMODEL extends NamedWarpScriptFunction implements WarpScriptStackFunction {
  public TOEGADSMODEL(String name) {
    super(name);
  }

  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {
    Object top = stack.pop();
    
    if (!(top instanceof String)) {
      throw new WarpScriptException(getName() + " expects an encoded EGADS model on top of the stack.");
    }
    
    try {
      stack.push(EGADSUtils.decodeModel(top.toString()));
    } catch (Exception e) {
      throw new WarpScriptException(getName() + " encountered an exception while decoding the model.");
    }
    
    return stack;
  }
  
}

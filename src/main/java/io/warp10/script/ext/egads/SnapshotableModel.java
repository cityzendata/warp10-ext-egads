package io.warp10.script.ext.egads;

import java.io.Serializable;

import com.yahoo.egads.data.Model;

import io.warp10.script.functions.SNAPSHOT.Snapshotable;

public class SnapshotableModel implements Snapshotable, Serializable {
  
  private final Model model;
  
  public SnapshotableModel(Model model) {
    this.model = model;
  }
  
  @Override
  public String snapshot() {
    StringBuilder sb = new StringBuilder();
    
    sb.append("'");
    sb.append("' ");
    sb.append(EGADSWarpScriptExtension.TOEGADSMODEL);

    return sb.toString();    
  }
  
  public Model getModel() {
    return this.model;
  }
}

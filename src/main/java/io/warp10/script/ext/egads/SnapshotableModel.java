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

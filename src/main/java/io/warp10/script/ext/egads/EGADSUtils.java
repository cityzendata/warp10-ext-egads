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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.yahoo.egads.data.TimeSeries.DataSequence;

import io.warp10.continuum.gts.GTSHelper;
import io.warp10.continuum.gts.GeoTimeSerie;
import io.warp10.crypto.OrderPreservingBase64;

public class EGADSUtils {
  public static DataSequence fromGTS(GeoTimeSerie gts) throws Exception {
    // Sort the GTS
    GTSHelper.sort(gts);
    
    Long[] ticks = new Long[GTSHelper.nvalues(gts)];
    Float[] values = new Float[GTSHelper.nvalues(gts)];
    
    Long lasttick = null;
    
    int idx = 0;
    
    for (int i = 0; i < ticks.length; i++) {
      long tick = GTSHelper.tickAtIndex(gts, i);
      Object value = GTSHelper.valueAtIndex(gts, i);
      
      if (0 == lasttick.compareTo(tick)) {
        continue;
      }
      
      lasttick = tick;
      
      if (value instanceof Number) {
        ticks[idx] = tick;
        values[idx++] = ((Number) value).floatValue();
      }
    }
    
    if (idx < ticks.length) {
      ticks = Arrays.copyOf(ticks, idx);
      values = Arrays.copyOf(values, idx);
    }
    
    return new DataSequence(ticks, values);
  }
  
  public static String encodeModel(SnapshotableModel model) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(baos);
    
    out.writeObject(model);
    
    return new String(OrderPreservingBase64.encode(baos.toByteArray()), Charsets.US_ASCII);
  }
  
  public static SnapshotableModel decodeModel(String encoded) throws Exception {
    byte[] raw = OrderPreservingBase64.decode(encoded.getBytes(Charsets.US_ASCII));
    ByteArrayInputStream bais = new ByteArrayInputStream(raw);
    ObjectInputStream in = new ObjectInputStream(bais);
    return (SnapshotableModel) in.readObject();
  }
  
  /**
   * Convert a map from WarpScript into a Properties instance
   */
  public static Properties toProperties(Map<Object,Object> params) {    
    Properties props = new Properties();
    
    for (Entry<Object,Object> entry: params.entrySet()) {
      props.setProperty(entry.getKey().toString(), entry.getValue().toString());
    }
    
    return props;
  }
}

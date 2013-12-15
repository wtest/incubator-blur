/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.blur.trace;

import java.io.IOException;

import org.apache.blur.BlurConfiguration;
import org.apache.blur.log.Log;
import org.apache.blur.log.LogFactory;

public class LogTraceStorage extends BaseTraceStorage {

  private static final Log LOG = LogFactory.getLog(LogTraceStorage.class);

  public LogTraceStorage(BlurConfiguration configuration) {
    super(configuration);
  }

  @Override
  public void store(TraceCollector collector) {
    String json = collector.toJson();
    LOG.info("Trace Complete [{0}]", json);
  }

  @Override
  public void close() throws IOException {
    
  }

}
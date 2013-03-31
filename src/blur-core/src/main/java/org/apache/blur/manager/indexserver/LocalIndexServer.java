package org.apache.blur.manager.indexserver;

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
import static org.apache.blur.lucene.LuceneVersionConstant.LUCENE_VERSION;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.blur.analysis.BlurAnalyzer;
import org.apache.blur.log.Log;
import org.apache.blur.log.LogFactory;
import org.apache.blur.lucene.search.FairSimilarity;
import org.apache.blur.lucene.store.refcounter.DirectoryReferenceFileGC;
import org.apache.blur.lucene.store.refcounter.IndexInputCloser;
import org.apache.blur.manager.writer.BlurIndex;
import org.apache.blur.manager.writer.BlurNRTIndex;
import org.apache.blur.manager.writer.SharedMergeScheduler;
import org.apache.blur.server.ShardContext;
import org.apache.blur.server.TableContext;
import org.apache.blur.thrift.generated.TableDescriptor;
import org.apache.blur.utils.BlurConstants;
import org.apache.blur.utils.BlurUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;

import com.google.common.io.Closer;

public class LocalIndexServer extends AbstractIndexServer {

  private final static Log LOG = LogFactory.getLog(LocalIndexServer.class);

  private final Map<String, Map<String, BlurIndex>> _readersMap = new ConcurrentHashMap<String, Map<String, BlurIndex>>();
  private final SharedMergeScheduler _mergeScheduler;
  private final IndexInputCloser _indexInputCloser;
  private final DirectoryReferenceFileGC _gc;
  private final ExecutorService _searchExecutor;
  private final TableContext _tableContext;
  private final Closer _closer;

  public LocalIndexServer(TableDescriptor tableDescriptor) throws IOException {
    _closer = Closer.create();
    _tableContext = TableContext.create(tableDescriptor);
    _mergeScheduler = new SharedMergeScheduler();
    _indexInputCloser = new IndexInputCloser();
    _indexInputCloser.init();
    _gc = new DirectoryReferenceFileGC();
    _gc.init();
    _searchExecutor = Executors.newCachedThreadPool();
    _closer.register(_mergeScheduler);
    _closer.register(_indexInputCloser);
    _closer.register(_gc);
    _closer.register(new CloseableExecutorService(_searchExecutor));

    getIndexes(_tableContext.getTable());
  }

  @Override
  public void close() {
    try {
      _closer.close();
    } catch (IOException e) {
      LOG.error("Unknown error", e);
    }
    for (String table : _readersMap.keySet()) {
      close(_readersMap.get(table));
    }
  }

  @Override
  public BlurAnalyzer getAnalyzer(String table) {
    return new BlurAnalyzer(new StandardAnalyzer(LUCENE_VERSION, new CharArraySet(LUCENE_VERSION,
        new HashSet<String>(), false)));
  }

  @Override
  public SortedSet<String> getShardListCurrentServerOnly(String table) throws IOException {
    Map<String, BlurIndex> tableMap = _readersMap.get(table);
    Set<String> shardsSet;
    if (tableMap == null) {
      shardsSet = getIndexes(table).keySet();
    } else {
      shardsSet = tableMap.keySet();
    }
    return new TreeSet<String>(shardsSet);
  }

  @Override
  public Map<String, BlurIndex> getIndexes(String table) throws IOException {
    Map<String, BlurIndex> tableMap = _readersMap.get(table);
    if (tableMap == null) {
      tableMap = openFromDisk();
      _readersMap.put(table, tableMap);
    }
    return tableMap;
  }

  @Override
  public Similarity getSimilarity(String table) {
    return new FairSimilarity();
  }

  private void close(Map<String, BlurIndex> map) {
    for (BlurIndex index : map.values()) {
      try {
        index.close();
      } catch (Exception e) {
        LOG.error("Error while trying to close index.", e);
      }
    }
  }

  private Map<String, BlurIndex> openFromDisk() throws IOException {
    String table = _tableContext.getDescriptor().getName();
    Path tablePath = _tableContext.getTablePath();
    File tableFile = new File(tablePath.toUri());
    if (tableFile.isDirectory()) {
      Map<String, BlurIndex> shards = new ConcurrentHashMap<String, BlurIndex>();
      int shardCount = _tableContext.getDescriptor().getShardCount();
      for (int i = 0; i < shardCount; i++) {
        String shardName = BlurUtil.getShardName(BlurConstants.SHARD_PREFIX, i);
        File file = new File(tableFile, shardName);
        file.mkdirs();
        MMapDirectory directory = new MMapDirectory(file);
        if (!DirectoryReader.indexExists(directory)) {
          new IndexWriter(directory, new IndexWriterConfig(LUCENE_VERSION, new KeywordAnalyzer())).close();
        }
        shards.put(shardName, openIndex(table, shardName, directory));
      }
      return shards;
    }
    throw new IOException("Table [" + table + "] not found.");
  }

  private BlurIndex openIndex(String table, String shard, Directory dir) throws CorruptIndexException, IOException {
    ShardContext shardContext = ShardContext.create(_tableContext, shard);
    BlurNRTIndex index = new BlurNRTIndex(shardContext, _mergeScheduler, _indexInputCloser, dir, _gc, _searchExecutor);
    return index;
  }

  @Override
  public TABLE_STATUS getTableStatus(String table) {
    return TABLE_STATUS.ENABLED;
  }

  @Override
  public List<String> getShardList(String table) {
    try {
      List<String> result = new ArrayList<String>();
      Path tablePath = _tableContext.getTablePath();
      File tableFile = new File(new File(tablePath.toUri()), table);
      if (tableFile.isDirectory()) {
        for (File f : tableFile.listFiles()) {
          if (f.isDirectory()) {
            result.add(f.getName());
          }
        }
      }
      return result;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getNodeName() {
    return "localhost";
  }

  @Override
  public String getTableUri(String table) {
    return _tableContext.getTablePath().toUri().toString();
  }

  @Override
  public int getShardCount(String table) {
    return _tableContext.getDescriptor().getShardCount();
  }

  @Override
  public long getTableSize(String table) throws IOException {
    try {
      File file = new File(new URI(getTableUri(table)));
      return getFolderSize(file);
    } catch (URISyntaxException e) {
      throw new IOException("bad URI", e);
    }
  }

  private long getFolderSize(File file) {
    long size = 0;
    if (file.isDirectory()) {
      for (File sub : file.listFiles()) {
        size += getFolderSize(sub);
      }
    } else {
      size += file.length();
    }
    return size;
  }

  @Override
  public CompressionCodec getCompressionCodec(String table) {
    throw new RuntimeException("Should not be used.");
  }

  @Override
  public int getCompressionBlockSize(String table) {
    throw new RuntimeException("Should not be used.");
  }
}
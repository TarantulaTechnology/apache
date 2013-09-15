/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.cassandra.db.compaction;

import java.util.Collection;

import org.apache.cassandra.db.ColumnFamilyStore;
import org.apache.cassandra.io.sstable.SSTableReader;
import org.apache.cassandra.io.sstable.SSTableWriter;

public class LeveledCompactionTask extends CompactionTask
{
    private final int level;
    private final int sstableSizeInMB;

    public LeveledCompactionTask(ColumnFamilyStore cfs, Collection<SSTableReader> sstables, int level, final int gcBefore, int sstableSizeInMB)
    {
        super(cfs, sstables, gcBefore);
        this.level = level;
        this.sstableSizeInMB = sstableSizeInMB;
    }

    @Override
    protected boolean newSSTableSegmentThresholdReached(SSTableWriter writer)
    {
        return writer.getOnDiskFilePointer() > sstableSizeInMB * 1024L * 1024L;
    }

    @Override
    protected boolean partialCompactionsAcceptable()
    {
        return false;
    }

    protected int getLevel()
    {
        return level;
    }
}

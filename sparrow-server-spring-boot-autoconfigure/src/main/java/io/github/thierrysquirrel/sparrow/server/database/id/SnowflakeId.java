/**
 * Copyright 2026/6/4 ThierrySquirrel
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io.github.thierrysquirrel.sparrow.server.database.id;

import io.github.thierrysquirrel.sparrow.server.database.id.constant.SnowflakeConstant;
import io.github.thierrysquirrel.sparrow.server.database.id.domain.SnowflakeDomain;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName: SnowflakeId
 * Description:
 * Date:2026/6/4
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class SnowflakeId {

    private static final Lock LOCK = new ReentrantLock();

    private static long LAST_TIMESTAMP = SnowflakeConstant.DEFAULT_LAST_TIMESTAMP;

    private static final long PAST_TIME = SnowflakeConstant.DEFAULT_PAST_TIME;

    public static long nextId() {
        int threadId = ThreadPoolId.getId();
        if (threadId >= SnowflakeConstant.MAX_THREAD_ID || threadId < 0) {
            throw new RuntimeException("threadId Error");
        }

        long timestamp = System.currentTimeMillis();
        if (timestamp < LAST_TIMESTAMP) {
            throw new RuntimeException("time Error");
        }

        AtomicInteger sequence = ThreadPoolId.getSequence();
        int incremented = sequence.incrementAndGet();
        if (timestamp == LAST_TIMESTAMP) {
            if (incremented >= SnowflakeConstant.MAX_SEQUENCE) {
                SnowflakeDomain snowflakeDomain = checkSequence(timestamp, incremented, sequence);
                timestamp = snowflakeDomain.getTimestamp();
                incremented = snowflakeDomain.getSequence();
            }
        }
        LAST_TIMESTAMP = timestamp;
        long thisTime = timestamp - PAST_TIME;
        return thisTime << SnowflakeConstant.THIS_TIME_LEFT |
                threadId << SnowflakeConstant.THREAD_ID_LEFT |
                incremented;
    }

    private static SnowflakeDomain checkSequence(long timestamp, int incremented, AtomicInteger sequence) {
        LOCK.lock();
        if (incremented >= SnowflakeConstant.MAX_SEQUENCE) {
            timestamp = nextMillis(LAST_TIMESTAMP);
            sequence.compareAndSet(incremented, 0);
            incremented = sequence.incrementAndGet();
        }
        LOCK.unlock();
        return new SnowflakeDomain(timestamp, incremented);
    }

    private static long nextMillis(long lastTimestamp) {

        long timestamp = System.currentTimeMillis();

        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}

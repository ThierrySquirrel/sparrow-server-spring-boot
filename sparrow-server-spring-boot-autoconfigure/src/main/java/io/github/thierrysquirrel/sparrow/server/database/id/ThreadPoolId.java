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

import io.github.thierrysquirrel.jellyfish.concurrency.map.hash.ConcurrencyHashMap;
import io.github.thierrysquirrel.jellyfish.container.JellyfishContainer;
import io.github.thierrysquirrel.jellyfish.thread.local.map.ThreadLocalMap;
import io.github.thierrysquirrel.sparrow.server.core.constant.MapConstant;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: ThreadPoolId
 * Description:
 * Date:2026/6/4
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class ThreadPoolId {
    private ThreadPoolId() {
    }

    private static final ConcurrencyHashMap<Long, Integer> THREAD_POOL_ID = new ConcurrencyHashMap<>(MapConstant.DEFAULT_MAP_SIZE);
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    private static final ThreadLocalMap<AtomicInteger> SEQUENCE = new ThreadLocalMap<>(MapConstant.DEFAULT_MAP_SIZE);

    public static int getId() {
        long id = Thread.currentThread().threadId();
        return THREAD_POOL_ID.getIfAbsent(id, key -> ATOMIC_INTEGER.incrementAndGet());
    }

    public static AtomicInteger getSequence() {
        JellyfishContainer<AtomicInteger> container = SEQUENCE.get();
        if (container.isEmpty()) {
            SEQUENCE.set(new AtomicInteger(0));
            container = SEQUENCE.get();
        }
        return container.getValue();
    }
}

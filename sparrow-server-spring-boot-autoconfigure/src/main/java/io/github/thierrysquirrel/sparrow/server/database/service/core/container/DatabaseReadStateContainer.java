/**
 * Copyright 2024/8/9 ThierrySquirrel
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
package io.github.thierrysquirrel.sparrow.server.database.service.core.container;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName: DatabaseReadStateContainer
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class DatabaseReadStateContainer {
    private static final Map<String, AtomicBoolean> DATABASE_READ_STATE = Maps.newConcurrentMap();

    private DatabaseReadStateContainer() {
    }

    public static boolean tryDatabaseRead(String topic) {
        AtomicBoolean tryRead = DATABASE_READ_STATE.computeIfAbsent(topic, key -> new AtomicBoolean(Boolean.TRUE));
        return tryRead.compareAndSet(Boolean.TRUE, Boolean.FALSE);
    }

    public static void tryCloseDatabaseRead(String topic) {
        DATABASE_READ_STATE.get(topic).compareAndSet(Boolean.FALSE, Boolean.TRUE);
    }
}

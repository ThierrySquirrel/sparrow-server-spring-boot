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
package io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.container;

import io.github.thierrysquirrel.jellyfish.concurrency.deque.array.ConcurrencyArrayDeque;
import io.github.thierrysquirrel.jellyfish.container.JellyfishContainer;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.builder.QueryBuilder;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.container.constant.MessageIdQueryConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: MessageIdQuery
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class MessageIdQuery {
    private static final Map<String, ConcurrencyArrayDeque<Long>> ID_QUERY = new ConcurrentHashMap<>();
    private static final Map<String, Long> ID_QUERY_PUT_TIME = new ConcurrentHashMap<>();

    private MessageIdQuery() {
    }

    public static List<Long> putId(String url, Long id) {
        ConcurrencyArrayDeque<Long> query = ID_QUERY.computeIfAbsent(url, key -> QueryBuilder.builderQueue());
        query.pushBack(id);
        ID_QUERY_PUT_TIME.put(url, System.currentTimeMillis());
        if (query.getSize() >= MessageIdQueryConstant.POLL_NUMBER) {
            return pollIdList(url);
        }
        return Collections.emptyList();
    }

    public static List<Long> pollTimeoutIdList(String url) {
        ConcurrencyArrayDeque<Long> idQuery = ID_QUERY.get(url);
        Long putTime = ID_QUERY_PUT_TIME.get(url);
        if (idQuery == null || putTime == null) {
            return Collections.emptyList();
        }
        if (System.currentTimeMillis() - putTime > MessageIdQueryConstant.TIMEOUT) {
            return pollIdList(url);
        }
        return Collections.emptyList();
    }

    private static List<Long> pollIdList(String url) {
        ConcurrencyArrayDeque<Long> idQuery = ID_QUERY.get(url);
        List<Long> idList = new ArrayList<>();
        for (int i = 0; i < MessageIdQueryConstant.POLL_NUMBER; i++) {
            JellyfishContainer<Long> tryPopBack = idQuery.tryPopBack();
            if (tryPopBack.isEmpty()) {
                continue;
            }
            idList.add(tryPopBack.getValue());
        }
        return idList;
    }
}

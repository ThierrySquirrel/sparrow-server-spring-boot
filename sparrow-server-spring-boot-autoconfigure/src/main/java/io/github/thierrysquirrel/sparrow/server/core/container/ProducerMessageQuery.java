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
package io.github.thierrysquirrel.sparrow.server.core.container;

import io.github.thierrysquirrel.jellyfish.concurrency.deque.array.ConcurrencyArrayDeque;
import io.github.thierrysquirrel.jellyfish.container.JellyfishContainer;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.builder.QueryBuilder;
import io.github.thierrysquirrel.sparrow.server.core.container.constant.ProducerMessageQueryConstant;
import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.SparrowMessageEntity;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: ProducerMessageQuery
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class ProducerMessageQuery {
    private static final Map<String, ConcurrencyArrayDeque<SparrowMessageEntity>> PRODUCER_MESSAGE = new ConcurrentHashMap<>();
    private static final Map<String, Long> PRODUCER_MESSAGE_PUT_TIME = new ConcurrentHashMap<>();

    private ProducerMessageQuery() {
    }

    public static List<SparrowMessageEntity> putMessage(String topic, SparrowMessageEntity sparrowMessageEntity) {
        ConcurrencyArrayDeque<SparrowMessageEntity> messageQuery = PRODUCER_MESSAGE.computeIfAbsent(topic, key -> QueryBuilder.builderQueue());
        messageQuery.pushBack(sparrowMessageEntity);

        PRODUCER_MESSAGE_PUT_TIME.put(topic, System.currentTimeMillis());
        if (messageQuery.getSize() >= ProducerMessageQueryConstant.POLL_NUMBER) {
            return pollMessage(topic);
        }
        return Collections.emptyList();
    }

    public static Map<String, List<SparrowMessageEntity>> pollTimeoutMessage() {
        Map<String, List<SparrowMessageEntity>> timeoutMessage = new ConcurrentHashMap<>();
        long thisTime = System.currentTimeMillis();
        for (Map.Entry<String, Long> putTimeEntity : PRODUCER_MESSAGE_PUT_TIME.entrySet()) {
            Long putTime = putTimeEntity.getValue();
            if ((thisTime - putTime) > ProducerMessageQueryConstant.TIMEOUT) {
                String topic = putTimeEntity.getKey();
                List<SparrowMessageEntity> messageList = pollMessage(topic);
                if (ObjectUtils.isEmpty(messageList)) {
                    continue;
                }
                timeoutMessage.put(topic, messageList);
            }
        }
        return timeoutMessage;
    }

    private static List<SparrowMessageEntity> pollMessage(String topic) {
        ConcurrencyArrayDeque<SparrowMessageEntity> messageQuery = PRODUCER_MESSAGE.get(topic);
        List<SparrowMessageEntity> messageList = new ArrayList<>();
        for (int i = 0; i < ProducerMessageQueryConstant.POLL_NUMBER; i++) {
            JellyfishContainer<SparrowMessageEntity> message = messageQuery.tryPopBack();
            if (message.isEmpty()) {
                break;
            }
            messageList.add(message.getValue());
        }
        return messageList;
    }
}

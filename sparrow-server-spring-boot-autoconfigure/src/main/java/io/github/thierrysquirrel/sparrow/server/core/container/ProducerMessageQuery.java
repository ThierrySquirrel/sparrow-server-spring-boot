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

import io.github.thierrysquirrel.sparrow.server.common.netty.builder.QueryBuilder;
import io.github.thierrysquirrel.sparrow.server.core.container.constant.ProducerMessageQueryConstant;
import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.SparrowMessageEntity;
import com.google.common.collect.Maps;
import org.jctools.queues.MpmcUnboundedXaddArrayQueue;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProducerMessageQuery
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class ProducerMessageQuery {
    private static final Map<String, MpmcUnboundedXaddArrayQueue<SparrowMessageEntity>> PRODUCER_MESSAGE = Maps.newConcurrentMap();
    private static final Map<String, Long> PRODUCER_MESSAGE_PUT_TIME = Maps.newConcurrentMap();

    private ProducerMessageQuery() {
    }

    public static List<SparrowMessageEntity> putMessage(String topic, SparrowMessageEntity sparrowMessageEntity) {
        MpmcUnboundedXaddArrayQueue<SparrowMessageEntity> messageQuery = PRODUCER_MESSAGE.computeIfAbsent(topic, key -> QueryBuilder.builderUnboundedQueue());
        messageQuery.offer(sparrowMessageEntity);

        PRODUCER_MESSAGE_PUT_TIME.put(topic, System.currentTimeMillis());
        if (messageQuery.size() >= ProducerMessageQueryConstant.POLL_NUMBER) {
            return pollMessage(topic);
        }
        return Collections.emptyList();
    }

    public static Map<String, List<SparrowMessageEntity>> pollTimeoutMessage() {
        Map<String, List<SparrowMessageEntity>> timeoutMessage = Maps.newConcurrentMap();
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
        MpmcUnboundedXaddArrayQueue<SparrowMessageEntity> messageQuery = PRODUCER_MESSAGE.get(topic);
        List<SparrowMessageEntity> messageList = new ArrayList<>();
        for (int i = 0; i < ProducerMessageQueryConstant.POLL_NUMBER; i++) {
            SparrowMessageEntity message = messageQuery.poll();
            if (ObjectUtils.isEmpty(message)) {
                break;
            }
            messageList.add(message);
        }
        return messageList;
    }
}

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

import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowMessage;
import io.github.thierrysquirrel.sparrow.server.core.container.constant.ConsumerMessageQueryConstant;
import com.google.common.collect.Maps;
import org.jctools.queues.MpmcArrayQueue;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ConsumerMessageQuery
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class ConsumerMessageQuery {
    private static final Map<String, MpmcArrayQueue<List<SparrowMessage>>> CONSUMER_MESSAGE = Maps.newConcurrentMap();

    private ConsumerMessageQuery() {
    }

    public static void putMessage(String topic, List<SparrowMessage> sparrowMessageList) {
        CONSUMER_MESSAGE.computeIfAbsent(topic, key -> new MpmcArrayQueue<>(ConsumerMessageQueryConstant.CAPACITY))
                .add(sparrowMessageList);
    }

    public static List<SparrowMessage> getMessage(String topic) {
        return CONSUMER_MESSAGE.computeIfAbsent(topic, key -> new MpmcArrayQueue<>(ConsumerMessageQueryConstant.CAPACITY))
                .poll();
    }
}

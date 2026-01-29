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
package io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.thread.execution;

import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.factory.execution.SparrowConsumerFactoryExecution;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.thread.AbstractConsumerThread;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.listener.MessageListener;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName: ConsumerThreadExecution
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class ConsumerThreadExecution extends AbstractConsumerThread {
    private static final Logger logger = Logger.getLogger(ConsumerThreadExecution.class.getName());

    public ConsumerThreadExecution(MessageListener messageListener, String url, String topic, SparrowMessage sparrowMessage) {
        super(messageListener, url, topic, sparrowMessage);
    }

    @Override
    protected void consumer(MessageListener messageListener, String url, String topic, SparrowMessage sparrowMessage) {
        try {
            SparrowConsumerFactoryExecution.consumer(messageListener, url, topic, sparrowMessage);
        } catch (Exception e) {
            String lpgMsg = "consumerError";
            logger.log(Level.WARNING, lpgMsg, e);
        }
    }
}

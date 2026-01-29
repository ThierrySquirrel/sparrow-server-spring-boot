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
package io.github.thierrysquirrel.sparrow.server.common.hummingbird.producer.init.client;

import io.github.thierrysquirrel.hummingbird.core.facade.SocketChannelFacade;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowRequestContext;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.builder.SparrowRequestContextBuilder;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.producer.init.container.SparrowProducerInitConstant;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.producer.listener.ProducerFail;

/**
 * ClassName: SparrowProducer
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowProducer {
    private final ProducerFail producerFail;
    private final String url;
    private final String topic;

    public SparrowProducer(ProducerFail producerFail, String url, String topic) {
        this.producerFail = producerFail;
        this.url = url;
        this.topic = topic;
    }

    public void sendMessage(byte[] message) {
        try {
            SocketChannelFacade<SparrowRequestContext> channel = SparrowProducerInitConstant.getSparrowProducerInit(url).init();
            SparrowRequestContext sparrowRequestContext = SparrowRequestContextBuilder.builderPostMessageRequest(topic, message);
            channel.sendMessage(sparrowRequestContext);
        } catch (Exception e) {
            producerFail.fail(topic, message, e);
        }

    }
}

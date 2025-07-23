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
package io.github.thierrysquirrel.sparrow.server.common.netty.consumer.init.client.core.factory;

import io.github.thierrysquirrel.sparrow.server.common.netty.consumer.init.client.core.container.MessageNumberContainer;
import io.github.thierrysquirrel.sparrow.server.common.netty.consumer.init.constant.SparrowConsumerInitConstant;
import io.github.thierrysquirrel.sparrow.server.common.netty.consumer.listener.MessageListener;
import io.github.thierrysquirrel.sparrow.server.common.netty.consumer.listener.constant.ConsumerState;
import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowMessage;
import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowRequestContext;
import io.github.thierrysquirrel.sparrow.server.common.netty.domain.builder.SparrowRequestContextBuilder;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * ClassName: SparrowConsumerFactory
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
@Slf4j
public class SparrowConsumerFactory {
    private SparrowConsumerFactory() {
    }

    public static ConsumerState consumer(MessageListener messageListener, String topic, SparrowMessage sparrowMessage) {
        ConsumerState consumer = messageListener.consumer(sparrowMessage.getMessage());
        MessageNumberContainer.decrement(topic);
        return consumer;
    }

    public static void confirmConsumption(String url, List<Long> idList) throws InterruptedException {
        Channel channel = SparrowConsumerInitConstant.getSparrowConsumerInit(url).init();
        SparrowRequestContext sparrowRequestContext = SparrowRequestContextBuilder.builderConfirmConsumption(idList);
        channel.writeAndFlush(sparrowRequestContext);
    }
}

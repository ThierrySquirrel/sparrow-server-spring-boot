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
package io.github.thierrysquirrel.sparrow.server.modular.execution;

import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowMessage;
import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowRequestContext;
import io.github.thierrysquirrel.sparrow.server.common.netty.domain.builder.SparrowRequestContextBuilder;
import io.github.thierrysquirrel.sparrow.server.core.container.ConsumerMessageQuery;
import io.github.thierrysquirrel.sparrow.server.core.container.ProducerMessageQuery;
import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.SparrowMessageEntity;
import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.builder.SparrowMessageEntityBuilder;
import io.github.thierrysquirrel.sparrow.server.database.service.SparrowMessageService;
import io.github.thierrysquirrel.sparrow.server.database.service.core.container.DatabaseReadStateContainer;
import io.github.thierrysquirrel.sparrow.server.database.service.core.execution.SparrowMessageServiceExecution;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * ClassName: MessageModularExecution
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class MessageModularExecution {
    private MessageModularExecution() {
    }

    public static void postMessage(SparrowMessageService sparrowMessageService, String topic, byte[] message) {
        SparrowMessageEntity sparrowMessageEntity = SparrowMessageEntityBuilder.builderSparrowMessageEntity(topic, message);

        List<SparrowMessageEntity> messageList = ProducerMessageQuery.putMessage(topic, sparrowMessageEntity);
        if (ObjectUtils.isEmpty(messageList)) {
            return;
        }
        SparrowMessageServiceExecution.asyncSaveAll(sparrowMessageService, messageList, topic);
    }

    public static void pullMessage(ChannelHandlerContext ctx, SparrowMessageService sparrowMessageService, Integer requestOffset, String topic) {
        List<SparrowMessage> message = ConsumerMessageQuery.getMessage(topic);
        if (ObjectUtils.isEmpty(message)) {
            boolean tryDatabaseRead = DatabaseReadStateContainer.tryDatabaseRead(topic);
            if (tryDatabaseRead) {
                SparrowMessageServiceExecution.asyncFindAllByTopic(sparrowMessageService, topic);
            }
            return;
        }
        SparrowRequestContext sparrowRequestContext = SparrowRequestContextBuilder.builderPullMessageResponse(requestOffset, message);
        ctx.writeAndFlush(sparrowRequestContext);
    }

    public static void confirmConsumption(SparrowMessageService sparrowMessageService, List<Long> idList) {
        sparrowMessageService.updateAllByIdList(idList);
    }
}

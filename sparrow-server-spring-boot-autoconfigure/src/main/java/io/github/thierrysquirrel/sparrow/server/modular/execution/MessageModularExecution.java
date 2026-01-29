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

import io.github.thierrysquirrel.hummingbird.core.facade.SocketChannelFacade;
import io.github.thierrysquirrel.jellyfish.container.JellyfishContainer;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowMessage;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowRequestContext;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.builder.SparrowRequestContextBuilder;
import io.github.thierrysquirrel.sparrow.server.core.container.ConsumerMessageQuery;
import io.github.thierrysquirrel.sparrow.server.core.container.ProducerMessageQuery;
import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.SparrowMessageEntity;
import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.builder.SparrowMessageEntityBuilder;
import io.github.thierrysquirrel.sparrow.server.database.service.SparrowMessageService;
import io.github.thierrysquirrel.sparrow.server.database.service.core.container.DatabaseReadStateContainer;
import io.github.thierrysquirrel.sparrow.server.database.service.core.execution.SparrowMessageServiceExecution;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName: MessageModularExecution
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class MessageModularExecution {
    private static final Logger logger = Logger.getLogger(MessageModularExecution.class.getName());

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

    public static void pullMessage(SocketChannelFacade<SparrowRequestContext> ctx, SparrowMessageService sparrowMessageService, Integer requestOffset, String topic) {

        JellyfishContainer<List<SparrowMessage>> message = ConsumerMessageQuery.getMessage(topic);
        if (message.isEmpty()) {
            boolean tryDatabaseRead = DatabaseReadStateContainer.tryDatabaseRead(topic);
            if (tryDatabaseRead) {
                SparrowMessageServiceExecution.asyncFindAllByTopic(sparrowMessageService, topic);
            }
            return;
        }
        SparrowRequestContext sparrowRequestContext = SparrowRequestContextBuilder.builderPullMessageResponse(requestOffset, message.getValue());

        sendMessage(ctx, sparrowRequestContext);

    }

    public static void confirmConsumption(SparrowMessageService sparrowMessageService, List<Long> idList) {
        sparrowMessageService.updateAllByIdList(idList);
    }

    private static void sendMessage(SocketChannelFacade<SparrowRequestContext> ctx, SparrowRequestContext msg) {
        try {
            ctx.sendMessage(msg);
        } catch (IOException e) {
            String logMsg = "pullMessage Error";
            logger.log(Level.WARNING, logMsg, e);
        }
    }
}

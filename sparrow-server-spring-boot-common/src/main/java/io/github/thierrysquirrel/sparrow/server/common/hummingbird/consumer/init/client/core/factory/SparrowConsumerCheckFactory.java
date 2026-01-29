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
package io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.factory;

import io.github.thierrysquirrel.hummingbird.core.facade.SocketChannelFacade;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.container.MessageIdQuery;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.container.MessageNumberContainer;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.container.RequestOffsetContainer;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.container.ResponseContainer;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.container.constant.MessageNumberConstant;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.factory.constant.ConsumerResponseConstant;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.utils.SleepUtils;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.client.core.utils.constant.SleepUtilsConstant;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.constant.SparrowConsumerInitConstant;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowMessageBatch;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowRequestContext;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.builder.SparrowRequestContextBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName: SparrowConsumerCheckFactory
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowConsumerCheckFactory {
    private static final Logger logger = Logger.getLogger(SparrowConsumerCheckFactory.class.getName());

    private SparrowConsumerCheckFactory() {
    }


    public static boolean consumptionTimeoutIdList(String url) {
        List<Long> idList = MessageIdQuery.pollTimeoutIdList(url);
        if (idList.isEmpty()) {
            return Boolean.FALSE;
        }
        SocketChannelFacade<SparrowRequestContext> channel = initChannel(url);
        if (channel == null) {
            return Boolean.TRUE;
        }
        SparrowRequestContext sparrowRequestContext = SparrowRequestContextBuilder.builderConfirmConsumption(idList);
        try {
            channel.sendMessage(sparrowRequestContext);
        } catch (IOException e) {
            String logMsg = "sendMessage Error";
            logger.log(Level.WARNING, logMsg, e);
        }
        return Boolean.FALSE;
    }

    public static boolean checkMessageNumber(String topic) {
        int messageNumber = MessageNumberContainer.getMessageNumber(topic);
        if (messageNumber >= MessageNumberConstant.MAX_MESSAGE_NUMBER) {
            SleepUtils.sleep(SleepUtilsConstant.MESSAGE_NUMBER_MAX);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static SparrowMessageBatch pullMessage(String url, String topic) {

        SocketChannelFacade<SparrowRequestContext> channel = initChannel(url);
        if (channel == null) {
            return null;
        }

        int requestOffset = RequestOffsetContainer.getRequestOffset();
        CompletableFuture<SparrowMessageBatch> response = ResponseContainer.createResponse(requestOffset);

        SparrowRequestContext sparrowRequestContext = SparrowRequestContextBuilder.builderPullMessageRequest(requestOffset, topic);
        try {
            channel.sendMessage(sparrowRequestContext);
            return response.get(ConsumerResponseConstant.TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            String logMsg = "response empty";
            logger.log(Level.WARNING, logMsg);
        } finally {
            ResponseContainer.remove(requestOffset);
        }
        return null;
    }

    private static SocketChannelFacade<SparrowRequestContext> initChannel(String url) {
        try {
            return SparrowConsumerInitConstant.getSparrowConsumerInit(url).init();
        } catch (Exception e) {
            String logMsg = "initError";
            logger.log(Level.WARNING, logMsg, e);
            SleepUtils.sleep(SleepUtilsConstant.CONNECTION_FAIL);
        }
        return null;
    }
}

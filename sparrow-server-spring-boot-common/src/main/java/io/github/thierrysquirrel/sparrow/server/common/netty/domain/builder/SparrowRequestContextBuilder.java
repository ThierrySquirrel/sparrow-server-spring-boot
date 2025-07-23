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
package io.github.thierrysquirrel.sparrow.server.common.netty.domain.builder;

import io.github.thierrysquirrel.sparrow.server.common.netty.domain.*;
import io.github.thierrysquirrel.sparrow.server.common.netty.domain.constant.Event;
import io.github.thierrysquirrel.sparrow.server.common.netty.domain.constant.Modular;

import java.util.List;

/**
 * ClassName: SparrowRequestContextBuilder
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowRequestContextBuilder {
    private SparrowRequestContextBuilder() {
    }

    public static SparrowRequestContext builderPostMessageRequest(String topic, byte[] message) {
        SparrowRequestContext sparrowRequestContext = builderMessageRequest();
        sparrowRequestContext.setEvent(Event.POST_MESSAGE);

        SparrowRequest sparrowRequest = new SparrowRequest();
        sparrowRequest.setParameters(new Object[]{topic, message});

        sparrowRequestContext.setSparrowRequest(sparrowRequest);
        return sparrowRequestContext;
    }

    public static SparrowRequestContext builderPullMessageRequest(Integer requestOffset, String topic) {
        SparrowRequestContext sparrowRequestContext = builderMessageRequest();
        sparrowRequestContext.setEvent(Event.PULL_MESSAGE);

        SparrowRequest sparrowRequest = new SparrowRequest();
        sparrowRequest.setParameters(new Object[]{requestOffset, topic});

        sparrowRequestContext.setSparrowRequest(sparrowRequest);
        return sparrowRequestContext;
    }

    public static SparrowRequestContext builderPullMessageResponse(Integer requestOffset, List<SparrowMessage> sparrowMessageList) {
        SparrowRequestContext sparrowRequestContext = new SparrowRequestContext();

        SparrowResponse sparrowResponse = new SparrowResponse();
        SparrowMessageBatch sparrowMessageBatch = SparrowMessageBatchBuilder.builderSparrowMessageBatch(requestOffset, sparrowMessageList);
        sparrowResponse.setData(sparrowMessageBatch);

        sparrowRequestContext.setSparrowResponse(sparrowResponse);
        return sparrowRequestContext;
    }

    public static SparrowRequestContext builderConfirmConsumption(List<Long> idList) {
        SparrowRequestContext sparrowRequestContext = builderMessageRequest();
        sparrowRequestContext.setEvent(Event.CONFIRM_CONSUMPTION);

        SparrowRequest sparrowRequest = new SparrowRequest();
        sparrowRequest.setParameters(new Object[]{idList});

        sparrowRequestContext.setSparrowRequest(sparrowRequest);
        return sparrowRequestContext;
    }


    private static SparrowRequestContext builderMessageRequest() {
        SparrowRequestContext sparrowRequestContext = new SparrowRequestContext();
        sparrowRequestContext.setModular(Modular.MESSAGE);
        return sparrowRequestContext;
    }
}

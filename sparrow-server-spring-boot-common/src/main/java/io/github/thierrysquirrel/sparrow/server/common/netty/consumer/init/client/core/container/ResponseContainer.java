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
package io.github.thierrysquirrel.sparrow.server.common.netty.consumer.init.client.core.container;

import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowMessageBatch;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * ClassName: ResponseContainer
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class ResponseContainer {
    private static final Map<Integer, CompletableFuture<SparrowMessageBatch>> RESPONSE = Maps.newConcurrentMap();

    private ResponseContainer() {
    }

    public static CompletableFuture<SparrowMessageBatch> createResponse(int requestOffset) {
        CompletableFuture<SparrowMessageBatch> response = new CompletableFuture<>();
        RESPONSE.put(requestOffset, response);
        return response;
    }

    public static void callResponse(int requestOffset, SparrowMessageBatch sparrowMessageBatch) {
        CompletableFuture<SparrowMessageBatch> response = RESPONSE.get(requestOffset);
        if (null == response) {
            return;
        }
        response.complete(sparrowMessageBatch);
    }

    public static void remove(int requestOffset) {
        RESPONSE.remove(requestOffset);
    }
}

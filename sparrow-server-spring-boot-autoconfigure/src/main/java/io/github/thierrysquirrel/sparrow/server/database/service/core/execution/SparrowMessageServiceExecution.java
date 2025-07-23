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
package io.github.thierrysquirrel.sparrow.server.database.service.core.execution;

import io.github.thierrysquirrel.sparrow.server.core.constant.ThreadPoolExecutorConstant;
import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.SparrowMessageEntity;
import io.github.thierrysquirrel.sparrow.server.database.service.SparrowMessageService;
import io.github.thierrysquirrel.sparrow.server.database.service.core.thread.execution.AsyncFindAllByTopicThreadExecution;
import io.github.thierrysquirrel.sparrow.server.database.service.core.thread.execution.AsyncSaveAllThreadExecution;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ClassName: SparrowMessageServiceFactory
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowMessageServiceExecution {
    private SparrowMessageServiceExecution() {
    }

    public static void asyncSaveAll(SparrowMessageService sparrowMessageService, List<SparrowMessageEntity> sparrowMessageEntityList, String topic) {
        AsyncSaveAllThreadExecution asyncSaveAllThreadExecution = new AsyncSaveAllThreadExecution(sparrowMessageService, sparrowMessageEntityList, topic);
        ThreadPoolExecutor asyncSparrowMessageService = ThreadPoolExecutorConstant.ASYNC_SPARROW_MESSAGE_SERVICE;
        asyncSparrowMessageService.execute(asyncSaveAllThreadExecution);
    }

    public static void asyncFindAllByTopic(SparrowMessageService sparrowMessageService, String topic) {
        AsyncFindAllByTopicThreadExecution asyncFindAllByTopicThreadExecution = new AsyncFindAllByTopicThreadExecution(sparrowMessageService, topic);
        ThreadPoolExecutor asyncSparrowMessageService = ThreadPoolExecutorConstant.ASYNC_SPARROW_MESSAGE_SERVICE;
        asyncSparrowMessageService.execute(asyncFindAllByTopicThreadExecution);
    }
}

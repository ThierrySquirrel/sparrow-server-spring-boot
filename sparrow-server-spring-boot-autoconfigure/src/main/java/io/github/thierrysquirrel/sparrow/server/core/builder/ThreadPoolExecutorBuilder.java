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
package io.github.thierrysquirrel.sparrow.server.core.builder;

import io.github.thierrysquirrel.sparrow.server.core.builder.constant.ThreadPoolExecutorBuilderConstant;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * ClassName: ThreadPoolExecutorBuilder
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class ThreadPoolExecutorBuilder {

    private ThreadPoolExecutorBuilder() {
    }

    public static ThreadPoolExecutor builderSparrowServerEventThreadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(ThreadPoolExecutorBuilderConstant.SPARROW_SERVER_EVENT).build();
        return new ThreadPoolExecutor(ThreadPoolExecutorBuilderConstant.SPARROW_SERVER_EVENT_CORE_POOL_SIZE,
                ThreadPoolExecutorBuilderConstant.SPARROW_SERVER_EVENT_MAXIMUM_POOL_SIZE,
                ThreadPoolExecutorBuilderConstant.KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public static ThreadPoolExecutor builderSparrowServerInitializationThreadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(ThreadPoolExecutorBuilderConstant.SPARROW_SERVER_INITIALIZATION).build();
        return new ThreadPoolExecutor(ThreadPoolExecutorBuilderConstant.SPARROW_SERVER_INITIALIZATION_CORE_POOL_SIZE,
                ThreadPoolExecutorBuilderConstant.SPARROW_SERVER_INITIALIZATION_MAXIMUM_POOL_SIZE,
                ThreadPoolExecutorBuilderConstant.KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public static ThreadPoolExecutor builderAsyncSparrowMessageServiceThreadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(ThreadPoolExecutorBuilderConstant.ASYNC_SPARROW_MESSAGE_SERVICE).build();
        return new ThreadPoolExecutor(ThreadPoolExecutorBuilderConstant.ASYNC_SPARROW_MESSAGE_SERVICE_CORE_POOL_SIZE,
                ThreadPoolExecutorBuilderConstant.ASYNC_SPARROW_MESSAGE_SERVICE_MAXIMUM_POOL_SIZE,
                ThreadPoolExecutorBuilderConstant.KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public static ScheduledThreadPoolExecutor builderFlushTimeoutMessageThreadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(ThreadPoolExecutorBuilderConstant.FLUSH_TIMEOUT_MESSAGE).build();
        return new ScheduledThreadPoolExecutor(ThreadPoolExecutorBuilderConstant.FLUSH_TIMEOUT_MESSAGE_CORE_POOL_SIZE, threadFactory);
    }

    public static ScheduledThreadPoolExecutor builderDeleteTimeoutMessageThreadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(ThreadPoolExecutorBuilderConstant.DELETE_TIMEOUT_MESSAGE).build();
        return new ScheduledThreadPoolExecutor(ThreadPoolExecutorBuilderConstant.DELETE_TIMEOUT_MESSAGE_CORE_POOL_SIZE, threadFactory);
    }

}

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
package io.github.thierrysquirrel.sparrow.server.common.netty.consumer.init.client.core.builder;

import io.github.thierrysquirrel.sparrow.server.common.netty.consumer.init.client.core.builder.constant.ThreadPoolExecutorBuilderConstant;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    public static ThreadPoolExecutor builderSparrowConsumerThreadPoolExecutor(String topic) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(ThreadPoolExecutorBuilderConstant.SPARROW_CONSUMER + ":" + topic).build();
        return new ThreadPoolExecutor(ThreadPoolExecutorBuilderConstant.SPARROW_CONSUMER_CORE_POOL_SIZE,
                ThreadPoolExecutorBuilderConstant.SPARROW_CONSUMER_MAXIMUM_POOL_SIZE,
                ThreadPoolExecutorBuilderConstant.KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}

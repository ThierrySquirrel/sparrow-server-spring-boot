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
package io.github.thierrysquirrel.sparrow.server.netty.handler.execution;

import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowRequestContext;
import io.github.thierrysquirrel.sparrow.server.core.constant.ThreadPoolExecutorConstant;
import io.github.thierrysquirrel.sparrow.server.netty.core.thread.execution.SparrowServerEventThreadExecution;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ClassName: SparrowServerEventExecution
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowServerEventExecution {
    private SparrowServerEventExecution() {
    }

    public static void event(ChannelHandlerContext ctx, SparrowRequestContext msg) {
        SparrowServerEventThreadExecution sparrowServerEventThreadExecution = new SparrowServerEventThreadExecution(ctx, msg);
        ThreadPoolExecutor sparrowServerEvent = ThreadPoolExecutorConstant.SPARROW_SERVER_EVENT;
        sparrowServerEvent.execute(sparrowServerEventThreadExecution);
    }
}

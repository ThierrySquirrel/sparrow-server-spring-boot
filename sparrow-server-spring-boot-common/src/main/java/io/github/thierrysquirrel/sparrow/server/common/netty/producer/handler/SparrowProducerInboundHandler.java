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
package io.github.thierrysquirrel.sparrow.server.common.netty.producer.handler;

import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowRequestContext;
import io.github.thierrysquirrel.sparrow.server.common.netty.handler.AbstractInboundHandler;
import io.github.thierrysquirrel.sparrow.server.common.netty.producer.handler.execution.ProducerHandlerExecution;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;

/**
 * ClassName: SparrowProducerInboundHandler
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowProducerInboundHandler extends AbstractInboundHandler {
    public SparrowProducerInboundHandler() {
        super(IdleState.WRITER_IDLE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SparrowRequestContext msg) {
        ProducerHandlerExecution.noResponse();
    }
}

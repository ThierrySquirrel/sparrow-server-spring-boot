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
package io.github.thierrysquirrel.sparrow.server.hummingbird.core.thread.execution;

import io.github.thierrysquirrel.hummingbird.core.facade.SocketChannelFacade;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowRequestContext;
import io.github.thierrysquirrel.sparrow.server.hummingbird.core.factory.execution.ModularMethodExecution;
import io.github.thierrysquirrel.sparrow.server.hummingbird.core.thread.AbstractSparrowServerEventThread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName: SparrowServerEventThreadExecution
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowServerEventThreadExecution extends AbstractSparrowServerEventThread {
    private static final Logger logger = Logger.getLogger(SparrowServerEventThreadExecution.class.getName());

    public SparrowServerEventThreadExecution(SocketChannelFacade<SparrowRequestContext> ctx, SparrowRequestContext msg) {
        super(ctx, msg);
    }

    /**
     * event
     *
     * @param ctx ctx
     * @param msg msg
     */
    @Override
    protected void event(SocketChannelFacade<SparrowRequestContext> ctx, SparrowRequestContext msg) {
        try {
            ModularMethodExecution.invoke(ctx, msg);
        } catch (Exception e) {
            String logMsg = "event Error";
            logger.log(Level.WARNING, logMsg, e);
        }
    }
}

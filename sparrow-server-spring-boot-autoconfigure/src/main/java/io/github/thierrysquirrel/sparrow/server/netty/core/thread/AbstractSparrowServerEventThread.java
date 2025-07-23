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
package io.github.thierrysquirrel.sparrow.server.netty.core.thread;

import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowRequestContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * ClassName: AbstractSparrowServerEventThread
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public abstract class AbstractSparrowServerEventThread implements Runnable {

    private final ChannelHandlerContext ctx;
    private final SparrowRequestContext msg;

    protected AbstractSparrowServerEventThread(ChannelHandlerContext ctx, SparrowRequestContext msg) {
        this.ctx = ctx;
        this.msg = msg;
    }

    /**
     * event
     *
     * @param ctx ctx
     * @param msg msg
     */
    protected abstract void event(ChannelHandlerContext ctx, SparrowRequestContext msg);

    @Override
    public void run() {
        event(this.ctx, this.msg);
    }
}

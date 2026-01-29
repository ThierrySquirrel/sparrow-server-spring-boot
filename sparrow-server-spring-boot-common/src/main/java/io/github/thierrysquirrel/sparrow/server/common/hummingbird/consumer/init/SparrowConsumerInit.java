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
package io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init;

import io.github.thierrysquirrel.hummingbird.core.client.init.HummingbirdClientInit;
import io.github.thierrysquirrel.hummingbird.core.client.init.builder.HummingbirdClientInitBuilder;
import io.github.thierrysquirrel.hummingbird.core.facade.SocketChannelFacade;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.coder.SparrowDecoder;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.coder.SparrowEncoder;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.handler.SparrowConsumerInboundHandler;

import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.constant.ConsumerThreadPoolContainer;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowRequestContext;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.handler.constant.IdleStateHandlerConstant;

/**
 * ClassName: SparrowConsumerInit
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowConsumerInit {
    private final String url;
    private HummingbirdClientInit<SparrowRequestContext> clientInit;
    private SocketChannelFacade<SparrowRequestContext> connect;

    public SparrowConsumerInit(String url) {
        this.url = url;
    }

    public SocketChannelFacade<SparrowRequestContext> init() throws Exception {
        if (clientInit == null || connect == null) {
            initConnect();
        }
        if (!connect.isOpen()) {
            initConnect();
        }
        return connect;
    }

    private void initConnect() throws Exception {
        clientInit = HummingbirdClientInitBuilder.builderHummingbirdClientInit(ConsumerThreadPoolContainer.getThreadPool(), url,
                IdleStateHandlerConstant.OTHER_TIMEOUT, IdleStateHandlerConstant.CLIENT_WRITE_TIMEOUT,
                new SparrowDecoder(), new SparrowEncoder(), new SparrowConsumerInboundHandler());
        connect = clientInit.connect();
    }
}

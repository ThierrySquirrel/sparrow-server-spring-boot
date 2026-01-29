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
package io.github.thierrysquirrel.sparrow.server.hummingbird.init;

import io.github.thierrysquirrel.hummingbird.core.server.init.HummingbirdServerInit;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.coder.SparrowDecoder;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.coder.SparrowEncoder;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.handler.constant.IdleStateHandlerConstant;
import io.github.thierrysquirrel.sparrow.server.hummingbird.handler.SparrowServerInboundHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName: SparrowServerInit
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowServerInit {
    private static final Logger logger = Logger.getLogger(SparrowServerInit.class.getName());

    private SparrowServerInit() {
    }

    public static void init(String sparrowServerUrl) {
        try {
            HummingbirdServerInit.init(sparrowServerUrl, IdleStateHandlerConstant.SERVER_READER_TIMEOUT, IdleStateHandlerConstant.OTHER_TIMEOUT,
                    new SparrowDecoder(), new SparrowEncoder(), new SparrowServerInboundHandler());
        } catch (IOException e) {
            String logMsg = "SparrowServerInit Error";
            logger.log(Level.WARNING, logMsg, e);
        }
    }

}

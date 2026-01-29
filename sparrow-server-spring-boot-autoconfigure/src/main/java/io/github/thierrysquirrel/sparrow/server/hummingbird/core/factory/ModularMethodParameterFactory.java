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
package io.github.thierrysquirrel.sparrow.server.hummingbird.core.factory;

import io.github.thierrysquirrel.hummingbird.core.facade.SocketChannelFacade;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowRequest;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowRequestContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: ModularMethodParameterFactory
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class ModularMethodParameterFactory {
    private ModularMethodParameterFactory() {
    }

    public static Object[] getParameter(SocketChannelFacade<SparrowRequestContext> ctx, SparrowRequestContext msg) {
        List<Object> parameter = new ArrayList<>();
        parameter.add(ctx);
        parameter.add(msg);
        SparrowRequest sparrowRequest = msg.getSparrowRequest();

        Object[] parameters = sparrowRequest.getParameters();
        parameter.addAll(Arrays.stream(parameters).collect(Collectors.toList()));

        return parameter.toArray();
    }
}

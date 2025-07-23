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
package io.github.thierrysquirrel.sparrow.server.netty.core.factory.execution;

import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowRequestContext;
import io.github.thierrysquirrel.sparrow.server.netty.core.container.ModularMethodContainer;
import io.github.thierrysquirrel.sparrow.server.netty.core.domain.ModularMethod;
import io.github.thierrysquirrel.sparrow.server.netty.core.factory.ModularMethodParameterFactory;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ClassName: ModularMethodExecution
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class ModularMethodExecution {
    private ModularMethodExecution() {
    }

    public static void invoke(ChannelHandlerContext ctx, SparrowRequestContext msg) throws InvocationTargetException, IllegalAccessException {
        Object[] eventParameter = ModularMethodParameterFactory.getParameter(ctx, msg);
        ModularMethod modularMethod = ModularMethodContainer.getMethodDomain(msg.getModular(), msg.getEvent());
        Method method = modularMethod.getMethod();
        Object object = modularMethod.getBean();
        method.invoke(object, eventParameter);
    }
}

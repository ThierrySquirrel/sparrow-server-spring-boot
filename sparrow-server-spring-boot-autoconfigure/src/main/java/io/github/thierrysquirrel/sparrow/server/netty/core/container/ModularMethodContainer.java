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
package io.github.thierrysquirrel.sparrow.server.netty.core.container;

import io.github.thierrysquirrel.sparrow.server.common.netty.domain.constant.Event;
import io.github.thierrysquirrel.sparrow.server.common.netty.domain.constant.Modular;
import io.github.thierrysquirrel.sparrow.server.netty.core.domain.ModularMethod;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * ClassName: ModularMethodContainer
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class ModularMethodContainer {
    private static final Map<Modular, Map<Event, ModularMethod>> METHOD_CONTAINER = Maps.newConcurrentMap();

    private ModularMethodContainer() {
    }

    public static void putMethodDomain(Modular modular, Event event, ModularMethod modularMethod) {
        METHOD_CONTAINER.computeIfAbsent(modular, key -> Maps.newConcurrentMap())
                .put(event, modularMethod);
    }

    public static ModularMethod getMethodDomain(Modular modular, Event event) {
        return METHOD_CONTAINER.get(modular).get(event);
    }
}

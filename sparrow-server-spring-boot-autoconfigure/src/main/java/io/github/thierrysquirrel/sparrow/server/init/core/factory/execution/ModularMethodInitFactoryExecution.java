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
package io.github.thierrysquirrel.sparrow.server.init.core.factory.execution;

import io.github.thierrysquirrel.sparrow.server.annotation.SparrowServerEvent;
import io.github.thierrysquirrel.sparrow.server.annotation.SparrowServerModular;
import io.github.thierrysquirrel.sparrow.server.init.core.factory.ModularMethodInitFactory;
import io.github.thierrysquirrel.sparrow.server.init.core.utils.AnnotatedMethodsUtils;
import org.springframework.context.ApplicationContext;

/**
 * ClassName: ModularMethodInitFactoryExecution
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class ModularMethodInitFactoryExecution {
    private ModularMethodInitFactoryExecution() {
    }

    public static void init(ApplicationContext applicationContext) {
        applicationContext.getBeansWithAnnotation(SparrowServerModular.class).forEach((beanName, bean) ->
                AnnotatedMethodsUtils.getMethodAndAnnotation(bean, SparrowServerEvent.class).
                        forEach((method, sparrowServerEvent) -> ModularMethodInitFactory.putModularMethod(bean, method, sparrowServerEvent.event()))
        );
    }
}

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
package io.github.thierrysquirrel.sparrow.server.autoconfigure;

import io.github.thierrysquirrel.sparrow.server.autoconfigure.constant.ScanConstant;
import io.github.thierrysquirrel.sparrow.server.database.service.SparrowMessageService;
import io.github.thierrysquirrel.sparrow.server.init.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: SparrowServerAutoconfigure
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
@Configuration
@EnableConfigurationProperties(SparrowServerProperties.class)
@ComponentScan(ScanConstant.MODULAR_SCAN)
@MapperScan(ScanConstant.MAPPER_SCAN)
public class SparrowServerAutoconfigure {

    @Bean
    @ConditionalOnMissingBean(ModularMethodInit.class)
    public ModularMethodInit modularMethodInit() {
        return new ModularMethodInit();
    }

    @Bean
    @ConditionalOnMissingBean(SparrowServerInitialization.class)
    public SparrowServerInitialization sparrowServerInitialization() {
        return new SparrowServerInitialization();
    }

    @Bean
    @ConditionalOnMissingBean(SparrowMessageService.class)
    public SparrowMessageService sparrowMessageService() {
        return new SparrowMessageService();
    }

    @Bean
    @ConditionalOnMissingBean(SparrowMessageEntityInit.class)
    public SparrowMessageEntityInit sparrowMessageEntityInit() {
        return new SparrowMessageEntityInit();
    }

    @Bean
    @ConditionalOnMissingBean(FlushTimeoutMessageInit.class)
    public FlushTimeoutMessageInit flushTimeoutMessageInit() {
        return new FlushTimeoutMessageInit();
    }

    @Bean
    @ConditionalOnMissingBean(DeleteTimeoutMessageInit.class)
    public DeleteTimeoutMessageInit deleteTimeoutMessageInit() {
        return new DeleteTimeoutMessageInit();
    }
}

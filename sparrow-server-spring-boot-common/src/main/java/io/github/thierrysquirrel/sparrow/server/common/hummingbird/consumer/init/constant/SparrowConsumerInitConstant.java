/**
 * Copyright 2026/6/4 ThierrySquirrel
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
package io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.constant;

import io.github.thierrysquirrel.jellyfish.concurrency.map.hash.ConcurrencyHashMap;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.consumer.init.SparrowConsumerInit;


/**
 * ClassName: SparrowConsumerInitConstant
 * Description:
 * Date:2026/6/4
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class SparrowConsumerInitConstant {
    private static final ConcurrencyHashMap<String, ConcurrencyHashMap<String, SparrowConsumerInit>> SPARROW_PRODUCER_INIT = new ConcurrencyHashMap<>(Runtime.getRuntime().availableProcessors() * 2);

    private SparrowConsumerInitConstant() {
    }

    public static SparrowConsumerInit getSparrowConsumerInit(String topic, String url) {
        return SPARROW_PRODUCER_INIT.getIfAbsent(topic, key -> new ConcurrencyHashMap<>(Runtime.getRuntime().availableProcessors() * 2))
                .getIfAbsent(url, key -> new SparrowConsumerInit(topic, url));
    }
}

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
package io.github.thierrysquirrel.sparrow.server.common.hummingbird.producer.init.client;

import io.github.thierrysquirrel.sparrow.server.common.hummingbird.producer.listener.DefaultProducerFail;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.utils.constant.SocketAddressConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: SparrowProducerCluster
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowProducerCluster {
    private static final Map<String, List<SparrowProducer>> SPARROW_PRODUCER = new ConcurrentHashMap<>();
    private static final Map<String, AtomicInteger> SPARROW_PRODUCER_OFFSET = new ConcurrentHashMap<>();

    private SparrowProducerCluster() {
    }

    public static SparrowProducer getSparrowProducer(String clusterUrl, String topic) {
        List<SparrowProducer> sparrowProducerList = SPARROW_PRODUCER.computeIfAbsent(topic, key -> createSparrowProducerList(clusterUrl, topic));
        int increment = SPARROW_PRODUCER_OFFSET.computeIfAbsent(topic, key -> new AtomicInteger()).incrementAndGet();
        int offset = increment % sparrowProducerList.size();
        int positiveOffset = (offset + sparrowProducerList.size()) % sparrowProducerList.size();
        return sparrowProducerList.get(positiveOffset);
    }


    private static List<SparrowProducer> createSparrowProducerList(String clusterUrl, String topic) {
        String[] urlSplit = clusterUrl.split(SocketAddressConstant.URL_SEPARATOR);
        List<SparrowProducer> sparrowProducerList = new ArrayList<>();
        DefaultProducerFail defaultProducerFail = new DefaultProducerFail();
        for (String url : urlSplit) {
            sparrowProducerList.add(new SparrowProducer(defaultProducerFail, url, topic));
        }
        return sparrowProducerList;
    }

}

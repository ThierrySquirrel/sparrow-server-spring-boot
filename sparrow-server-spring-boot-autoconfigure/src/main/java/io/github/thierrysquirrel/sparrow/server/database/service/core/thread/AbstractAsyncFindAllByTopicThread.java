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
package io.github.thierrysquirrel.sparrow.server.database.service.core.thread;

import io.github.thierrysquirrel.sparrow.server.database.service.SparrowMessageService;

/**
 * ClassName: AbstractAsyncFindAllByTopicThread
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public abstract class AbstractAsyncFindAllByTopicThread implements Runnable {
    private final SparrowMessageService sparrowMessageService;
    private final String topic;

    protected AbstractAsyncFindAllByTopicThread(SparrowMessageService sparrowMessageService, String topic) {
        this.sparrowMessageService = sparrowMessageService;
        this.topic = topic;
    }

    /**
     * findAllByTopic
     *
     * @param sparrowMessageService sparrowMessageService
     * @param topic                 topic
     */
    protected abstract void findAllByTopic(SparrowMessageService sparrowMessageService, String topic);

    @Override
    public void run() {
        findAllByTopic(this.sparrowMessageService, this.topic);
    }
}

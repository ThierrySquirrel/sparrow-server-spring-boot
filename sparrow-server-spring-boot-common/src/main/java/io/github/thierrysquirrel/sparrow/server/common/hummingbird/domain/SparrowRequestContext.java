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
package io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain;

import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.constant.Event;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.constant.Modular;

/**
 * ClassName: SparrowRequestContext
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowRequestContext {
    private Modular modular;
    private Event event;
    private SparrowRequest sparrowRequest;
    private SparrowResponse sparrowResponse;

    public Modular getModular() {
        return modular;
    }

    public void setModular(Modular modular) {
        this.modular = modular;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public SparrowRequest getSparrowRequest() {
        return sparrowRequest;
    }

    public void setSparrowRequest(SparrowRequest sparrowRequest) {
        this.sparrowRequest = sparrowRequest;
    }

    public SparrowResponse getSparrowResponse() {
        return sparrowResponse;
    }

    public void setSparrowResponse(SparrowResponse sparrowResponse) {
        this.sparrowResponse = sparrowResponse;
    }

    @Override
    public String toString() {
        return "SparrowRequestContext{" +
                "modular=" + modular +
                ", event=" + event +
                ", sparrowRequest=" + sparrowRequest +
                ", sparrowResponse=" + sparrowResponse +
                '}';
    }
}

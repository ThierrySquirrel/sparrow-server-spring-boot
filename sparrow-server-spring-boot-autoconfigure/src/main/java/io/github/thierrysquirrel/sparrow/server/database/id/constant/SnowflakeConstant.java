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

package io.github.thierrysquirrel.sparrow.server.database.id.constant;

/**
 * ClassName: SnowflakeConstant
 * Description:
 * Date:2026/6/4
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class SnowflakeConstant {
    private SnowflakeConstant() {
    }

    public static final long DEFAULT_LAST_TIMESTAMP = 0L;

    public static final long DEFAULT_PAST_TIME = 0;

    public static final int MAX_THREAD_ID = 128;

    public static final int MAX_SEQUENCE = 4096;

    public static final int THIS_TIME_LEFT = 18;

    public static final int THREAD_ID_LEFT = 12;
}

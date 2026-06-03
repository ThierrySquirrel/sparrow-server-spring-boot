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
package io.github.thierrysquirrel.sparrow.server.common.hummingbird.coder.utils;


import io.github.thierrysquirrel.ants.utils.AntsUtils;


/**
 * ClassName: SerializerUtils
 * Description:
 * Date:2026/6/4
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class SerializerUtils {
    private SerializerUtils() {
    }

    public static <T> byte[] serialize(T object) {
        return AntsUtils.serialize(object);
    }

    public static <T> T deSerialize(byte[] bytes, Class<T> clazz) {
        return AntsUtils.deSerialize(bytes, clazz);
    }

}

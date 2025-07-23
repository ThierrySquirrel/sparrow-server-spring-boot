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
package io.github.thierrysquirrel.sparrow.server.core.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DomainUtils
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class DomainUtils {
    private DomainUtils() {
    }

    public static <T> T convert(Object domain, Class<T> convertClass) {
        try {
            T newInstance = convertClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(domain, newInstance);
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }

    public static <E, T> List<T> convertList(List<E> domainList, Class<T> convertClass) {
        List<T> list = new ArrayList<>();
        domainList.forEach(domain -> list.add(convert(domain, convertClass)));
        return list;
    }
}

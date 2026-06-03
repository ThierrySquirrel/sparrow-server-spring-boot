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

package io.github.thierrysquirrel.sparrow.server.database.mapper;

import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.SparrowMessageEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName: SparrowMessageUtils
 * Description:
 * Date:2026/6/4
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class SparrowMessageUtils {
    private SparrowMessageUtils() {
    }

    public static void findAllByTopicAndIsDeleted(PreparedStatement preparedStatement, String topic, Byte isDeleted, int size) throws SQLException {
        preparedStatement.setString(1, topic);
        preparedStatement.setByte(2, isDeleted);
        preparedStatement.setInt(3, size);
    }

    public static SparrowMessageEntity findAllByTopicAndIsDeleted(ResultSet resultSet, String topic, Byte isDeleted, int size) throws SQLException {
        SparrowMessageEntity sparrowMessageEntity = new SparrowMessageEntity();
        sparrowMessageEntity.setId(resultSet.getLong(1));
        sparrowMessageEntity.setMessage(resultSet.getBytes(2));
        return sparrowMessageEntity;
    }
}

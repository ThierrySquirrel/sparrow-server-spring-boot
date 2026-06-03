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

import io.github.thierrysquirrel.sparrow.server.database.mapper.constant.MapperConstant;
import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.SparrowMessageEntity;
import org.springframework.jdbc.core.*;

import java.util.Date;
import java.util.List;

/**
 * ClassName: SparrowMessageJdbcTemplate
 * Description:
 * Date:2026/6/4
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class SparrowMessageJdbcTemplate {

    private SparrowMessageJdbcTemplate() {
    }

    public static void initSparrowMessageEntity(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute(MapperConstant.INIT_SPARROW_MESSAGE_ENTITY);
    }

    public static void initIndexTopic(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute(MapperConstant.INIT_INDEX_TOPIC);
    }

    public static void initIndexIsDeleted(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute(MapperConstant.INIT_INDEX_IS_DELETED);

    }

    public static void saveAll(JdbcTemplate jdbcTemplate, List<SparrowMessageEntity> sparrowMessageEntityList) {
        jdbcTemplate.batchUpdate(MapperConstant.SAVE_ALL, new SparrowMessageSavaAllBatch(sparrowMessageEntityList));
    }

    public static void deleteAllByIsDeletedAndGmtCreateLessThanEqual(JdbcTemplate jdbcTemplate, Byte isDeleted, Date gmtModified, int size) {
        jdbcTemplate.update(MapperConstant.DELETE_ALL_BY_IS_DELETED_AND_GMT_CREATE_LESS_THAN_EQUAL, isDeleted, gmtModified, size);
    }

    public static List<SparrowMessageEntity> findAllByTopicAndIsDeleted(JdbcTemplate jdbcTemplate, String topic, Byte isDeleted, int size) {
        return jdbcTemplate.query(MapperConstant.FIND_ALL_BY_TOPIC_AND_IS_DELETED, preparedStatement -> SparrowMessageUtils.findAllByTopicAndIsDeleted(preparedStatement, topic, isDeleted, size),
                (resultSet, rowNum) -> SparrowMessageUtils.findAllByTopicAndIsDeleted(resultSet, topic, isDeleted, size));
    }

    public static void updateAllById(JdbcTemplate jdbcTemplate, List<Long> idList, Byte isDeleted) {
        jdbcTemplate.batchUpdate(MapperConstant.UPDATE_ALL_BY_ID, new SparrowMessageUpdateAllById(idList, isDeleted));
    }


}

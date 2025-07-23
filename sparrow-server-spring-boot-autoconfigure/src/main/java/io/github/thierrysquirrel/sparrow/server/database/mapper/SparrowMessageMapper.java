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
package io.github.thierrysquirrel.sparrow.server.database.mapper;

import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.SparrowMessageEntity;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * ClassName: SparrowMessageMapper
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
@Mapper
public interface SparrowMessageMapper {
    /**
     * initSparrowMessageEntity
     */

    @Update("CREATE TABLE IF NOT EXISTS SPARROW_MESSAGE_ENTITY(" +
            "ID BIGINT NOT NULL AUTO_INCREMENT," +
            "TOPIC VARCHAR(32) NOT NULL," +
            "MESSAGE VARBINARY(65536) NOT NULL," +
            "IS_DELETED TINYINT DEFAULT 0 NOT NULL," +
            "GMT_CREATE TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP(0) NOT NULL," +
            "GMT_MODIFIED TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) NOT NULL," +
            "CONSTRAINT PK_ID PRIMARY KEY (ID))")
    void initSparrowMessageEntity();

    /**
     * initSparrowMessageEntityIndex
     */
    @Update("CREATE INDEX IF NOT EXISTS IDX_TOPIC ON SPARROW_MESSAGE_ENTITY (TOPIC,IS_DELETED)")
    void initIndexTopic();

    /**
     * initIndexIsDeleted
     */
    @Update("CREATE INDEX IF NOT EXISTS IDX_IS_DELETED ON SPARROW_MESSAGE_ENTITY (IS_DELETED,GMT_MODIFIED)")
    void initIndexIsDeleted();

    /**
     * saveAll
     *
     * @param sparrowMessageEntityList sparrowMessageEntityList
     */
    @Insert("<script> " +
            "INSERT INTO SPARROW_MESSAGE_ENTITY (TOPIC,MESSAGE) VALUES" +
            "<foreach collection='sparrowMessageEntityList' item='entity' separator=','> " +
            "(#{entity.topic},#{entity.message})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void saveAll(@Param("sparrowMessageEntityList") List<SparrowMessageEntity> sparrowMessageEntityList);

    /**
     * deleteAllByIsDeletedAndGmtCreateLessThanEqual
     *
     * @param isDeleted   isDeleted
     * @param gmtModified gmtModified
     * @param size        size
     */
    @Delete("DELETE FROM SPARROW_MESSAGE_ENTITY WHERE IS_DELETED = #{isDeleted} AND GMT_MODIFIED <= #{gmtModified} LIMIT #{size}")
    void deleteAllByIsDeletedAndGmtCreateLessThanEqual(@Param("isDeleted") Byte isDeleted, @Param("gmtModified") Date gmtModified, @Param("size") int size);

    /**
     * findAllByTopicAndIsDeleted
     *
     * @param topic     topic
     * @param isDeleted isDeleted
     * @param size      size
     * @return SparrowMessageEntityList
     */
    @Select("SELECT ID,MESSAGE FROM SPARROW_MESSAGE_ENTITY WHERE TOPIC = #{topic} AND IS_DELETED = #{isDeleted} LIMIT #{size}")
    List<SparrowMessageEntity> findAllByTopicAndIsDeleted(@Param("topic") String topic, @Param("isDeleted") Byte isDeleted, @Param("size") int size);

    /**
     * updateAllById
     *
     * @param idList    idList
     * @param isDeleted isDeleted
     */
    @Update("<script> " +
            "UPDATE SPARROW_MESSAGE_ENTITY SET IS_DELETED = #{isDeleted} WHERE ID IN" +
            "<foreach collection='idList' item='id' open='(' close=')' separator=','> " +
            "#{id}" +
            "</foreach> " +
            "</script>")
    void updateAllById(@Param("idList") List<Long> idList, @Param("isDeleted") Byte isDeleted);
}

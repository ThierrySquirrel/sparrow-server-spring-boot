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

package io.github.thierrysquirrel.sparrow.server.database.mapper.constant;

/**
 * ClassName: MapperConstant
 * Description:
 * Date:2026/6/4
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class MapperConstant {

    private MapperConstant() {
    }

    public static String INIT_SPARROW_MESSAGE_ENTITY = "CREATE TABLE IF NOT EXISTS SPARROW_MESSAGE_ENTITY(" +
            "ID BIGINT NOT NULL," +
            "TOPIC VARCHAR(32) NOT NULL," +
            "MESSAGE VARBINARY(65536) NOT NULL," +
            "IS_DELETED TINYINT DEFAULT 0 NOT NULL," +
            "GMT_CREATE TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP(0) NOT NULL," +
            "GMT_MODIFIED TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) NOT NULL," +
            "CONSTRAINT PK_ID PRIMARY KEY (ID))";

    public static String INIT_INDEX_TOPIC = "CREATE INDEX IF NOT EXISTS IDX_TOPIC ON SPARROW_MESSAGE_ENTITY (TOPIC,IS_DELETED)";

    public static String INIT_INDEX_IS_DELETED = "CREATE INDEX IF NOT EXISTS IDX_IS_DELETED ON SPARROW_MESSAGE_ENTITY (IS_DELETED,GMT_MODIFIED)";

    public static String SAVE_ALL = "INSERT INTO SPARROW_MESSAGE_ENTITY (ID,TOPIC,MESSAGE) VALUES (?,?,?)";

    public static String DELETE_ALL_BY_IS_DELETED_AND_GMT_CREATE_LESS_THAN_EQUAL = "DELETE FROM SPARROW_MESSAGE_ENTITY WHERE IS_DELETED = ? AND GMT_MODIFIED <= ? LIMIT ?";

    public static String FIND_ALL_BY_TOPIC_AND_IS_DELETED = "SELECT ID,MESSAGE FROM SPARROW_MESSAGE_ENTITY WHERE TOPIC = ? AND IS_DELETED = ? LIMIT ?";

    public static String UPDATE_ALL_BY_ID = "UPDATE SPARROW_MESSAGE_ENTITY SET IS_DELETED = ? WHERE ID IN (?)";
}

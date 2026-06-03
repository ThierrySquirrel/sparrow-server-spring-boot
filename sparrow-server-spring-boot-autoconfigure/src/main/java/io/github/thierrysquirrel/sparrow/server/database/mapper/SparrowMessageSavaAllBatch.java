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

import io.github.thierrysquirrel.sparrow.server.database.id.SnowflakeId;
import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.SparrowMessageEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * ClassName: SparrowMessageSavaAllBatch
 * Description:
 * Date:2026/6/4
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class SparrowMessageSavaAllBatch implements BatchPreparedStatementSetter {
    private List<SparrowMessageEntity> sparrowMessageEntityList;

    public SparrowMessageSavaAllBatch(List<SparrowMessageEntity> sparrowMessageEntityList) {
        this.sparrowMessageEntityList = sparrowMessageEntityList;
    }

    public List<SparrowMessageEntity> getSparrowMessageEntityList() {
        return sparrowMessageEntityList;
    }

    public void setSparrowMessageEntityList(List<SparrowMessageEntity> sparrowMessageEntityList) {
        this.sparrowMessageEntityList = sparrowMessageEntityList;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, int index) throws SQLException {
        sparrowMessageEntityList.get(index).setId(SnowflakeId.nextId());
        preparedStatement.setLong(1, sparrowMessageEntityList.get(index).getId());
        preparedStatement.setString(2, sparrowMessageEntityList.get(index).getTopic());
        preparedStatement.setBytes(3, sparrowMessageEntityList.get(index).getMessage());
    }

    @Override
    public int getBatchSize() {
        return sparrowMessageEntityList.size();
    }

    @Override
    public String toString() {
        return "SparrowMessageSavaAllBatch{" +
                "sparrowMessageEntityList=" + sparrowMessageEntityList +
                '}';
    }
}

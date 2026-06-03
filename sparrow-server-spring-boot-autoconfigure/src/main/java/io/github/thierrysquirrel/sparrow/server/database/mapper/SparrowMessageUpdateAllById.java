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

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * ClassName: SparrowMessageUpdateAllById
 * Description:
 * Date:2026/6/4
 *
 * @author ThierrySquirrel
 * @since JDK25
 **/
public class SparrowMessageUpdateAllById implements BatchPreparedStatementSetter {
    private List<Long> idList;
    private Byte isDeleted;

    @Override
    public void setValues(PreparedStatement preparedStatement, int index) throws SQLException {
        preparedStatement.setByte(1, isDeleted);
        preparedStatement.setLong(2, idList.get(index));
    }

    public SparrowMessageUpdateAllById(List<Long> idList, Byte isDeleted) {
        this.idList = idList;
        this.isDeleted = isDeleted;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "SparrowMessageUpdateAllById{" +
                "idList=" + idList +
                ", isDeleted=" + isDeleted +
                '}';
    }

    @Override
    public int getBatchSize() {
        return idList.size();
    }
}

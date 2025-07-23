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
package io.github.thierrysquirrel.sparrow.server.database.service;

import io.github.thierrysquirrel.sparrow.server.common.netty.domain.SparrowMessage;
import io.github.thierrysquirrel.sparrow.server.core.container.ConsumerMessageQuery;
import io.github.thierrysquirrel.sparrow.server.core.container.constant.ConsumerMessageQueryConstant;
import io.github.thierrysquirrel.sparrow.server.core.utils.DomainUtils;
import io.github.thierrysquirrel.sparrow.server.database.mapper.SparrowMessageMapper;
import io.github.thierrysquirrel.sparrow.server.database.mapper.entity.SparrowMessageEntity;
import io.github.thierrysquirrel.sparrow.server.database.service.core.constant.SparrowMessageServiceConstant;
import io.github.thierrysquirrel.sparrow.server.database.service.core.container.DatabaseReadStateContainer;
import io.github.thierrysquirrel.sparrow.server.database.service.core.utils.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * ClassName: SparrowMessageService
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowMessageService {
    @Autowired
    private SparrowMessageMapper sparrowMessageMapper;

    public void initSparrowMessageEntity() {
        sparrowMessageMapper.initSparrowMessageEntity();
        sparrowMessageMapper.initIndexTopic();
        sparrowMessageMapper.initIndexIsDeleted();
    }

    public void saveAll(List<SparrowMessageEntity> sparrowMessageEntityList, String topic) {
        sparrowMessageMapper.saveAll(sparrowMessageEntityList);

        List<SparrowMessage> sparrowMessagesList = DomainUtils.convertList(sparrowMessageEntityList, SparrowMessage.class);
        ConsumerMessageQuery.putMessage(topic, sparrowMessagesList);
    }

    public void deleteAllTimeoutMessage() {
        Date gmtModified = DateUtils.getPastTime(SparrowMessageServiceConstant.RETENTION_TIME_DAY);
        sparrowMessageMapper.deleteAllByIsDeletedAndGmtCreateLessThanEqual(SparrowMessageServiceConstant.IS_DELETE, gmtModified, SparrowMessageServiceConstant.DELETE_MESSAGE_NUMBER);
    }

    public void findAllByTopic(String topic) {
        List<SparrowMessageEntity> sparrowMessageEntityList = sparrowMessageMapper.findAllByTopicAndIsDeleted(topic, SparrowMessageServiceConstant.IS_NOT_DELETE, SparrowMessageServiceConstant.FIND_ALL_MESSAGE_NUMBER);
        if (ObjectUtils.isEmpty(sparrowMessageEntityList)) {
            return;
        }
        List<SparrowMessage> sparrowMessageList = DomainUtils.convertList(sparrowMessageEntityList, SparrowMessage.class);
        List<List<SparrowMessage>> messageLists = Lists.partition(sparrowMessageList, ConsumerMessageQueryConstant.LIST_MESSAGE_NUMBER);
        for (List<SparrowMessage> messageList : messageLists) {
            ConsumerMessageQuery.putMessage(topic, messageList);
        }

        DatabaseReadStateContainer.tryCloseDatabaseRead(topic);
    }

    public void updateAllByIdList(List<Long> idList) {
        sparrowMessageMapper.updateAllById(idList, SparrowMessageServiceConstant.IS_DELETE);
    }


}

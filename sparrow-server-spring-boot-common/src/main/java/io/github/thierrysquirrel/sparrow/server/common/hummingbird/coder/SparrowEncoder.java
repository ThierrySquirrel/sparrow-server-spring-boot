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
package io.github.thierrysquirrel.sparrow.server.common.hummingbird.coder;

import io.github.thierrysquirrel.hummingbird.core.coder.HummingbirdEncoder;
import io.github.thierrysquirrel.hummingbird.core.facade.ByteBufferFacade;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.coder.constant.CoderIdentifyConstant;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.coder.utils.SerializerUtils;
import io.github.thierrysquirrel.sparrow.server.common.hummingbird.domain.SparrowRequestContext;

/**
 * ClassName: SparrowEncoder
 * Description:
 * Date:2024/8/9
 *
 * @author ThierrySquirrel
 * @since JDK21
 **/
public class SparrowEncoder implements HummingbirdEncoder<SparrowRequestContext> {

    @Override
    public void encoder(SparrowRequestContext message, ByteBufferFacade byteBufferFacade) {
        byteBufferFacade.putBytes(CoderIdentifyConstant.SPARROW.getValue());
        byte[] serialize = SerializerUtils.serialize(message);
        byteBufferFacade.putInt(serialize.length);
        byteBufferFacade.putBytes(serialize);
    }
}

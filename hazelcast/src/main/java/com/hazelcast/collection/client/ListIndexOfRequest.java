/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.collection.client;

import com.hazelcast.collection.CollectionPortableHook;
import com.hazelcast.collection.list.ListIndexOfOperation;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.security.permission.ActionConstants;
import com.hazelcast.spi.Operation;

import java.io.IOException;

/**
 * @ali 9/4/13
 */
public class ListIndexOfRequest extends CollectionRequest {

    Data value;

    boolean last;

    public ListIndexOfRequest() {
    }

    public ListIndexOfRequest(String name, Data value, boolean last) {
        super(name);
        this.value = value;
        this.last = last;
    }

    @Override
    protected Operation prepareOperation() {
        return new ListIndexOfOperation(name, last, value);
    }

    @Override
    public int getClassId() {
        return CollectionPortableHook.LIST_INDEX_OF;
    }

    @Override
    public void writePortable(PortableWriter writer) throws IOException {
        super.writePortable(writer);
        writer.writeBoolean("l", last);
        value.writeData(writer.getRawDataOutput());
    }

    @Override
    public void readPortable(PortableReader reader) throws IOException {
        super.readPortable(reader);
        last = reader.readBoolean("l");
        value = new Data();
        value.readData(reader.getRawDataInput());
    }

    @Override
    public String getRequiredAction() {
        return ActionConstants.ACTION_READ;
    }
}

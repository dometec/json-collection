/*
 * Copyright 2011 Erlend Hamnaberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.hamnaberg.json.generator;

import com.google.common.base.Function;
import net.hamnaberg.json.util.ListOps;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import java.util.List;

public abstract class AbstractGenerator<T> implements Generator<T> {
    protected final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

    protected void addArrayIfNotEmpty(ObjectNode node, String name, ArrayNode array) {
        if (array.size() != 0) {
            node.put(name, array);
        }
    }

    protected <T> ArrayNode createArray(List<T> value, Function<T, JsonNode> function) {
        ArrayNode itemsArray = nodeFactory.arrayNode();
        itemsArray.addAll(ListOps.transform(value, function));
        return itemsArray;
    }
}

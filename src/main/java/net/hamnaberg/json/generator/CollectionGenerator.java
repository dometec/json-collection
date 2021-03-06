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
import net.hamnaberg.json.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

public class CollectionGenerator extends AbstractGenerator<Collection> {
    private final GeneratorFactory factory;

    public CollectionGenerator() {
        factory = new CollectionJsonGenerator();
    }

    @Override
    public JsonNode toNode(Collection object) {
        ObjectNode node = nodeFactory.objectNode();
        node.put("href", object.getHref().toString());
        node.put("version", object.getVersion().getIdentifier());
        if (object.hasError()) {
            node.put("error", factory.generate(object.getError()));
        } else {
            addArrayIfNotEmpty(node, "items", createArray(object.getItems(), new Value2JsonNode<Item>()));
            addArrayIfNotEmpty(node, "links", createArray(object.getLinks(), new Value2JsonNode<Link>()));
            addArrayIfNotEmpty(node, "queries", createArray(object.getQueries(), new Value2JsonNode<Query>()));
            if (object.hasTemplate()) {
                node.put("template", factory.generate(object.getTemplate()));
            }
        }
        ObjectNode outer = nodeFactory.objectNode();
        outer.put("collection", node);
        return outer;
    }

    protected class Value2JsonNode<T> implements Function<T, JsonNode> {
        @Override
        public JsonNode apply(T input) {
            return factory.generate(input);
        }
    }

    private static class CollectionJsonGenerator extends GeneratorFactory {
        private CollectionJsonGenerator() {
            register(Link.class, new LinkGenerator());
            register(Property.class, new PropertyGenerator());
            register(Item.class, new ItemGenerator());
            register(ErrorMessage.class, new ErrorMessageGenerator());
            register(Template.class, new TemplateGenerator());
        }
    }
}

/*
 * Copyright (C) 2013 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package me.glindholm.jira.rest.client.test.matchers;

import com.google.common.collect.Lists;

import me.glindholm.jira.rest.client.api.AddressableEntity;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsListContainingInAnyOrder;

import java.net.URI;
import java.util.List;

public class AddressableEntityMatchers {

    public static Matcher<? super AddressableEntity> withSelf(URI uri) {
        return new FeatureMatcher<AddressableEntity, URI>(Matchers.is(uri), "entity with self that", "self") {

            @Override
            protected URI featureValueOf(AddressableEntity AddressableEntity) {
                return AddressableEntity.getSelf();
            }
        };
    }

    public static Matcher<List<? extends AddressableEntity>> entitiesWithSelf(URI... uris) {
        final List<Matcher<? super AddressableEntity>> matchers = Lists.newArrayListWithCapacity(uris.length);
        for (URI uri : uris) {
            matchers.add(withSelf(uri));
        }
        return IsListContainingInAnyOrder.containsInAnyOrder(matchers);
    }
}

/*
 * Copyright (C) 2012 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.glindholm.jira.rest.client.api.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.time.OffsetDateTime;

/**
 * Represents Issue change history group
 *
 * @since 0.6
 */
public class ChangelogGroup {
    private final BasicUser author;
    private final OffsetDateTime created;
    private final Iterable<ChangelogItem> items;

    public ChangelogGroup(BasicUser author, OffsetDateTime created, Iterable<ChangelogItem> items) {
        this.author = author;
        this.created = created;
        this.items = items;
    }

    public BasicUser getAuthor() {
        return author;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public Iterable<ChangelogItem> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChangelogGroup) {
            ChangelogGroup that = (ChangelogGroup) obj;
            return Objects.equal(this.author, that.author)
                    && Objects.equal(this.created, that.created)
                    && Objects.equal(this.items, that.items);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(author, created, items);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("author", author)
                .add("created", created)
                .add("items", items)
                .toString();
    }
}

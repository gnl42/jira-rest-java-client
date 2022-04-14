/*
 * Copyright (C) 2010 Atlassian
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

import java.net.URI;

import javax.annotation.Nullable;

import java.time.OffsetDateTime;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import me.glindholm.jira.rest.client.api.AddressableEntity;

/**
 * A file attachment attached to an issue
 *
 * @since v0.1
 */
public class Attachment implements AddressableEntity {
    private final long id;
    private final URI self;
    private final String filename;
    private final BasicUser author;
    private final OffsetDateTime creationDate;
    private final int size;
    private final String mimeType;
    private final URI contentUri;

    @Nullable
    private final URI thumbnailUri;

    public Attachment(long id, URI self, String filename, BasicUser author, OffsetDateTime creationDate, int size, String mimeType, URI contentUri,
            URI thumbnailUri) {
        this.id = id;
        this.self = self;
        this.filename = filename;
        this.author = author;
        this.creationDate = creationDate;
        this.size = size;
        this.mimeType = mimeType;
        this.contentUri = contentUri;
        this.thumbnailUri = thumbnailUri;
    }

    public boolean hasThumbnail() {
        return thumbnailUri != null;
    }

    public long getId() {
        return id;
    }

    @Override
    public URI getSelf() {
        return self;
    }

    public String getFilename() {
        return filename;
    }

    public BasicUser getAuthor() {
        return author;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public int getSize() {
        return size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public URI getContentUri() {
        return contentUri;
    }

    @Nullable
    public URI getThumbnailUri() {
        return thumbnailUri;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).
                add("self", self).
                add("filename", filename).
                add("author", author).
                add("creationDate", creationDate).
                add("size", size).
                add("mimeType", mimeType).
                add("contentUri", contentUri).
                add("thumbnailUri", thumbnailUri).
                toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Attachment) {
            Attachment that = (Attachment) obj;
            return Objects.equal(self, that.self)
                    && Objects.equal(filename, that.filename)
                    && Objects.equal(author, that.author)
                    && creationDate.isEqual(that.creationDate)
                    && Objects.equal(size, that.size)
                    && Objects.equal(mimeType, that.mimeType)
                    && Objects.equal(contentUri, that.contentUri)
                    && Objects.equal(thumbnailUri, that.thumbnailUri);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(self, filename, author, creationDate, size, mimeType, contentUri, thumbnailUri);
    }
}

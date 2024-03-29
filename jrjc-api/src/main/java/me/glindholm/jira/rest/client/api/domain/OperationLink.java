/*
 * Copyright (C) 2014 Atlassian
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

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Represents operations link
 *
 * @since 2.0
 */
public class OperationLink implements Serializable, Operation {
    private static final long serialVersionUID = 1L;

    @Nullable
    private final String id;
    @Nullable
    private final String styleClass;
    private final String label;
    @Nullable
    private final String title;
    private final String href;
    @Nullable
    private final Integer weight;
    @Nullable
    private final String iconClass;

    public OperationLink(@Nullable final String id, @Nullable final String styleClass, final String label, @Nullable final String title,
            final String href, @Nullable final Integer weight, @Nullable final String iconClass) {
        this.id = id;
        this.styleClass = styleClass;
        this.iconClass = iconClass;
        this.label = label;
        this.title = title;
        this.href = href;
        this.weight = weight;
    }

    @Nullable
    @Override
    public String getId() {
        return id;
    }

    @Override
    public <T> Optional<T> accept(final OperationVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Nullable
    public String getStyleClass() {
        return styleClass;
    }

    public String getLabel() {
        return label;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    @Nullable
    public Integer getWeight() {
        return weight;
    }

    @Nullable
    public String getIconClass() {
        return iconClass;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof OperationLink) {
            OperationLink that = (OperationLink) o;
            return Objects.equals(id, that.id)
                    && Objects.equals(styleClass, that.styleClass)
                    && Objects.equals(label, that.label)
                    && Objects.equals(title, that.title)
                    && Objects.equals(href, that.href)
                    && Objects.equals(weight, that.weight)
                    && Objects.equals(iconClass, that.iconClass);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, styleClass, label, title, href, weight, iconClass);
    }

    @Override
    public String toString() {
        return "OperationLink [id=" + id + ", styleClass=" + styleClass + ", label=" + label + ", title=" + title + ", href=" + href + ", weight=" + weight
                + ", iconClass=" + iconClass + "]";
    }
}

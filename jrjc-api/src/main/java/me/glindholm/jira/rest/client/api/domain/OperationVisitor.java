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

import java.util.Optional;

/**
 * Interface visit operation elements
 *
 * @see Operations#accept(OperationVisitor)
 * @see Operation#accept(OperationVisitor)
 * @since 2.0
 */
public interface OperationVisitor<T> {
    /**
     * Visits operation element
     *
     * @param operation operation element to be visited
     * @return Present value means "I've found a value let's finish visiting".
     * If absent traversal through operation elements will be continued.
     */
    Optional<T> visit(Operation operation);
}

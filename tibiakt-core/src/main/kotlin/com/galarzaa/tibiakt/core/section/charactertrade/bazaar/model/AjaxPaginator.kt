/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model

import com.galarzaa.tibiakt.core.pagination.Paginated

/**
 * A paginator that can be fetched via AJAX requests.
 */
public interface AjaxPaginator<T> : Paginated<T> {
    /**
     * Whether this result set was fully fetched or not.
     */
    public val isFullyFetched: Boolean
}

/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.client.exceptions

/**
 *  Exception thrown when Tibia.com returns a 403 status code.
 *
 *  Tibia.com returns a 403 status code when it detects that too many requests are being done.
 *  This has its own subclass to let the user decide to treat this differently than other network errors.
 */
public open class ForbiddenException(message: String? = null, cause: Throwable?) : NetworkException(message, cause)

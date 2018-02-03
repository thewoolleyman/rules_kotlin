/*
 * Copyright 2018 The Bazel Authors. All rights reserved.
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
package io.bazel.ruleskotlin.workers

open class Meta<T: Any>(
        private val id: String,
        private val defaultValue: T? = null
) {
    constructor(id: String) : this(id, null)

    /**
     * Gets a mandatory value.
     */
    fun mustGet(ctx: Context): T =
        ctx[this] ?: checkNotNull(defaultValue) { "mandatory meta parameter missing in context and does not have a default value" }

    /**
     * Gets an optional value, if it has not been bound the default value is used.
     */
    operator fun get(ctx: Context): T? {
        val res = ctx[this]
        return when {
            res != null -> res
            defaultValue != null -> defaultValue
            else -> null
        }
    }

    fun bind(ctx: Context, value: T) { check(ctx.putIfAbsent(this, value) == null) { "attempting to change bound meta variable: $id " } }
}
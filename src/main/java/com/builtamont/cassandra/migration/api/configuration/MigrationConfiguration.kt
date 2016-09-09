/**
 * File     : MigrationConfiguration.kt
 * License  :
 *   Original   - Copyright (c) 2015 - 2016 Contrast Security
 *   Derivative - Copyright (c) 2016 Citadel Technology Solutions Pte Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.builtamont.cassandra.migration.api.configuration

import com.builtamont.cassandra.migration.api.MigrationVersion
import com.builtamont.cassandra.migration.internal.util.StringUtils

/**
 * Main Cassandra migration configuration.
 */
// TODO: To be merged with `CassandraMigrationConfiguration`
class MigrationConfiguration : Configuration() {

    /**
     * Cassandra migration configuration properties.
     *
     * @param namespace The property namespace.
     * @param description The property description.
     */
    enum class MigrationProperty constructor(val namespace: String, val description: String) {
        SCRIPTS_ENCODING(BASE_PREFIX + "scripts.encoding", "Encoding for CQL scripts"),
        SCRIPTS_LOCATIONS(BASE_PREFIX + "scripts.locations", "Locations of the migration scripts in CSV format"),
        ALLOW_OUT_OF_ORDER(BASE_PREFIX + "scripts.allowoutoforder", "Allow out of order migration"),
        TARGET_VERSION(BASE_PREFIX + "version.target", "The target version. Migrations with a higher version number will be ignored.")
    }

    /**
     * The encoding of CQL migration scripts.
     * (default: UTF-8)
     */
    var encoding = "UTF-8"
      get set

    /**
     * Locations of the migration scripts in CSV format.
     * (default: db/migration)
     */
    var scriptsLocations = arrayOf("db/migration")
      get set

    /**
     * The target version. Migrations with a higher version number will be ignored.
     * (default: the latest version)
     */
    var target = MigrationVersion.LATEST
        get private set

    /**
     * Set the target version property from String value.
     *
     * @param target Target version as String.
     */
    fun setTargetFromString(target: String) {
        this.target = MigrationVersion.fromVersion(target)
    }

    /**
     * Allow out of order migrations.
     * (default: false)
     */
    var isAllowOutOfOrder = false
        get set

    /**
     * Set allow out of order migration property from String value.
     *
     * @param allowOutOfOrder Allow out of order as String.
     */
    fun setIsAllowOutOfOrderFromString(allowOutOfOrder: String) {
        this.isAllowOutOfOrder = allowOutOfOrder.toBoolean()
    }

    /**
     * MigrationConfig initialization.
     */
    init {
        val scriptsEncodingProp = System.getProperty(MigrationProperty.SCRIPTS_ENCODING.namespace)
        if (!scriptsEncodingProp.isNullOrBlank()) this.encoding = scriptsEncodingProp.trim()

        val targetVersionProp = System.getProperty(MigrationProperty.TARGET_VERSION.namespace)
        if (!targetVersionProp.isNullOrBlank()) setTargetFromString(targetVersionProp)

        val locationsProp = System.getProperty(MigrationProperty.SCRIPTS_LOCATIONS.namespace)
        if (!locationsProp.isNullOrBlank()) scriptsLocations = StringUtils.tokenizeToStringArray(locationsProp, ",")

        val allowOutOfOrderProp = System.getProperty(MigrationProperty.ALLOW_OUT_OF_ORDER.namespace)
        if (!allowOutOfOrderProp.isNullOrBlank()) setIsAllowOutOfOrderFromString(allowOutOfOrderProp)
    }

}
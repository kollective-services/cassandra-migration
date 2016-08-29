/**
 * File     : Initialize.kt
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
package com.builtamont.cassandra.migration.internal.command

import com.datastax.driver.core.Session

/**
 * Handles the initialize command.
 */
class Initialize {

    /**
     * Runs the initialization.
     *
     * @param session The Cassandra session connection to use to execute the migration.
     * @param keyspace The Cassandra keyspace to connect to.
     * @param migrationVersionTableName The Cassandra migration version table name.
     */
    fun run(session: Session, keyspace: com.builtamont.cassandra.migration.config.Keyspace, migrationVersionTableName: String) {
        val dao = com.builtamont.cassandra.migration.internal.dbsupport.SchemaVersionDAO(session, keyspace, migrationVersionTableName)
        dao.createTablesIfNotExist()
    }

}

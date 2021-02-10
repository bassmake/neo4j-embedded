package sk.bsmk

import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import io.kotest.engine.spec.tempdir
import org.neo4j.configuration.GraphDatabaseSettings
import org.neo4j.configuration.connectors.BoltConnector
import org.neo4j.configuration.helpers.SocketAddress
import org.neo4j.dbms.api.DatabaseManagementService
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder
import org.neo4j.graphdb.GraphDatabaseService

object Neo4jEmbeddedListener : TestListener {

    private var managementService: DatabaseManagementService? = null

    fun graphDb(): GraphDatabaseService = managementService!!.database(GraphDatabaseSettings.DEFAULT_DATABASE_NAME)

    override suspend fun beforeSpec(spec: Spec) {
        val neo4jDir = spec.tempdir().toPath()
        managementService = DatabaseManagementServiceBuilder(neo4jDir)
            .setConfig(GraphDatabaseSettings.mode, GraphDatabaseSettings.Mode.SINGLE)
            .setConfig(BoltConnector.enabled, true)
            .setConfig(BoltConnector.listen_address, SocketAddress(Neo4jService.hostname, Neo4jService.port))
            .build()
    }

    override suspend fun afterSpec(spec: Spec) {
        managementService!!.shutdown()
    }

}
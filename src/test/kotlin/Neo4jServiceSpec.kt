package sk.bsmk

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.engine.spec.tempdir
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.shouldBe
import org.neo4j.configuration.GraphDatabaseSettings
import org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME
import org.neo4j.configuration.connectors.BoltConnector
import org.neo4j.configuration.connectors.HttpConnector
import org.neo4j.configuration.helpers.SocketAddress
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder
import org.neo4j.graphdb.Label
import java.nio.file.Path
import java.util.*
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path

@ExperimentalPathApi
class Neo4jServiceSpec : ShouldSpec({


    should("return data from cypher query") {

        val neo4jDir = tempdir().toPath()
        val managementService = DatabaseManagementServiceBuilder(neo4jDir)
            .setConfig(GraphDatabaseSettings.mode, GraphDatabaseSettings.Mode.SINGLE)
            .setConfig(BoltConnector.enabled, true)
            .setConfig(BoltConnector.listen_address, SocketAddress(Neo4jService.hostname, Neo4jService.port))
            .build()

        val graphDb = managementService.database(DEFAULT_DATABASE_NAME)

        val label = "test node ${UUID.randomUUID()}"

        graphDb.beginTx().use { trx ->
            val node = trx.createNode()
            node.addLabel(Label.label(label))
            trx.commit()
        }

        Neo4jService.retrieveLabels() shouldHaveSingleElement label

        managementService.shutdown()

    }

})
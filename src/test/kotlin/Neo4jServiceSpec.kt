package sk.bsmk

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldHaveSingleElement
import org.neo4j.graphdb.Label
import java.util.*
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class Neo4jServiceSpec : ShouldSpec({

    listener(Neo4jEmbeddedListener)

    should("return data from cypher query") {

        val graphDb = Neo4jEmbeddedListener.graphDb()

        val label = "test node ${UUID.randomUUID()}"

        graphDb.beginTx().use { trx ->
            val node = trx.createNode()
            node.addLabel(Label.label(label))
            trx.commit()
        }

        Neo4jService.retrieveLabels() shouldHaveSingleElement label
    }

})
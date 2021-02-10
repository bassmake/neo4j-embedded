package sk.bsmk

import org.neo4j.driver.GraphDatabase

object Neo4jService {

    const val hostname = "localhost"
    const val port = 7687

    fun retrieveLabels(): List<String> {
        return GraphDatabase.driver("bolt://$hostname:$port").use { driver ->
            driver.session().use { session ->
                session.beginTransaction().use { trx ->
                    val result = trx.run("MATCH (n) RETURN n").single()
                    result.values().map { it.asNode().labels().first() }
                }
            }
        }
    }

}
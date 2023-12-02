package team.hlab.jpa.entitymanager

import jakarta.persistence.EntityManagerFactory
import org.junit.jupiter.api.Test
import team.hlab.jpa.SpringJpaTest

@SpringJpaTest
class EntityManagerTest(
    private val entityManagerFactory: EntityManagerFactory,
) {
    @Test
    fun create_entityManager_from_factory() {
        val em = entityManagerFactory.createEntityManager()
        val tx = em.transaction
        try {
            tx.begin()
            // logic
            tx.commit()
        } catch (e: Throwable) {
            tx.rollback()
        } finally {
            em.close()
        }
    }
}
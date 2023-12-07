package team.hlab.jpa.player

import jakarta.persistence.EntityManagerFactory
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import team.hlab.jpa.SpringJpaTest
import team.hlab.jpa.support.transactional
import java.lang.IllegalStateException
import java.util.UUID

@SpringJpaTest
class PlayerTest(
    private val entityManagerFactory: EntityManagerFactory,
) {
    @DisplayName("If reference entity isn't given, jpa can't save entity")
    @Test
    fun t1() {
        val em = entityManagerFactory.createEntityManager()
        val id = UUID.randomUUID().toString()

        em.transactional {
            assertThatThrownBy {
                // team is no persisted by persistence-context
                val team = TeamEntity(id = "1", name = "hlab")
                // player's team reference is set with "nullable=false"
                val player = PlayerEntity(id = id, name = "hubtwork", team = team)
                em.persist(player)
                // so it would be throw IllegalStateException -> TransientPropertyValueException
            }.isInstanceOf(IllegalStateException::class.java)
        }
    }

    @DisplayName("If reference entity is given properly, jpa can save entity")
    @Test
    fun t2() {
        val em = entityManagerFactory.createEntityManager()
        val teamId = UUID.randomUUID().toString()
        val playerId = UUID.randomUUID().toString()

        em.transactional {
            val team = TeamEntity(id = teamId, "hlab")
            val player = PlayerEntity(id = playerId, name = "hubtwork", team = team)
            // we kept ordering about team & player for persistence-context
            // so it would work properly
            em.persist(team)
            em.persist(player)
        }

        val em2 = entityManagerFactory.createEntityManager()
        em2.transactional {
            val findPlayer = em2.find(PlayerEntity::class.java, playerId)
            assertThat(findPlayer).isNotNull
            assertThat(findPlayer.id).isEqualTo(playerId)
            assertThat(findPlayer.team.id).isEqualTo(teamId)
        }
    }
}
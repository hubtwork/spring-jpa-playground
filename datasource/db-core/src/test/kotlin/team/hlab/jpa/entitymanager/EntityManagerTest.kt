package team.hlab.jpa.entitymanager

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import team.hlab.jpa.SpringJpaTest
import team.hlab.jpa.member.MemberEntity
import team.hlab.jpa.support.transactional
import java.util.UUID

@SpringJpaTest
class EntityManagerTest(
    private val entityManagerFactory: EntityManagerFactory,
) {
    @DisplayName("create EntityManager and Transaction from Factory")
    @Test
    fun t1() {
        val em = entityManagerFactory.createEntityManager()
        assertThat(em).isInstanceOf(EntityManager::class.java)
    }

    @DisplayName("when error thrown, transaction must rollback")
    @Test
    fun t2() {
        val id = UUID.randomUUID().toString()

        val em = entityManagerFactory.createEntityManager()
        em.transactional {
            val member = MemberEntity(id = id, username = "alen", age = 1, address = "hlab")
            em.persist(member)
            throw Exception("something wrong")
        }

        val em2 = entityManagerFactory.createEntityManager()
        val nullMember = em2.find(MemberEntity::class.java, id)
        assertThat(nullMember).isNull()
    }

    @DisplayName("when call persist, entityManager will save entity")
    @Test
    fun t3() {
        val id = UUID.randomUUID().toString()

        val em = entityManagerFactory.createEntityManager()
        em.transactional {
            val member = MemberEntity(id = id, username = "alen", age = 1, address = "hlab")
            em.persist(member)
        }

        val em2 = entityManagerFactory.createEntityManager()
        val findMember = em2.find(MemberEntity::class.java, id)
        assertThat(findMember).isNotNull
        assertThat(findMember.id).isEqualTo(id)
    }
}
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

    @DisplayName("when entity's data changed, update query need to be sent")
    @Test
    fun t4() {
        val id = UUID.randomUUID().toString()

        val em = entityManagerFactory.createEntityManager()
        val member = MemberEntity(id = id, username = "alen", age = 1, address = "hlab")
        em.transactional {
            em.persist(member)
            member.rename("arc")
            member.growUp(year = 1)
        }

        val em2 = entityManagerFactory.createEntityManager()
        val findMember = em2.find(MemberEntity::class.java, id)
        assertThat(findMember).isNotNull
        assertThat(findMember.age).isEqualTo(2)
    }

    @DisplayName("with jpql, get multiple result from db")
    @Test
    fun t5() {
        fun <T> List<T>.contentEquals(other: List<T>) = this.size == other.size && this.toSet() == other.toSet()

        val getQuery = "select m from MemberEntity m"
        val ids = (0..10).map { UUID.randomUUID().toString() }

        val em = entityManagerFactory.createEntityManager()
        // do not open transaction longer like this in production codes.
        em.transactional {
            ids.forEachIndexed { idx, id ->
                val member = MemberEntity(id = id, username = "user#$idx", age = idx, address = "address")
                em.persist(member)
            }
            // query would be sent to DB for checking all MemberEntity ( Not use 1st cache )
            val members = em.createQuery(getQuery, MemberEntity::class.java).resultList
            assertThat(members.size).isEqualTo(ids.size)
            assertThat(members.contentEquals(ids)).isTrue()
        }

        val em2 = entityManagerFactory.createEntityManager()
        em2.transactional {
            // query would be sent to DB for checking all MemberEntity ( Not use 1st cache )
            val members = em2.createQuery(getQuery, MemberEntity::class.java).resultList
            assertThat(members.size).isEqualTo(ids.size)
            assertThat(members.contentEquals(ids)).isTrue()
            // query would not be sent because save entities on persistence context at previous jpql's result
            val member = em2.find(MemberEntity::class.java, ids[0])
            assertThat(member).isNotNull
        }
    }

    @DisplayName("when entity detached from context, any persistence-context's functions would not be supported")
    @Test
    fun t6() {
        val id = UUID.randomUUID().toString()

        val em = entityManagerFactory.createEntityManager()
        val member = MemberEntity(id = id, username = "alen", age = 1, address = "hlab")
        em.transactional {
            em.persist(member)
            // detach -> insert query on lazy-query-storage and 1st-cache will be removed
            em.detach(member)
            val nullMember = em.find(MemberEntity::class.java, id)
            // -> will send query to DB (flush) to find member with "id"
            // (because context don't know about MemberEntity with given ID)
            assertThat(nullMember).isNull()
        }
    }

    @DisplayName("when merge function called, EntityManager will execute \"save or update\" on given entity")
    @Test
    fun t7() {
        val id = UUID.randomUUID().toString()
        val member = MemberEntity(id = id, username = "alen", age = 1, address = "hlab")

        val em = entityManagerFactory.createEntityManager()
        em.transactional {
            em.persist(member)
        }
        // member is currently detached ( EM1 is closed )
        member.growUp(9)
        assertThat(member.id).isEqualTo(id)

        val em2 = entityManagerFactory.createEntityManager()
        em2.transactional {
            // load from DB and create snapshot on EM2's Persistence Context
            em2.merge(member)
            // but snapshot's age would be 1, so update query would be sent
        }
    }
}
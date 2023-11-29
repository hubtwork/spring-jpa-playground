package team.hlab.jpa

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

fun TestEntityManager.persistAll(vararg entity: BaseEntity) {
    entity.forEach { persist(it) }
    flush()
    clear()
}
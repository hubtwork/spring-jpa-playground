package team.hlab.jpa.support

import jakarta.persistence.EntityManager

fun EntityManager.transactional(logic: () -> Unit) {
    val tx = transaction
    try {
        tx.begin()
        logic()
        tx.commit()
    } catch (e: Throwable) {
        tx.rollback()
    } finally {
        close()
    }
}
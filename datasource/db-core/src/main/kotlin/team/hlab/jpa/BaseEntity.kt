package team.hlab.jpa

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime

/**
 * Base Entity class by [MappedSuperclass]
 *
 * @property id Sequence ID
 * @property createdAt dateTime when entity is first created.
 * @property updatedAt dateTime when entity is last modified.
 */
@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
open class BaseEntity : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0L

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: LocalDateTime? = null

    @UpdateTimestamp
    @Column
    var updatedAt: LocalDateTime? = null
        protected set
}
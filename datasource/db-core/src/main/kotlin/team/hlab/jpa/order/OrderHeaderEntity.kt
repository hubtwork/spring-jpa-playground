package team.hlab.jpa.order

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "ORDER")
class OrderHeaderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    val id: Long,
    @Column(name = "MEMBER_ID")
    val memberId: String,
    @Column(name = "ORDER_REQUEST_DATE")
    val requestDate: LocalDateTime,
    @Enumerated(value = EnumType.STRING)
    val status: Status,
) {
    enum class Status {
        PENDING,
        CANCELED,
    }
}
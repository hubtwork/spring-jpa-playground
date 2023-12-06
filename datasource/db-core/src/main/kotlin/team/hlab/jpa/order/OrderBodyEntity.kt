package team.hlab.jpa.order

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "ORDER_BODY")
class OrderBodyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_BODY_ID")
    val id: Long,
    @Column(name = "ORDER_HEADER_ID")
    val headerId: Long,
    @Column(name = "ORDER_PRODUCT_ID")
    val itemId: String,
    @Column(name = "ORDER_PRODUCT_COUNT")
    val itemCount: Long,
)
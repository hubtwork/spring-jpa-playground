package team.hlab.jpa.member

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Entity
@Table(name = "MEMBER")
class MemberAdminEntity(
    @Id
    @Column(name = "ID")
    val id: String,
    @Column(name = "NAME", nullable = false, length = 10)
    val username: String,
    @Column(name = "AGE", nullable = false)
    val age: Int,
    @Column(name = "ADDRESS")
    val address: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    val gender: Gender,
    @Column(name = "EMAIL")
    val email: String,
) {
    override fun toString(): String {
        return "id: $id, username: $username, age: $age, email: $email, gender: $gender, address: $address"
    }

    enum class Gender {
        MALE,
        FEMALE,
    }
}

@Repository
interface MemberAdminJpaRepository : JpaRepository<MemberAdminEntity, String>
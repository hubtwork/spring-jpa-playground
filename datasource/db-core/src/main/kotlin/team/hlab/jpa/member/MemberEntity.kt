package team.hlab.jpa.member

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Entity
@Table(name = "MEMBER")
class MemberEntity(
    @Id
    @Column(name = "ID")
    val id: String,
    username: String,
    age: Int,
    @Column(name = "ADDRESS")
    val address: String,
) {
    @Column(name = "NAME")
    var username: String = username
        protected set

    @Column(name = "AGE")
    var age: Int = age
        protected set

    fun rename(name: String) {
        username = name
    }

    fun growUp(year: Int) {
        age += year
    }

    override fun toString(): String {
        return "id: $id, username: $username, age: $age, address: $address"
    }
}

@Repository
interface MemberJpaRepository : JpaRepository<MemberEntity, String>
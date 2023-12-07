package team.hlab.jpa.player

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "TEAM")
class TeamEntity(
    @Id
    @Column(name = "TEAM_ID")
    val id: String,
    val name: String,
)
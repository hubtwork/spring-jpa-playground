package team.hlab.jpa.player

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class PlayerEntity(
    @Id
    @Column(name = "PLAYER_ID")
    val id: String,
    val name: String,
    team: TeamEntity,
) {
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    var team: TeamEntity = team
        protected set

    fun changeTeam(nextTeam: TeamEntity) {
        this.team = nextTeam
    }
}
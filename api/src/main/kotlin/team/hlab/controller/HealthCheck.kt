package team.hlab.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/health")
@RestController
class HealthCheck {
    @GetMapping
    fun healthCheckRoute(): String {
        return "ok"
    }
}
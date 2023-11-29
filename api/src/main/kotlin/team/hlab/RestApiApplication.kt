package team.hlab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class RestApiApplication

fun main(args: Array<String>) {
    runApplication<RestApiApplication>(*args)
}
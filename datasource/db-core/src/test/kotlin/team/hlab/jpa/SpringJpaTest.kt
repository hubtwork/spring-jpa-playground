package team.hlab.jpa

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestConstructor

@ComponentScan
@EnableJpaAuditing
@EnableAutoConfiguration
private class SpringJpaTestConfiguration

@ActiveProfiles("local")
@ContextConfiguration(classes = [SpringJpaTestConfiguration::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
annotation class SpringJpaTest
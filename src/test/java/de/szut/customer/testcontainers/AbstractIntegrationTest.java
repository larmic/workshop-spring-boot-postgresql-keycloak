package de.szut.customer.testcontainers;

import de.szut.customer.database.CustomerJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("it")
@ContextConfiguration(initializers = PostgresContextInitializer.class)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected CustomerJpaRepository customerJpaRepository;

    @BeforeEach
    void setUp() {
        this.customerJpaRepository.deleteAll();
    }
}

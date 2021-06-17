package de.szut.customer.database;

import de.szut.customer.database.model.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;

class InMemoryCustomerRepositoryTest {

    private final InMemoryCustomerRepository repository = new InMemoryCustomerRepository();

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("Save with")
    class Save {

        @Test
        void idIsSet() {
            final var customer = new CustomerEntity("test-name", "test-company");
            customer.setId(15L);
            final var savedCustomer = repository.save(customer);

            assertThat(savedCustomer.getId()).isEqualTo(15L);
            assertThat(savedCustomer.getName()).isEqualTo("test-name");
            assertThat(savedCustomer.getCompany()).isEqualTo("test-company");
            assertThat(savedCustomer.getCreateDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
            assertThat(savedCustomer.getLastUpdateDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
        }

        @Test
        void idIsNotSet() {
            final var customer = new CustomerEntity("test-name", "test-company");
            final var savedCustomer = repository.save(customer);

            assertThat(savedCustomer.getId()).isEqualTo(1L);
            assertThat(savedCustomer.getName()).isEqualTo("test-name");
            assertThat(savedCustomer.getCompany()).isEqualTo("test-company");
            assertThat(savedCustomer.getCreateDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
            assertThat(savedCustomer.getLastUpdateDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
        }
    }

    @Nested
    @DisplayName("ExistsById with")
    class ExistsById {

        @Test
        void customerNotExists() {
            assertThat(repository.existsById(1L)).isFalse();
        }

        @Test
        void customerExists() {
            final var customer = new CustomerEntity("test-name", "test-company");
            repository.save(customer);

            assertThat(repository.existsById(1L)).isTrue();
        }
    }

    @Nested
    @DisplayName("GetById with")
    class GetById {

        @Test
        void customerNotExists() {
            assertThat(repository.getById(1L)).isNull();
        }

        @Test
        void customerExists() {
            final var customer = new CustomerEntity("test-name", "test-company");
            repository.save(customer);

            assertThat(repository.existsById(1L)).isNotNull();
        }
    }

    @Test
    void findAll() {
        repository.save(new CustomerEntity("test-name-1", "test-company-1"));
        repository.save(new CustomerEntity("test-name-2", "test-company-2"));
        repository.save(new CustomerEntity("test-name-3", "test-company-3"));

        assertThat(repository.findAll())
            .extracting(CustomerEntity::getId, CustomerEntity::getName, CustomerEntity::getCompany)
            .containsExactlyInAnyOrder(
                tuple(1L, "test-name-1", "test-company-1"),
                tuple(2L, "test-name-2", "test-company-2"),
                tuple(3L, "test-name-3", "test-company-3")
            );
    }
}
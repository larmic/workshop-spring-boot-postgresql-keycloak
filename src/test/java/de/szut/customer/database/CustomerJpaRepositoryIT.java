package de.szut.customer.database;

import de.szut.customer.database.model.CustomerEntity;
import de.szut.customer.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;

class CustomerJpaRepositoryIT extends AbstractIntegrationTest {

    @BeforeEach
    void setUp() {
        customerJpaRepository.deleteAll();
    }

    @Nested
    @DisplayName("Save with")
    class Save {

        @Test
        void idIsSet() {
            final var customer = customerJpaRepository.save(new CustomerEntity("first-test-name", "first-test-company"));
            final var updatedCustomer = new CustomerEntity("second-test-name", "second-test-company");
            updatedCustomer.setId(customer.getId());
            final var savedCustomer = customerJpaRepository.save(updatedCustomer);

            assertThat(savedCustomer.getId()).isNotNull();
            assertThat(savedCustomer.getName()).isEqualTo("second-test-name");
            assertThat(savedCustomer.getCompany()).isEqualTo("second-test-company");
            assertThat(savedCustomer.getCreateDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
            assertThat(savedCustomer.getLastUpdateDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
        }

        @Test
        void idIsNotSet() {
            final var customer = new CustomerEntity("test-name", "test-company");
            final var savedCustomer = customerJpaRepository.save(customer);

            assertThat(savedCustomer.getId()).isNotNull();
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
            assertThat(customerJpaRepository.existsById(1L)).isFalse();
        }

        @Test
        void customerExists() {
            final var customer = new CustomerEntity("test-name", "test-company");
            customerJpaRepository.save(customer);

            assertThat(customerJpaRepository.existsById(customer.getId())).isTrue();
        }
    }

    @Nested
    @DisplayName("GetOne with")
    class GetOne {

        @Test
        void customerExists() {
            final var customer = new CustomerEntity("test-name", "test-company");
            customerJpaRepository.save(customer);

            assertThat(customerJpaRepository.getOne(customer.getId()).getId()).isEqualTo(customer.getId());
        }
    }

    @Nested
    @DisplayName("DeleteById with")
    class DeleteById {

        @Test
        void customerNotExists() {
            assertThatThrownBy(() -> customerJpaRepository.deleteById(1L))
                .isInstanceOf(EmptyResultDataAccessException.class)
                .hasMessage("No class de.szut.customer.database.model.CustomerEntity entity with id 1 exists!");
        }

        @Test
        void customerExists() {
            final var customer = new CustomerEntity("test-name", "test-company");
            customerJpaRepository.save(customer);

            customerJpaRepository.deleteById(customer.getId());

            assertThat(customerJpaRepository.existsById(customer.getId())).isFalse();
        }
    }

    @Test
    void findAll() {
        customerJpaRepository.save(new CustomerEntity("test-name-1", "test-company-1"));
        customerJpaRepository.save(new CustomerEntity("test-name-2", "test-company-2"));
        customerJpaRepository.save(new CustomerEntity("test-name-3", "test-company-3"));

        assertThat(customerJpaRepository.findAll())
            .extracting(CustomerEntity::getId, CustomerEntity::getName, CustomerEntity::getCompany)
            .containsExactlyInAnyOrder(
                tuple(1L, "test-name-1", "test-company-1"),
                tuple(2L, "test-name-2", "test-company-2"),
                tuple(3L, "test-name-3", "test-company-3")
            );
    }
}
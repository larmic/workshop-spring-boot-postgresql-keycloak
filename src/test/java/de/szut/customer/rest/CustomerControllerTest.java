package de.szut.customer.rest;

import de.szut.customer.database.CustomerJpaRepository;
import de.szut.customer.database.InMemoryCustomerRepository;
import de.szut.customer.database.model.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {CustomerController.class, InMemoryCustomerRepository.class})
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerJpaRepository customerRepository;

    @BeforeEach
    void setUp() {
        // Return saved customer on calling save method and set ID to 1
        when(customerRepository.save(any())).thenAnswer((Answer<CustomerEntity>) invocation -> {
            final var entity = (CustomerEntity) invocation.getArguments()[0];
            entity.setId(1L);
            return entity;
        });
    }

    @Nested
    @DisplayName("createCustomer() with")
    class CreateCustomer {

        @Test
        @DisplayName("body is empty")
        void bodyIsEmpty() throws Exception {
            mockMvc.perform(post("/").content(""))
                .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("body is not set")
        void bodyIsNull() throws Exception {
            mockMvc.perform(post("/"))
                .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("body is set")
        void bodyIsNotEmpty() throws Exception {
            mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"test-name\",\"company\":\"test-company\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("1")))
                .andExpect(jsonPath("name", is("test-name")))
                .andExpect(jsonPath("company", is("test-company")));

            final var argumentCaptor = ArgumentCaptor.forClass(CustomerEntity.class);
            verify(customerRepository).save(argumentCaptor.capture());
            assertThat(argumentCaptor.getValue().getName()).isEqualTo("test-name");
            assertThat(argumentCaptor.getValue().getCompany()).isEqualTo("test-company");
            assertThat(argumentCaptor.getValue().getCreateDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
            assertThat(argumentCaptor.getValue().getLastUpdateDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
        }
    }

    @Nested
    @DisplayName("getCustomer() with")
    class GetCustomer {

        @Test
        @DisplayName("customer not exists")
        void customerNotExists() throws Exception {
            mockMvc.perform(get("/17090"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
        }

        @Test
        @DisplayName("customer exists")
        void customerExists() throws Exception {
            final var customer = addCustomerToDatabase("second test customer", "second test customers company");

            mockMvc.perform(get("/" + customer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(customer.getId().toString())))
                .andExpect(jsonPath("name", is(customer.getName())))
                .andExpect(jsonPath("company", is(customer.getCompany())));
        }
    }

    @Nested
    @DisplayName("getAllCustomers() with")
    class GetAllCustomers {

        @Test
        @DisplayName("customers are empty")
        void customersAreEmpty() throws Exception {
            mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
        }

        @Test
        @DisplayName("customers are not empty")
        void customersAreNotEmpty() throws Exception {
            final var customer = addCustomerToDatabase("third test customer", "third test customers company");

            mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(customer.getId().toString())))
                .andExpect(jsonPath("$[0].name", is(customer.getName())))
                .andExpect(jsonPath("$[0].company", is(customer.getCompany())));
        }
    }

    @Nested
    @DisplayName("updateCustomer() with")
    class UpdateCustomer {

        @Test
        @DisplayName("customer not exists")
        void customerAreEmpty() throws Exception {
            mockMvc.perform(put("/1875700").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"test-name\",\"company\":\"test-company\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
        }

        @Test
        @DisplayName("customer exists")
        void customerAreNotEmpty() throws Exception {
            final var customer = addCustomerToDatabase("fourth test customer", "fourth test customers company");

            mockMvc.perform(put("/" + customer.getId()).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"other-name\",\"company\":\"other-company\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(customer.getId().toString())))
                .andExpect(jsonPath("name", is("other-name")))
                .andExpect(jsonPath("company", is("other-company")));
        }
    }

    @Nested
    @DisplayName("deleteCustomer() with")
    class DeleteCustomer {

        @Test
        @DisplayName("customer not exists")
        void customerAreEmpty() throws Exception {
            mockMvc.perform(delete("/76"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
        }

        @Test
        @DisplayName("customer exists")
        void customerAreNotEmpty() throws Exception {
            final var customer = addCustomerToDatabase("fifth test customer", "fifth test customers company");

            mockMvc.perform(delete("/" + customer.getId()))
                .andExpect(status().isOk());
        }
    }

    private CustomerEntity addCustomerToDatabase(final String name, final String company) {
        final var customer = new CustomerEntity(name, company);
        customer.setId(1L);

        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.getOne(1L)).thenReturn(customer);
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

        return customer;
    }
}
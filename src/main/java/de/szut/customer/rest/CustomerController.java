package de.szut.customer.rest;

import de.szut.customer.database.CustomerJpaRepository;
import de.szut.customer.database.model.CustomerEntity;
import de.szut.customer.rest.dto.AddCustomerDto;
import de.szut.customer.rest.dto.ReadCustomerDto;
import de.szut.customer.rest.dto.UpdateCustomerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private final CustomerJpaRepository customerRepository;

    public CustomerController(CustomerJpaRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/")
    public ReadCustomerDto createCustomer(@RequestBody final AddCustomerDto customer) {
        final var entity = this.customerRepository.save(new CustomerEntity(customer.getName(), customer.getCompany()));

        return mapToDto(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadCustomerDto> getCustomer(@PathVariable final String id) {
        if (this.customerRepository.existsById(Long.valueOf(id))) {
            final var entity = this.customerRepository.getOne(Long.valueOf(id));
            return ResponseEntity.ok(mapToDto(entity));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public Collection<ReadCustomerDto> getAllCustomers() {
        return this.customerRepository.findAll().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReadCustomerDto> updateCustomer(@PathVariable final String id,
                                                          @RequestBody final UpdateCustomerDto customer) {
        if (this.customerRepository.existsById(Long.valueOf(id))) {
            final var entity = this.customerRepository.getOne(Long.valueOf(id));
            entity.setName(customer.getName());
            entity.setCompany(customer.getCompany());
            entity.setLastUpdateDate(LocalDateTime.now());
            this.customerRepository.save(entity);

            return ResponseEntity.ok(mapToDto(entity));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable final String id) {
        if (this.customerRepository.existsById(Long.valueOf(id))) {
            this.customerRepository.deleteById(Long.valueOf(id));
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    private ReadCustomerDto mapToDto(final CustomerEntity entity) {
        return new ReadCustomerDto(entity.getId().toString(), entity.getName(), entity.getCompany());
    }
}

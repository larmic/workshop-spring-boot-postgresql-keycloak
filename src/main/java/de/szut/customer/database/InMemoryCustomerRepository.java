package de.szut.customer.database;

import de.szut.customer.database.model.CustomerEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryCustomerRepository {

    private static final Map<Long, CustomerEntity> customers = new HashMap<>();

    public CustomerEntity save(final CustomerEntity customer) {
        if (customer.getId() == null) {
            customer.setId((long) customers.size() + 1);
        }

        customers.put(customer.getId(), customer);
        return customer;
    }

    public boolean existsById(final Long id) {
        return customers.get(id) != null;
    }

    public CustomerEntity getById(final Long id) {
        return customers.get(id);
    }

    public List<CustomerEntity> findAll() {
        return new ArrayList<>(customers.values());
    }

    public void deleteById(final Long id) {
        customers.remove(id);
    }

    public void deleteAll() {
        customers.clear();
    }
}

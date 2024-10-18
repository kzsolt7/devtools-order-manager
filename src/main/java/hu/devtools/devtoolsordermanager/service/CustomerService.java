package hu.devtools.devtoolsordermanager.service;

import hu.devtools.devtoolsordermanager.entity.Customer;
import hu.devtools.devtoolsordermanager.entity.Note;
import hu.devtools.devtoolsordermanager.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        if (customer.getContacts() != null) {
            customer.getContacts().forEach(contact -> contact.setCustomer(customer));
        }
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void updateCustomer(Long id, Customer customerDetails) {
        Customer existingCustomer = getCustomerById(id);
        existingCustomer.setCompanyName(customerDetails.getCompanyName());
        existingCustomer.setAddress(customerDetails.getAddress());
        existingCustomer.setActivity(customerDetails.getActivity());
        existingCustomer.setTaxNumber(customerDetails.getTaxNumber());

        if (customerDetails.getContacts() != null) {
            existingCustomer.getContacts().clear();
            customerDetails.getContacts().forEach(contact -> contact.setCustomer(existingCustomer));
            existingCustomer.getContacts().addAll(customerDetails.getContacts());
        }
        customerRepository.save(existingCustomer);
    }

    public void addNoteToCustomer(Long customerId, Note note) {
        Customer customer = getCustomerById(customerId);

        if (customer.getNotes() == null) {
            customer.setNotes(new ArrayList<>());
        }

        Note newNote = new Note();
        newNote.setCustomer(customer);
        newNote.setContent(note.getContent());
        newNote.setCreatedAt(LocalDateTime.now());

        customer.getNotes().add(newNote);

        customerRepository.save(customer);
    }

}

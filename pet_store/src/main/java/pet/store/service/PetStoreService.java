package pet.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.model.PetStoreData;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class PetStoreService {

 @Autowired
 private PetStoreDao petStoreDao;

 @Autowired
 private EmployeeDao employeeDao;

 @Autowired
 private CustomerDao customerDao;

 @Transactional(readOnly = false)
 public PetStoreData savePetStore(PetStoreData petStoreData) {
  Long petStoreId = petStoreData.getPetStoreId();
  PetStore petStore;

  if (petStoreId == null) {
   petStore = findOrCreatePetStore(petStoreId);
  } else {
   petStore = findPetStoreById(petStoreId);
   if (petStore == null) {
    throw new NoSuchElementException("Pet store with ID= " + petStoreId + " does not exist");
   }
  }

  copyPetStore(petStore, petStoreData);
  return new PetStoreData(petStoreDao.save(petStore));
 }

 private void copyPetStore(PetStore petStore, PetStoreData petStoreData) {
  petStore.setPetStoreId(petStoreData.getPetStoreId());
  petStore.setPetStoreName(petStoreData.getPetStoreName());
  petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
  petStore.setPetStoreCity(petStoreData.getPetStoreCity());
  petStore.setPetStoreState(petStoreData.getPetStoreState());
  petStore.setPetStoreZip(petStoreData.getPetStoreZip());
  petStore.setPetStorePhone(petStoreData.getPetStorePhone());
 }

 private PetStore findOrCreatePetStore(Long petStoreId) {
  PetStore petStore;

  if (Objects.isNull(petStoreId)) {
   petStore = new PetStore();
  } else {
   petStore = findPetStoreById(petStoreId);
  }
  return petStore;
 }

 private PetStore findPetStoreById(Long petStoreId) {
  return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("Contributor with ID=" + petStoreId +
          " was not found"));
 }

 @Transactional(readOnly = false)
 public PetStoreData.PetStoreEmployee saveEmployee(Long petStoreId, PetStoreData.PetStoreEmployee petStoreEmployee) {
  PetStore petStore = findPetStoreById(petStoreId);
  Long employeeId = petStoreEmployee.getEmployeeId();
  Employee employee = findOrCreateEmployee(petStoreEmployee.getEmployeeId(), employeeId);

  copyEmployeeFields(employee, petStoreEmployee);

  employee.setPetStore(petStore);
  petStore.getEmployees().add(employee);
  Employee dbEmployee = employeeDao.save(employee);

  return new PetStoreData.PetStoreEmployee(dbEmployee);
 }

 private Employee findEmployeeById(Long employeeId, Long petStoreId) {
  Employee employee = employeeDao.findById(employeeId)
          .orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found"));

  if (petStoreId.equals(employee.getPetStore().getPetStoreId())) {
   return employee;
  } else {
   throw new IllegalArgumentException("Employee with Id= " + employeeId + " does not match the pet store id of " + petStoreId);
  }
 }

 private Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {
  Employee employee;

  if (Objects.isNull(employeeId)) {
   employee = new Employee();
  } else {
   employee = findEmployeeById(petStoreId, employeeId);
  }
  return employee;
 }

 private void copyEmployeeFields(Employee employee, PetStoreData.PetStoreEmployee petStoreEmployee) {
  employee.setEmployeeId(petStoreEmployee.getEmployeeId());
  employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
  employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
  employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
  employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
 }

 @Transactional(readOnly = false)
 public PetStoreData.PetStoreCustomer saveCustomer(Long petStoreId, PetStoreData.PetStoreCustomer petStoreCustomer) {
  PetStore petStore = findPetStoreById(petStoreId);
  Long customerID = petStoreCustomer.getCustomerId();
  Customer customer = findOrCreateCustomer(petStoreCustomer.getCustomerId(), customerID);

  copyCustomerFields(customer, petStoreCustomer);

  customer.setCustomerId(customerID);
  petStore.getCustomers().add(customer);
  Customer dbCustomer = customerDao.save(customer);

  return new PetStoreData.PetStoreCustomer(dbCustomer);
 }

 private Customer findCustomerById(Long customerId, Long petStoreId) {
  Customer customer = customerDao.findById(customerId)
          .orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found"));

  if (petStoreId.equals(customer.getCustomerId())) {
   return customer;
  } else {
   throw new IllegalArgumentException("Customer with Id= " + customerId + " does not match the pet store id of " + petStoreId);
  }
 }

 private Customer findOrCreateCustomer(Long customerId, Long petStoreId) {
  Customer customer;

  if (Objects.isNull(customerId)) {
   customer = new Customer();
  } else {
   customer = findCustomerById(petStoreId, customerId);
  }

  for (PetStore petStore : customer.getPetStores()) {
   if (Objects.equals(petStore.getPetStoreId(), petStoreId)) {
    customer.getPetStores().add(petStore);
   }
  }
  return customer;
 }

 private void copyCustomerFields(Customer customer, PetStoreData.PetStoreCustomer petStoreCustomer) {
  customer.setCustomerId(petStoreCustomer.getCustomerId());
  customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
  customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
  customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
 }
}






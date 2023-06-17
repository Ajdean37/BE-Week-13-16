package pet.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.model.PetStoreData;
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
 public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
  PetStore petStore = findPetStoreById(petStoreId);
  Long employeeId = petStoreEmployee.getEmployeeId();
  Employee employee = findOrCreateEmployee(petStoreId, employeeId);

  copyEmployeeFields(employee, petStoreEmployee);

  employee.setPetStore(petStore);
  petStore.getEmployees().add(employee);
  Employee dbEmployee = employeeDao.save(employee);

  return new PetStoreEmployee(dbEmployee);
 }

 private Employee findEmployeeById(Long petStoreId, Long employeeId) {
  Employee employee = employeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId +
    " was not found"));

   if (petStoreId.equals(employee.getPetStore().getPetStoreId()) ) {
    return employee;
   } else {
    throw new IllegalArgumentException("Employee with Id= " + employeeId + " does not match the pet store id of " + petStoreId);
   }
//  petStoreDao.findById(petStoreId).orElseThrow(() -> new IllegalArgumentException("Pet store with ID=" + petStoreId +
//    " was not found"));
//  return employee;
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

 private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
  employee.setEmployeeId(petStoreEmployee.getEmployeeId());
  employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
  employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
  employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
  employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
 }
}






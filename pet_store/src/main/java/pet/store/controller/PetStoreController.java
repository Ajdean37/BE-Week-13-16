package pet.store.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pet.store.controller.model.PetStoreData;
import pet.store.entity.PetStore;
import pet.store.service.PetStoreService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

 @Autowired
 private PetStoreService petStoreService;

 @PostMapping("/store")
 @ResponseStatus(code = HttpStatus.CREATED)
 public PetStoreData createPetStoreData(@RequestBody PetStoreData petStoreData) {
  log.info("Creating Pet Store {}", petStoreData);
  return petStoreService.savePetStore(petStoreData);
 }

 @PutMapping("/store/{petStoreId}")
 @ResponseStatus(code = HttpStatus.CREATED)
 public PetStoreData modifyPetStoreData(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
  petStoreData.setPetStoreId(petStoreId);
  log.info("Modifying Pet Store {}", petStoreData);
  return petStoreService.savePetStore(petStoreData);
 }

 @PostMapping("/store/{petStoreId}/employee")
 @ResponseStatus(code = HttpStatus.CREATED)
 public PetStoreData.PetStoreEmployee addPetStoreEmployee(@PathVariable Long petStoreId,
                                                          @RequestBody PetStoreData.PetStoreEmployee petStoreEmployee) {
  log.info("Adding pet store employee {} {}", petStoreId, petStoreEmployee);
  return petStoreService.saveEmployee(petStoreId, petStoreEmployee);

 }

 @PostMapping("/store/{petStoreId}/customer")
 @ResponseStatus(code = HttpStatus.CREATED)
 public PetStoreData.PetStoreCustomer addPetStoreCustomer(@PathVariable Long petStoreId,
                                                          @RequestBody PetStoreData.PetStoreCustomer petStoreCustomer) {
  log.info("Adding pet store employee {} {}", petStoreId, petStoreCustomer);
  return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
 }

 @GetMapping
 public List<PetStoreData> listAllPetStores() {
  return petStoreService.retrieveAllPetStores();
 }

 @GetMapping("/store/{petStoreId}")
 public PetStoreData listPetStoreById(@PathVariable("petStoreId") Long petStoreId) {
  return petStoreService.retrievePetStoreById(petStoreId);
 }

 @DeleteMapping("/store/{petStoreId}")
 public Map<String, String> deletePetStoreById(@PathVariable("petStoreId") Long petStoreId) {
  log.info("Deleting pet store id {}", petStoreId);
  petStoreService.deletePetStoreById(petStoreId);
  return Map.of("message", "Deletion of pet store with Id= " + petStoreId + " was successful");
 }

}

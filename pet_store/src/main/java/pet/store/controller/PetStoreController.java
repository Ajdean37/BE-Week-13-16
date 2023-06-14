package pet.store.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pet.store.controller.model.PetStoreData;
import pet.store.entity.PetStore;
import pet.store.service.PetStoreService;

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

}

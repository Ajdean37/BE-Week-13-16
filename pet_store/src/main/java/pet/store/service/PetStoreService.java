package pet.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class PetStoreService {
 
 @Autowired
 private PetStoreDao petStoreDao;

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
  petStore.setPetStoreName(petStore.getPetStoreName());
  petStore.setPetStoreAddress(petStore.getPetStoreAddress());
  petStore.setPetStoreCity(petStore.getPetStoreCity());
  petStore.setPetStoreState(petStoreData.getPetStoreState());
  petStore.setPetStoreZip(petStore.getPetStoreZip());
  petStore.setPetStorePhone(petStore.getPetStorePhone());
 }

 private PetStore findOrCreatePetStore(Long petStoreId) {
  PetStore petStore;

  if (Objects.isNull(petStoreId)) {
   petStore = new PetStore();
  }
  else {
   petStore = findPetStoreById(petStoreId);
  }
  return petStore;
 }

 private PetStore findPetStoreById(Long petStoreId) {
  return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("Contributor with ID=" + petStoreId +
    " was not found"));
 }

 }


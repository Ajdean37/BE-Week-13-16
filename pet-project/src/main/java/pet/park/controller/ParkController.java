package pet.park.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pet.park.controller.model.ContributorData;
import pet.park.parkService.ParkService;

@RestController
@RequestMapping("/pet_park")
@Slf4j
public class ParkController {
 private ParkService parkService;
 @PostMapping("/contributor")
 @ResponseStatus(code = HttpStatus.CREATED)
 public ContributorData insertContributor(@RequestBody ContributorData contributorData) {
  log.info("Creating contributor {}", contributorData);
  return  parkService.saveContributor(contributorData);
 }
}
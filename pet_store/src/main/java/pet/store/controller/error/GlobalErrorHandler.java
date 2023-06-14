package pet.store.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@Slf4j
public class GlobalErrorHandler {

 public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
  log.info("No such element exception: {}", ex.toString());
  Map<String, String> message = new HashMap<>();
  message.put("message", ex.toString());
  return message;
 }
}

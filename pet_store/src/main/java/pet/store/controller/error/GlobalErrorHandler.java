package pet.store.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@Slf4j
public class GlobalErrorHandler {

 @ExceptionHandler(NoSuchElementException.class)
 @ResponseStatus(HttpStatus.NOT_FOUND)
 public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
  log.info("No such element exception: {}", ex.toString());
  Map<String, String> message = new HashMap<>();
  message.put("message: {}", ex.toString());
  return message;
 }
}

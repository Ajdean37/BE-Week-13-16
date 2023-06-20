package dog.rescue.service;

import dog.rescue.controller.model.LocationData;
import dog.rescue.dao.LocationDao;
import dog.rescue.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RescueService {

 @Autowired
 private LocationDao locationDao;

 @Transactional(readOnly = false)
 public LocationData saveLocation(LocationData locationData) {
  Location location = locationData.toLocation();
  Location dbLocation = locationDao.save(location);

  return new LocationData(dbLocation);
 }
}

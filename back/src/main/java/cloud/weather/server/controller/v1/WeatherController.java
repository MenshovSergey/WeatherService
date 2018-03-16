package cloud.weather.server.controller.v1;

import cloud.weather.server.service.weather.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/v1")
public class WeatherController {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherService weatherService;

    @GetMapping(value = "/city/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getByCity(HttpServletRequest request, @PathVariable("city") String city) {
        try {
            return new ResponseEntity<>(weatherService.getByCity(city, request.getParameter("token")), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Errors while process request", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error");
        }
    }

    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getHistory(HttpServletRequest request) {
        try {
            return new ResponseEntity<>(weatherService.getHistory(request.getParameter("token")), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Errors while process request", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error");
        }
    }

    @GetMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAvailableCities() {
        try {
            return new ResponseEntity<>(weatherService.getAvailableCities(), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Errors while process request", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error");
        }
    }

}
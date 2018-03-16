package cloud.weather.server.model.info.impl;

import cloud.weather.server.model.info.Info;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Entity
@Table(name = "requests_by_city")
public class WeatherInfo implements Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "finished")
    private boolean finished;
    @Column(name = "city")
    private String city;
    @Column(name = "date")
    private Date date;
    @Column(name = "temp")
    private double temp;
    @Column(name = "pressure")
    private double pressure;
    @Column(name = "description")
    private String description;
    @Column(name = "lon")
    private double lon;
    @Column(name = "lat")
    private double lat;
    @Column(name = "image_url")
    private String imageUrl;
    @Transient
    private String type = "requests_by_city";
}

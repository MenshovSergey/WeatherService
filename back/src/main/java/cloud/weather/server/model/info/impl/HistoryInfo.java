package cloud.weather.server.model.info.impl;

import cloud.weather.server.model.info.Info;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HistoryInfo implements Info {
    private List<WeatherInfo> history;
}

package cloud.weather.server.model.info.impl;

import cloud.weather.server.model.info.Info;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueueInfo implements Info {
    private int pos;
}

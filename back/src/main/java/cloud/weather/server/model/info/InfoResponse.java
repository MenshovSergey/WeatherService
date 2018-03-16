package cloud.weather.server.model.info;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoResponse {
    private String type;
    private Info info;

    public static class Builder {
        private String type;
        private Info info;

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setInfo(Info info) {
            this.info = info;
            return this;
        }

        public InfoResponse build() {
            return new InfoResponse(type, info);
        }
    }

}

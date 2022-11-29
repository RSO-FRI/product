package si.fri.rso.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("rest-properties")
@ApplicationScoped
public class RestProperties {

    @ConfigValue(watch = true)
    private Boolean maintenanceMode;

    private Boolean broken;

    public Boolean getBroken() {
        return broken;
    }

    public void setBroken(final Boolean broken) {
        this.broken = broken;
    }

}

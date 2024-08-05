package calendario.agendamentos.event;

import java.time.LocalTime;

public class TimeWrapper {
    private LocalTime time;

    public TimeWrapper(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}

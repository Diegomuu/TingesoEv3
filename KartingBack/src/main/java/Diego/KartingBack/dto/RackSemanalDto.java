package Diego.KartingBack.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RackSemanalDto {
    private LocalDate semanaInicio;
    private List<SlotDto> slots;

    public LocalDate getSemanaInicio() { return semanaInicio; }
    public void setSemanaInicio(LocalDate semanaInicio) {
        this.semanaInicio = semanaInicio;
    }
    public List<SlotDto> getSlots() { return slots; }
    public void setSlots(List<SlotDto> slots) {
        this.slots = slots;
    }
}



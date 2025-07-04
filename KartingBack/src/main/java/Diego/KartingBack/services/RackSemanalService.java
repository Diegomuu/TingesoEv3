package Diego.KartingBack.services;

import Diego.KartingBack.entities.RackSemanalEntity;
import Diego.KartingBack.entities.SlotEntity;
import Diego.KartingBack.repositories.RackSemanalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RackSemanalService {

    private final RackSemanalRepository rackRepo;
    private static final Duration SLOT_STEP   = Duration.ofMinutes(30);
    private static final LocalTime START_WEEKDAY = LocalTime.of(14, 0);
    private static final LocalTime START_WEEKEND = LocalTime.of(10, 0);
    private static final LocalTime END_TIME      = LocalTime.of(22, 0);

    public RackSemanalService(RackSemanalRepository rackRepo) {
        this.rackRepo = rackRepo;
    }

    @Transactional
    public RackSemanalEntity getOrCreateRack(LocalDate anyDate) {
        LocalDate lunes = anyDate.with(DayOfWeek.MONDAY);

        return rackRepo.findBySemanaInicio(lunes)
                .orElseGet(() -> {
                    RackSemanalEntity rack = new RackSemanalEntity();
                    rack.setSemanaInicio(lunes);

                    List<SlotEntity> slots = new ArrayList<>();
                    for (DayOfWeek dia : DayOfWeek.values()) {
                        LocalTime start = (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY)
                                ? START_WEEKEND : START_WEEKDAY;

                        for (LocalTime hora = start; hora.isBefore(END_TIME); hora = hora.plus(SLOT_STEP)) {
                            slots.add(new SlotEntity(dia, hora, rack));
                        }
                    }

                    rack.setSlots(slots);
                    return rackRepo.save(rack);
                });
    }
}

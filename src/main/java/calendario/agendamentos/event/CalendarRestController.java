package calendario.agendamentos.event;

import calendario.agendamentos.category.Category;
import calendario.agendamentos.category.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarRestController {

    private final EventService eventService;
    private final CategoryService categoryService;

    public CalendarRestController(EventService eventService, CategoryService categoryService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
    }

    @GetMapping("/events/new/{date}")
    public ResponseEntity<?> createEventForm(@PathVariable("date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        List<Event> events = eventService.getEventsByDate(localDate);
        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(new EventFormResponse(date, events, categories));
    }

    @PostMapping("/events")
    public ResponseEntity<?> addEvent(@RequestParam String title,
                                      @RequestParam String date,
                                      @RequestParam String startTime,
                                      @RequestParam Long categoryId) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalTime start = LocalTime.parse(startTime);
        Category category = categoryService.getCategoryById(categoryId);
        int duration = category.getDuration();  // Get the duration from the category
        LocalTime end = start.plusMinutes(duration);

        Event event = new Event();
        event.setTitle(title);
        event.setDate(localDate);
        event.setStartTime(start);
        event.setEndTime(end);
        event.setCategory(category); // Set the category
        eventService.saveEvent(event);

        return ResponseEntity.ok(event);
    }

}

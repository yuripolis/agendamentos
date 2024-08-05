package calendario.agendamentos.event;

import calendario.agendamentos.category.Category;
import calendario.agendamentos.category.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/administracao/calendar")
public class CalendarController {

    private final EventService eventService;
    private final CategoryService categoryService;

    public CalendarController(EventService eventService, CategoryService categoryService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCalendar(Model model) {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        int daysInMonth = YearMonth.now().lengthOfMonth();

        model.addAttribute("currentYear", currentYear);
        model.addAttribute("currentMonth", String.format("%02d", currentMonth));
        model.addAttribute("daysInMonth", daysInMonth);
        return "calendar";
    }

    @GetMapping("/events/{date}")
    public String getEventsByDate(@PathVariable("date") String date, Model model) {
        LocalDate localDate = LocalDate.parse(date);
        List<Event> events = eventService.getEventsByDate(localDate);
        model.addAttribute("events", events);
        model.addAttribute("date", date);
        return "events";
    }

    @GetMapping("/events/new/{date}")
    public String createEventForm(@PathVariable("date") String date, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        List<Event> events = eventService.getEventsByDate(localDate);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("date", date);
        model.addAttribute("events", events);
        model.addAttribute("categories", categories);
        return "createEvent";
    }

    @PostMapping("/events")
    public String addEvent(@RequestParam String title, 
                           @RequestParam String date, 
                           @RequestParam String startTime,
                           @RequestParam Long categoryId, 
                           Model model) {
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
        
        return "redirect:/administracao/calendar";
    }

    @GetMapping("/categories/new")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "createCategory";
    }

    @PostMapping("/categories")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/administracao/calendar/categories/new";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        LocalDate currentDate = LocalDate.now();
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        int daysInMonth = yearMonth.lengthOfMonth();

        model.addAttribute("currentYear", currentDate.getYear());
        model.addAttribute("currentMonth", String.format("%02d", currentDate.getMonthValue()));
        model.addAttribute("daysInMonth", daysInMonth);
    }
}

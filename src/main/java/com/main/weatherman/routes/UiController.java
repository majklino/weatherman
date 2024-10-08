package com.main.weatherman.routes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.weatherman.model.AverageTemp;
import com.main.weatherman.model.City;
import com.main.weatherman.services.CityService;
import com.main.weatherman.services.MeasurementsService;

@Controller
@RequestMapping("")
public class UiController {
    private final CityService cityService;
    private final MeasurementsService measurementsService;
    private static final Logger logger = LoggerFactory.getLogger(UiController.class);

    @Autowired
    public UiController(CityService cityService, MeasurementsService measurementsService) {
        this.cityService = cityService;
        this.measurementsService = measurementsService;
    }

    @GetMapping("")
    public String index(Model model) {
        logger.info("index.html rendering requested...");
        List<City> cities = this.cityService.getAllCities();
        model.addAttribute("cities", cities);

        List<AverageTemp> avgs = new ArrayList<AverageTemp>();
        for (City city : cities) {
            double avg1 = this.measurementsService.findAvgTempForCity(city.getId(), 1);
            double avg7 = this.measurementsService.findAvgTempForCity(city.getId(), 7);
            double avg14 = this.measurementsService.findAvgTempForCity(city.getId(), 14);

            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            avg1 = Double.parseDouble(decimalFormat.format(avg1));
            avg7 = Double.parseDouble(decimalFormat.format(avg7));
            avg14 = Double.parseDouble(decimalFormat.format(avg14));

            AverageTemp avg = new AverageTemp(city.getId(), avg1, avg7, avg14);
            avgs.add(avg);
        }
        model.addAttribute("avgs", avgs);

        return "index";
    }
}

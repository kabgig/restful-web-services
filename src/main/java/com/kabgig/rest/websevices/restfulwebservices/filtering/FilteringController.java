package com.kabgig.rest.websevices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue filtering(){
        SomeBean someBean = new SomeBean("vale1","value2","value3");
        MappingJacksonValue mappingJacksonValue =
                getMappingJacksonValue(
                        someBean,
                        "field1",
                        "field3",
                        "SomeBeanFilter");
        return mappingJacksonValue;
    }

    private static MappingJacksonValue getMappingJacksonValue(Object beanObject, String property1, String property2, String filterName) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(beanObject);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(property1, property2);
        FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList(){
        List<SomeBean> beanList = Arrays.asList(
                new SomeBean("value1","value2","value3"),
                new SomeBean("value4","value5","value6"));
        MappingJacksonValue mappingJacksonValue =
                getMappingJacksonValue(
                        beanList,
                        "field2",
                        "field3",
                        "SomeBeanFilter");
        return mappingJacksonValue;
    }

}

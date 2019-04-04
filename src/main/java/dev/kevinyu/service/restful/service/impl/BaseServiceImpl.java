package dev.kevinyu.service.restful.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseServiceImpl {

    protected BaseServiceImpl(){ }

    protected Pageable generatePageable(String sortby, int offset, int limit){

        Sort sort = Sort.unsorted();

        if(!sortby.isEmpty()){
            List<Sort.Order> orders = Arrays.stream(sortby.split(",")).map(sb-> {
                String trimedSortby = sb.trim();
                if(trimedSortby.startsWith("-")) {
                    return Sort.Order.desc(trimedSortby.substring(1).trim());
                } else if (trimedSortby.startsWith("+")) {
                    return Sort.Order.asc(trimedSortby.substring(1).trim());
                }

                return Sort.Order.asc(trimedSortby);
            }).collect(Collectors.toList());

            sort = Sort.by(orders);
        }

        if(offset < 1) {
            offset = 1;
        }

        if(limit < 1) {
            limit = 1000;
        }

        Pageable pageable = PageRequest.of(offset - 1, limit, sort);

        return pageable;
    }

}

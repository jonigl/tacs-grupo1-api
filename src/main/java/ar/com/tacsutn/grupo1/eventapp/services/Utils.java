package ar.com.tacsutn.grupo1.eventapp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

class Utils {

    static <T> Page<T> listToPage(List<T> list, Pageable pageable) {
        int start = Math.toIntExact(pageable.getOffset());

        int end;
        if ((start + pageable.getPageSize()) > list.size()) {
            end = list.size();
        } else {
            end = start + pageable.getPageSize();
        }

        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}

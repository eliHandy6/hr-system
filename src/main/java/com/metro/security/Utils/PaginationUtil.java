package com.metro.security.Utils;
import com.metro.security.Exceptions.APIExceptions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationUtil {
    public static final Integer DEFAULT_PAGE_SIZE = 1000;

    public static Pageable createPage(Integer page, Integer size) {
        if (page != null) {
            if (page <= 0) {
                throw APIExceptions.badRequest("page must be greater than 0");
            }
            page = page - 1;
        } else {
            page = 0;
        }

        if (size == null) {
            size = DEFAULT_PAGE_SIZE;
        }

        Pageable pageable = PageRequest.of(page, size);
        return pageable;
    }
}

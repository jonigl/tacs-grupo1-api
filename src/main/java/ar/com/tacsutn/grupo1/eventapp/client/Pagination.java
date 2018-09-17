package ar.com.tacsutn.grupo1.eventapp.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pagination {

    private final long objectCount;
    private final int pageNumber;
    private final int pageSize;
    private final int pageCount;
    private final boolean hasMoreItems;

    @JsonCreator
    public Pagination(

            @JsonProperty(value = "object_count", required = true)
            long objectCount,

            @JsonProperty(value = "page_number", required = true)
            int pageNumber,

            @JsonProperty(value = "page_size", required = true)
            int pageSize,

            @JsonProperty(value = "page_count", required = true)
            int pageCount,

            @JsonProperty(value = "has_more_items", required = true)
            boolean hasMoreItems
    ) {

        this.objectCount = objectCount;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.hasMoreItems = hasMoreItems;
    }

    public long getObjectCount() {
        return objectCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public boolean isHasMoreItems() {
        return hasMoreItems;
    }
}

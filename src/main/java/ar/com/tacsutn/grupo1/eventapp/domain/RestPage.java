package ar.com.tacsutn.grupo1.eventapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@ApiModel("Page")
public class RestPage<T> extends PageImpl<T> {

    /**
     * The number of the current page.
     */
    @JsonProperty("page")
    @ApiModelProperty(example = "0")
    private int number;

    /**
     * Maximum number of elements per page.
     */
    @JsonProperty("perPage")
    @ApiModelProperty(example = "10")
    private int size;

    /**
     * Total number of pages.
     */
    @JsonProperty
    @ApiModelProperty(example = "1")
    private int totalPages;

    /**
     * Total number of elements.
     */
    @JsonProperty("total")
    @ApiModelProperty(example = "1")
    private long totalElements;

    /**
     * The number of elements on current page.
     */
    @JsonProperty
    @ApiModelProperty(example = "1")
    private int numberOfElements;

    /**
     * List of elements.
     */
    @JsonProperty
    private List<T> content;

    @JsonIgnore
    private boolean previousPage;

    @JsonIgnore
    private boolean first;

    @JsonIgnore
    private boolean nextPage;

    @JsonIgnore
    private boolean last;

    @JsonIgnore
    private Sort sort;

    @JsonIgnore
    private Pageable pageable;

    private RestPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public RestPage(Page<T> page) {
        this(page.getContent(), page.getPageable(), page.getTotalElements());
    }
}

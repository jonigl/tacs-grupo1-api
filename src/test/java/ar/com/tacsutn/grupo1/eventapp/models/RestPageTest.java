package ar.com.tacsutn.grupo1.eventapp.models;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;

public class RestPageTest {

    @Test
    public void shouldDetectLastPage() {
        Pageable pageable = PageRequest.of(2, 3);

        Page<String> page = new PageImpl<>(
            Arrays.asList("a", "b", "c"),
            pageable,
            9
        );

        RestPage<String> restPage = new RestPage<>(page);

        Assert.assertFalse(restPage.isFirst());
        Assert.assertTrue(restPage.isLast());
    }

    @Test
    public void shouldDetectEmptyPage() {
        Pageable pageable = PageRequest.of(5, 5);

        Page<String> page = new PageImpl<>(
            Collections.emptyList(),
            pageable,
            10
        );

        RestPage<String> restPage = new RestPage<>(page);

        Assert.assertFalse(restPage.hasContent());
    }
}

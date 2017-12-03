package org.qatools.rp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PagedList<T> {
    @JsonProperty("page")
    private PageMetadata pageMetadata;

    @JsonProperty("content")
    private List<T> content;

    public PageMetadata getPageMetadata() {
        return this.pageMetadata;
    }

    public List<T> getContent() {
        return this.content;
    }
}

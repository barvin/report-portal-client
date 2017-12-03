package org.qatools.rp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageMetadata {
    @JsonProperty
    private long size;
    @JsonProperty
    private long totalElements;
    @JsonProperty
    private long totalPages;
    @JsonProperty
    private long number;
}

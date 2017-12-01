package org.qatools.rp.codec;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.qatools.rp.exceptions.ReportPortalClientException;

import static feign.FeignException.errorStatus;

public class ReportPortalErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = errorStatus(methodKey, response);
        return new ReportPortalClientException(exception);
    }
}

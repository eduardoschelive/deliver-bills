package com.eduardoschelive.deliverbills.filter.resolver;

import com.eduardoschelive.deliverbills.filter.FilterPagination;
import com.eduardoschelive.deliverbills.filter.FilterSorting;
import com.eduardoschelive.deliverbills.filter.annotation.FilterEndpoint;
import com.eduardoschelive.deliverbills.filter.utils.FilterUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class FilterEndpointPageableResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Pageable.class) && parameter.hasMethodAnnotation(FilterEndpoint.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        var filterDetails = FilterUtils.extractFilterableFieldsFromMethod(parameter);
        var queryParameters = FilterUtils.extractQueryParameters(webRequest);

        var sort = new FilterSorting(queryParameters.getSortParameters(), filterDetails);
        var pagination = new FilterPagination(queryParameters.getPaginationParameters());

        return pagination.getPageRequest().withSort(sort.getSort());
    }
}

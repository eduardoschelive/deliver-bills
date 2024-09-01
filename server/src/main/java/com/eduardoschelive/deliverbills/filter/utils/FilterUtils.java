package com.eduardoschelive.deliverbills.filter.utils;

import com.eduardoschelive.deliverbills.common.dto.PagedModel;
import com.eduardoschelive.deliverbills.filter.annotation.FilterEndpoint;
import com.eduardoschelive.deliverbills.filter.annotation.Filterable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Field;
import java.util.*;

import static com.eduardoschelive.deliverbills.filter.utils.FilterConstants.FILTER_PARAM_SEPARATOR;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterUtils {

    public static Map<String, FilterFieldDetails> extractFilterableFields(Class<?> targetClass) {
        Map<String, FilterFieldDetails> fieldDetailsHashMap = new HashMap<>();
        var fieldPathStack = new ArrayDeque<String>();
        traverseFields(fieldPathStack, targetClass, fieldDetailsHashMap);
        return fieldDetailsHashMap;
    }

    private static void traverseFields(Deque<String> fieldPathStack, Class<?> currentClass, Map<String, FilterFieldDetails> fieldDetailsHashMap) {
        for (var field : getAllFieldsUpTo(currentClass, Object.class)) {
            evaluateField(field, fieldPathStack, fieldDetailsHashMap);
        }
    }

    private static Iterable<Field> getAllFieldsUpTo(@NonNull Class<?> startClass, @NonNull Class<?> exclusiveParent) {
        var currentClassFields = new ArrayList<>(Arrays.asList(startClass.getDeclaredFields()));
        var parentClass = startClass.getSuperclass();

        if (parentClass != null && !parentClass.equals(exclusiveParent)) {
            currentClassFields.addAll((List<Field>) getAllFieldsUpTo(parentClass, exclusiveParent));
        }

        return currentClassFields;
    }

    private static void evaluateField(Field field, Deque<String> fieldPathStack, Map<String, FilterFieldDetails> fieldDetailsHashMap) {
        if (field.isAnnotationPresent(Filterable.class)) {
            var annotation = field.getAnnotation(Filterable.class);
            var fullPath = constructFieldPath(fieldPathStack, field.getName());
            var customPath = annotation.customPath().isBlank() ? fullPath : annotation.customPath();
            fieldDetailsHashMap.put(customPath, new FilterFieldDetails(field.getType(), fullPath));
        }
    }

    private static String constructFieldPath(Deque<String> fieldPathStack, String fieldName) {
        return String.join(".", fieldPathStack) + (fieldPathStack.isEmpty() ? "" : ".") + fieldName;
    }

    public static String extractFieldPath(String key) {
        int separatorIndex = key.lastIndexOf(FILTER_PARAM_SEPARATOR);
        return (separatorIndex != -1) ? key.substring(0, separatorIndex).trim() : null;
    }

    public static String extractOperation(String key) {
        int separatorIndex = key.lastIndexOf(FILTER_PARAM_SEPARATOR);
        return (separatorIndex != -1) ? key.substring(separatorIndex + 1).trim() : null;
    }

    public static Map<String, FilterFieldDetails> extractFilterableFieldsFromMethod(MethodParameter method) {
        var annotation = method.getMethodAnnotation(FilterEndpoint.class);

        if (annotation == null) {
            throw new IllegalArgumentException("Method must be annotated with @FilterEndpoint");
        }

        return extractFilterableFields(annotation.value());
    }

    public static QueryParameterDetails extractQueryParameters(WebRequest webRequest) {
        var queryParamsMap = new HashMap<String, String>();
        webRequest.getParameterNames().forEachRemaining(name -> queryParamsMap.put(name, webRequest.getParameter(name)));
        return new QueryParameterDetails(queryParamsMap);
    }

    public static <T> ResponseEntity<PagedModel<T>> buildResponseEntity(PagedModel<T> page) {
        var responseHeaders = new HttpHeaders();
        var pageMetadata = page.metadata();

        var totalElements = pageMetadata.totalElements();
        responseHeaders.add(FilterConstants.X_FILTER_RESULT_SIZE_HEADER, String.valueOf(totalElements));

        return ResponseEntity.ok().headers(responseHeaders).body(page);
    }

}

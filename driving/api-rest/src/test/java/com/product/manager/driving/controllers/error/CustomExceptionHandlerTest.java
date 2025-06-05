package com.product.manager.driving.controllers.error;

import com.product.manager.driving.controllers.models.Error;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZoneOffset;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {

    private CustomExceptionHandler exceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Mock
    private MethodParameter methodParameter;

    @BeforeEach
    void setUp() {
        exceptionHandler = new CustomExceptionHandler();
    }

    @Test
    void handleIllegalArgumentShouldReturnInvalidCriteriaError() {
        // Given
        String errorMessage = "Invalid sorting criteria";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        // When
        ResponseEntity<Error> response = exceptionHandler.handleIllegalArgument(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INVALID_CRITERIA", response.getBody().getCode().getValue());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
        assertEquals(ZoneOffset.UTC, response.getBody().getTimestamp().getOffset());

        // Verificar detalles cuando el mensaje contiene "criteria"
        assertNotNull(response.getBody().getDetails());
        Map<String, Object> details = response.getBody().getDetails();
        assertEquals("criteria", details.get("field"));
        assertEquals("Invalid sorting criteria provided", details.get("issue"));
    }

    @Test
    void handleIllegalArgumentShouldReturnErrorWithoutDetailsWhenNotCriteriaRelated() {
        // Given
        String errorMessage = "Some other illegal argument";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        // When
        ResponseEntity<Error> response = exceptionHandler.handleIllegalArgument(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INVALID_CRITERIA", response.getBody().getCode().getValue());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertNull(response.getBody().getDetails());
    }

    @Test
    void handleNullPointerShouldReturnMissingCriteriaError() {
        // Given
        NullPointerException exception = new NullPointerException();

        // When
        ResponseEntity<Error> response = exceptionHandler.handleNullPointer(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("MISSING_CRITERIA", response.getBody().getCode().getValue());
        assertEquals("Required sorting criteria is missing or null", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
        assertEquals(ZoneOffset.UTC, response.getBody().getTimestamp().getOffset());

        // Verificar detalles
        assertNotNull(response.getBody().getDetails());
        Map<String, Object> details = response.getBody().getDetails();
        assertEquals("criteria", details.get("field"));
        assertEquals("Criteria cannot be null", details.get("issue"));
    }

    // ===== VALIDATION EXCEPTIONS =====

    @Test
    void handleValidationExceptionsShouldReturnValidationError() {
        // Given
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "testObject");
        bindingResult.addError(new FieldError("testObject", "salesWeight", "must be non-negative"));
        bindingResult.addError(new FieldError("testObject", "stockRatioWeight", "cannot be null"));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);

        // When
        ResponseEntity<Error> response = exceptionHandler.handleValidationExceptions(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("VALIDATION_ERROR", response.getBody().getCode().getValue());
        assertEquals("Invalid request data", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());

        // Verificar detalles con errores de campo
        assertNotNull(response.getBody().getDetails());
        Map<String, Object> details = response.getBody().getDetails();
        assertTrue(details.containsKey("fieldErrors"));

        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) details.get("fieldErrors");
        assertEquals("must be non-negative", fieldErrors.get("salesWeight"));
        assertEquals("cannot be null", fieldErrors.get("stockRatioWeight"));
    }

    @Test
    void handleHttpMessageNotReadableShouldReturnInvalidJsonError() {
        // Given
        String errorMessage = "JSON parse error: Cannot deserialize criteria";
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException(errorMessage);

        // When
        ResponseEntity<Error> response = exceptionHandler.handleHttpMessageNotReadable(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INVALID_JSON", response.getBody().getCode().getValue());
        assertEquals("Invalid JSON format or missing required fields", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());

        // Verificar detalles básicos
        assertNotNull(response.getBody().getDetails());
        Map<String, Object> details = response.getBody().getDetails();
        assertEquals("Request body is not valid JSON or is missing required fields", details.get("issue"));
    }

    @Test
    void handleHttpMessageNotReadableShouldProvideSpecificHintsForCriteria() {
        // Given
        String errorMessage = "JSON parse error: Missing criteria field";
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException(errorMessage);

        // When
        ResponseEntity<Error> response = exceptionHandler.handleHttpMessageNotReadable(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        // Verificar hints específicos para criteria
        Map<String, Object> details = response.getBody().getDetails();
        assertEquals("criteria", details.get("field"));
        assertEquals("Ensure 'criteria' object is provided with valid 'weights'", details.get("hint"));
    }

    @Test
    void handleHttpMessageNotReadableShouldProvideSpecificHintsForWeights() {
        // Given
        String errorMessage = "JSON parse error: Invalid weights format";
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException(errorMessage);

        // When
        ResponseEntity<Error> response = exceptionHandler.handleHttpMessageNotReadable(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        // Verificar hints específicos para weights
        Map<String, Object> details = response.getBody().getDetails();
        assertEquals("criteria.weights", details.get("field"));
        assertEquals("Ensure 'weights' is an object with numeric values", details.get("hint"));
    }

    @Test
    void handleTypeMismatchShouldReturnBadRequestErrorResponse() {
        // Given
        String parameterName = "someParam";
        String errorMessage = "Invalid parameter format";

        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "abc",
                Long.class,
                parameterName,
                methodParameter,
                new NumberFormatException(errorMessage)
        );

        // When
        ResponseEntity<Error> response = exceptionHandler.handleTypeMismatch(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INVALID_PARAMETER", response.getBody().getCode().getValue());
        assertEquals("Parameter '" + parameterName + "' has invalid type", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
        assertEquals(ZoneOffset.UTC, response.getBody().getTimestamp().getOffset());

        // Verificar detalles específicos
        assertNotNull(response.getBody().getDetails());
        Map<String, Object> details = response.getBody().getDetails();
        assertEquals(parameterName, details.get("parameter"));
        assertEquals("abc", details.get("providedValue"));
        assertEquals("Long", details.get("expectedType"));
    }

    @Test
    void handleRuntimeExceptionShouldReturnInternalErrorResponse() {
        // Given
        String errorMessage = "Database connection failed";
        RuntimeException exception = new RuntimeException(errorMessage);

        // When
        ResponseEntity<Error> response = exceptionHandler.handleRuntimeException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INTERNAL_ERROR", response.getBody().getCode().getValue());
        assertEquals("An unexpected error occurred while processing the request", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
        assertEquals(ZoneOffset.UTC, response.getBody().getTimestamp().getOffset());

        // Verificar detalles con tipo de excepción
        assertNotNull(response.getBody().getDetails());
        Map<String, Object> details = response.getBody().getDetails();
        assertEquals("RuntimeException", details.get("type"));
    }

    @Test
    void handleAllExceptionsShouldReturnInternalServerErrorResponse() {
        // Given
        String errorMessage = "Unexpected error occurred";
        Exception exception = new Exception(errorMessage);

        // When
        ResponseEntity<Error> response = exceptionHandler.handleAllExceptions(exception, webRequest);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INTERNAL_ERROR", response.getBody().getCode().getValue());
        assertEquals("An unexpected error occurred while processing the request", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
        assertEquals(ZoneOffset.UTC, response.getBody().getTimestamp().getOffset());
    }

    @Test
    void handleAllExceptionsShouldReturnInternalServerErrorResponseForNullMessage() {
        // Given
        Exception exception = new Exception();

        // When
        ResponseEntity<Error> response = exceptionHandler.handleAllExceptions(exception, webRequest);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INTERNAL_ERROR", response.getBody().getCode().getValue());
        assertEquals("An unexpected error occurred while processing the request", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }


    @Test
    void timestampShouldBeInUtcFormat() {
        // Given
        Exception exception = new RuntimeException("Test error");

        // When
        ResponseEntity<Error> response = exceptionHandler.handleAllExceptions(exception, webRequest);

        // Then
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getTimestamp());
        assertEquals(ZoneOffset.UTC, response.getBody().getTimestamp().getOffset());
        assertEquals(0, response.getBody().getTimestamp().getNano());
    }

    @Test
    void shouldHandleExceptionWithoutMessage() {
        // Given
        IllegalArgumentException exception = new IllegalArgumentException();

        // When
        ResponseEntity<Error> response = exceptionHandler.handleIllegalArgument(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INVALID_CRITERIA", response.getBody().getCode().getValue());
        assertNull(response.getBody().getMessage());
    }

    @Test
    void shouldHandleTypeMismatchWithNullRequiredType() {
        // Given
        String parameterName = "testParam";

        MethodArgumentTypeMismatchException exception = Mockito.mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn(parameterName);
        when(exception.getValue()).thenReturn("invalidValue");
        when(exception.getRequiredType()).thenReturn(null);  // Simular caso null

        // When
        ResponseEntity<Error> response = exceptionHandler.handleTypeMismatch(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INVALID_PARAMETER", response.getBody().getCode().getValue());
        assertEquals("Parameter '" + parameterName + "' has invalid type", response.getBody().getMessage());

        // Verificar que maneja correctamente el tipo null
        Map<String, Object> details = response.getBody().getDetails();
        assertEquals("unknown", details.get("expectedType"));
        assertEquals(parameterName, details.get("parameter"));
        assertEquals("invalidValue", details.get("providedValue"));
    }
}
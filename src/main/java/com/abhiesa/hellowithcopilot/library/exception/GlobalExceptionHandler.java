package com.abhiesa.hellowithcopilot.library.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles exceptions globally for the application and returns RFC 7807 compliant HTTP responses.
 * Defensive programming: validates exception objects and logs unexpected errors.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles NotFoundException and returns a 404 RFC 7807 response.
   *
   * @param ex the NotFoundException thrown
   * @return ResponseEntity with problem details and 404 status
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> notFound(NotFoundException ex) {
    String detail = (ex != null && ex.getMessage() != null && !ex.getMessage().isBlank())
      ? ex.getMessage()
      : "Resource not found";
    return ResponseEntity
      .status(404)
      .header("Content-Type", "application/problem+json")
      .body(Map.of(
        "type", "https://httpstatuses.com/404",
        "title", "Not Found",
        "status", 404,
        "detail", detail
      ));
  }

  /**
   * Handles BusinessException and returns a 400 RFC 7807 response.
   *
   * @param ex the BusinessException thrown
   * @return ResponseEntity with problem details and 400 status
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> business(BusinessException ex) {
    String detail = (ex != null && ex.getMessage() != null && !ex.getMessage().isBlank())
      ? ex.getMessage()
      : "Business rule violation";
    return ResponseEntity
      .status(400)
      .header("Content-Type", "application/problem+json")
      .body(Map.of(
        "type", "https://httpstatuses.com/400",
        "title", "Business Error",
        "status", 400,
        "detail", detail
      ));
  }

  /**
   * Handles all other exceptions and returns a 500 RFC 7807 response.
   *
   * @param ex the Exception thrown
   * @return ResponseEntity with problem details and 500 status
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> generic(Exception ex) {
    logger.error("Unhandled exception", ex);
    String detail = (ex != null && ex.getMessage() != null && !ex.getMessage().isBlank())
      ? ex.getMessage()
      : "Internal server error";
    return ResponseEntity
      .status(500)
      .header("Content-Type", "application/problem+json")
      .body(Map.of(
        "type", "https://httpstatuses.com/500",
        "title", "Internal Server Error",
        "status", 500,
        "detail", detail
      ));
  }
}
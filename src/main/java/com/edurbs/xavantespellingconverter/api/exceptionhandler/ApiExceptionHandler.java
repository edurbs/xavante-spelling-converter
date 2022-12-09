package com.edurbs.xavantespellingconverter.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "Ocorreu um erro interno inesperado no sistema. Tente novamente. Se o problema persistir, entre em contato com o administrador do sistema.";

    @Autowired
    private MessageSource messageSource;

    @Override
    @Nullable
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        
        ProblemType problemType = ProblemType.HANDLER_NOT_FOUND;
        String detail = String.format("O recurso %s que você tentou acessar não existe.", ex.getRequestURL());
        
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request) {

        ProblemType problemType = ProblemType.INVALID_PROPERTY;
        String detail = String.format("A propriedade %s não existe.", ex.getPath());

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode statusCode, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(statusCode.value())
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage(detail)
                .offsetDateTime(OffsetDateTime.now().toString());
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
            HttpStatusCode statusCode, WebRequest request) {
        if(body == null){
            body = Problem.builder()
                    .offsetDateTime(OffsetDateTime.now().toString())
                    .title(HttpStatus.valueOf(statusCode.value()).getReasonPhrase())
                    .status(statusCode.value())
                    .userMessage(GENERIC_ERROR_MESSAGE)
                    .build();
        }else if (body instanceof String){
            body = Problem.builder()
                .offsetDateTime(OffsetDateTime.now().toString())
                .title((String) body)
                .status(statusCode.value())
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        
        if(rootCause instanceof InvalidFormatException){
            return handleInvalidFormat( (InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException){
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
        String detail = "O corpo da requisição está inválido. Verifique o erro de sintaxe.";
        Problem problem = createProblemBuilder(status, problemType, detail).build();


        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }



    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        
        ProblemType problemType = ProblemType.INVALID_PARAMETER;
        String detail = String.format("A propriedade %s recebeu o valor de '%s', que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s.", 
                ex.getPath(), ex.getValue(), ex.getTargetType().getSimpleName());
        
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();
        
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // TODO Auto-generated method stub
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request, BindingResult bindingResult) {
        
        ProblemType problemType = ProblemType.INVALID_DATA;
        String detail = "Um ou mais campos estão inválidos.";

        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                        String msg = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                        String name = objectError.getObjectName();
                        if(objectError instanceof FieldError){
                            name = ((FieldError) objectError).getField();
                        }
                        return Problem.Object.builder()
                                .name(name)
                                .userMessage(msg)
                                .build();
                })
                .collect(Collectors.toList());
        
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    
}

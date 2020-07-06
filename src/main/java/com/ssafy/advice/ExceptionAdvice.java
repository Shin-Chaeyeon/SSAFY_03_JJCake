package com.ssafy.advice;

import com.ssafy.advice.exception.*;
import com.ssafy.vo.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.service.ResponseSerivce;

import javax.servlet.http.HttpServletRequest;

import java.nio.file.AccessDeniedException;

import static jdk.nashorn.internal.runtime.ECMAErrors.getMessage;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseSerivce responseService;
    private final MessageSource messageSource;

    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    // 모든 에러

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage(String.format("%s", e.toString())));
    }

    // DB 에러

    @ExceptionHandler(CustomDataBaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customDataBaseException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("databaseError.code")), getMessage(String.format("%s", e.toString())));
    }

    // 통신 에러

    @ExceptionHandler(CustomCommunicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult communicationException(HttpServletRequest request, CustomCommunicationException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("communicationError.code")), getMessage("communicationError.msg"));
    }

    // 로그인 에러

    @ExceptionHandler(CustomMemberExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult communicationException(HttpServletRequest request, CustomMemberExistException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("existingUser.code")), getMessage("existingUser.msg"));
    }

    @ExceptionHandler(CustomEmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customEmailSignFailedException(HttpServletRequest request, CustomEmailSigninFailedException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }

    @ExceptionHandler(CustomDuplicateNicknameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customDuplicateNicknameException(HttpServletRequest request, CustomDuplicateNicknameException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("duplicateNickname.code")), getMessage("duplicateNickname.msg"));
    }

    @ExceptionHandler(CustomDuplicateEmailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customDuplicateEmailException(HttpServletRequest request, CustomDuplicateEmailException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("duplicateEmail.code")), getMessage("duplicateEmail.msg"));
    }

    // 이미지 저장 에러

    @ExceptionHandler(CustomImageSaveException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customImageSaveException(HttpServletRequest request, RuntimeException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("saveImageFaild.code")), getMessage(getMessage(String.format("%s", e.toString()))));
    }

    @ExceptionHandler(CustomImageDeleteException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customImageDeleteException(HttpServletRequest request, RuntimeException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("deleteImageFaild.code")), getMessage(getMessage(String.format("%s", e.toString()))));
    }

    // 조회 실패 에러

    @ExceptionHandler(CustomMemberNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CustomMemberNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(CustomPortfolioNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customPortfolioNotFoundException(HttpServletRequest request, CustomPortfolioNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("portfolioNotFound.code")), getMessage("portfolioNotFound.msg"));
    }

    @ExceptionHandler(CustomReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customReviewNotFoundException(HttpServletRequest request, CustomReviewNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("reviewNotFound.code")), getMessage("reviewNotFound.msg"));
    }

    @ExceptionHandler(CustomOrderNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customOrderNotFoundException(HttpServletRequest request, CustomOrderNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("orderNotFound.code")), getMessage("orderNotFound.msg"));
    }

    @ExceptionHandler(CustomMessageNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customMessageNotFoundException(HttpServletRequest request, CustomMessageNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("messageNotFound.code")), getMessage("messageNotFound.msg"));
    }

    @ExceptionHandler(CustomEstimateNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customEstimateNotFoundException(HttpServletRequest request, CustomEstimateNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("estimateNotFound.code")), getMessage("estimateNotFound.msg"));
    }

    @ExceptionHandler(CustomMarketNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult customMarketNotFoundException(HttpServletRequest request, CustomMarketNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("marketNotFound.code")), getMessage("marketNotFound.msg"));
    }

    // 권한 에러

    @ExceptionHandler(CustomAuthenticationEntryPointException.class)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CustomAuthenticationEntryPointException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }


}

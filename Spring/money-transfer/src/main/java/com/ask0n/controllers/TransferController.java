package com.ask0n.controllers;

import com.ask0n.exceptions.ErrorConfirmationException;
import com.ask0n.exceptions.ErrorTransferException;
import com.ask0n.models.ErrorMessage;
import com.ask0n.models.Operation;
import com.ask0n.models.Transfer;
import com.ask0n.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("transfer")
    public Operation transfer(@Valid @RequestBody Transfer transfer) {
        return transferService.transfer(transfer);
    }

    @PostMapping("confirmOperation")
    public Operation confirmOperation(@Valid @RequestBody Operation operation) {
        return transferService.confirmOperation(operation);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidException(MethodArgumentNotValidException error) {
        return new ResponseEntity<>(new ErrorMessage(error.getLocalizedMessage(), 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ErrorTransferException.class)
    public ResponseEntity<ErrorMessage> handleErrorTransfer(ErrorTransferException error) {
        return new ResponseEntity<>(new ErrorMessage(error.getLocalizedMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ErrorConfirmationException.class)
    public ResponseEntity<ErrorMessage> handleErrorConfirmation(ErrorConfirmationException error) {
        return new ResponseEntity<>(new ErrorMessage(error.getLocalizedMessage(), 501), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

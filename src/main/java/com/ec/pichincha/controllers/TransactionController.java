package com.ec.pichincha.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ec.pichincha.dto.request.TransactionClientCreateRequest;
import com.ec.pichincha.dto.request.TransactionCreateRequest;
import com.ec.pichincha.dto.request.TransactionUpdateRequest;
import com.ec.pichincha.dto.response.ClientInfoResponse;
import com.ec.pichincha.dto.response.TransactionCreateResponse;
import com.ec.pichincha.dto.response.TransactionInfoResponse;
import com.ec.pichincha.services.ITransactionsServices;

@RestController
@RequestMapping("movimientos")
public class TransactionController {
	
	ITransactionsServices transactionsServices;
	
	@PostMapping("/create")
	public ResponseEntity<?> createTransaction(
			@Valid @RequestBody TransactionCreateRequest transactionCreateRequest, BindingResult result ){
		
		Map<String, Object> response = new HashMap<>();
		TransactionCreateResponse newTransaction = null;
		
		if(result.hasErrors()) {
			List<String> errrors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errrors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			newTransaction = transactionsServices.createTransaction(transactionCreateRequest);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar una transaccion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La transaccion a sido creada con éxito");
		response.put("transaction", newTransaction);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateClient(
			@Valid @RequestBody TransactionUpdateRequest transactionUpdateRequest,  BindingResult result){
		
		Map<String, Object> response = new HashMap<>();
		
		TransactionInfoResponse currentTransaction = transactionsServices.findById(transactionUpdateRequest.getTransaction().getId());
		
		TransactionUpdateRequest transaction = new TransactionUpdateRequest();
		
		TransactionInfoResponse updateTransaction = null;
				
		if(result.hasErrors()) {
			List<String> errrors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errrors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(currentTransaction == null) {
			response.put("mensaje", "Error: no se pudo editar, la transaccion ID:  "
					.concat(transactionUpdateRequest.getTransaction().getId().toString())
					.concat(" No existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			
			BeanUtils.copyProperties(currentTransaction, transaction);
			updateTransaction = transactionsServices.updateTransaction(transaction);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar transaccion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La transaccion e a sido actualizado con éxito");
		response.put("cliente", updateTransaction);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/delete")
	public  ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			transactionsServices.deleteTransaction(id);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la transaccion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("mensaje", "La transacción a sido eliminado con éxito");
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	
	}
	
	public ResponseEntity<?> transactionForClient(@Valid @RequestBody 
			TransactionClientCreateRequest request, BindingResult result){
		
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errrors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errrors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		//Consultar si el cliente existe
		ClientInfoResponse clienteSearch = transactionsServices.idClient(request.getTransactionClient().getIdClient());
		
		if(clienteSearch == null) {
			response.put("mensaje", "Error: el clinete, no existe ID:  "
					.concat(request.getTransactionClient().getIdClient())
					.concat(" No existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		//consultar la tabla c
		return null;
	}

}

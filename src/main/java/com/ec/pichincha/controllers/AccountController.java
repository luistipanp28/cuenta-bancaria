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

import com.ec.pichincha.dto.request.AccountCreateRequest;
import com.ec.pichincha.dto.request.AccountUpdateRequest;
import com.ec.pichincha.dto.response.AccountCreateResponse;
import com.ec.pichincha.dto.response.AccountInfoResponse;
import com.ec.pichincha.services.IAccountServices;

@RestController
@RequestMapping("/cuentas")
public class AccountController {
	
	private IAccountServices accountServices;
	
	@PostMapping("/create")
	public ResponseEntity<?> createClient(
			@Valid @RequestBody AccountCreateRequest accountCreateRequest, BindingResult result ){
		
		Map<String, Object> response = new HashMap<>();
		AccountCreateResponse newAccount = null;
		
		if(result.hasErrors()) {
			List<String> errrors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errrors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			
				newAccount = accountServices.createAccount(accountCreateRequest);	
			
			
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar nueva cuenta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La cuenta a sido creada con éxito");
		response.put("cliente", newAccount);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateClient(
			@Valid @RequestBody AccountUpdateRequest accountUpdateRequest,  BindingResult result){
		
		Map<String, Object> response = new HashMap<>();
		
		AccountInfoResponse currentAccount = accountServices.findById(accountUpdateRequest.getAccount().getId());
		
		AccountUpdateRequest account = new AccountUpdateRequest();
		
		AccountInfoResponse updateAccount = null;
				
		if(result.hasErrors()) {
			List<String> errrors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errrors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(currentAccount == null) {
			response.put("mensaje", "Error: no se pudo editar, la cuenta ID:  "
					.concat(accountUpdateRequest.getAccount().getId().toString())
					.concat(" No existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			
			BeanUtils.copyProperties(currentAccount, account);
			updateAccount = accountServices.updateAccount(account);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar cuenta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La cuenta a sido actualizada con éxito");
		response.put("cliente", updateAccount);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/delete")
	public  ResponseEntity<?> deleteAccount(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			accountServices.deleteAccount(id);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la cuenta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("mensaje", "La cuenta a sido eliminada con éxito");
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	
	}

}

package com.ec.pichincha.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ec.pichincha.dto.request.ClientCreateRequest;
import com.ec.pichincha.dto.request.ClientUpdateRequest;
import com.ec.pichincha.dto.response.ClientCreateResponse;
import com.ec.pichincha.dto.response.ClientInfoResponse;
import com.ec.pichincha.services.IClientServices;



@RestController
@RequestMapping("/clientes")
public class ClientController {
	
	@Autowired
	private IClientServices clientServices;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createClient(
			@Valid @RequestBody ClientCreateRequest clientCreateRequest, BindingResult result ){
		
		Map<String, Object> response = new HashMap<>();
		ClientCreateResponse newClient = null;
		
		if(result.hasErrors()) {
			List<String> errrors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errrors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			newClient = clientServices.createClient(clientCreateRequest);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar cliente nuevo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente a sido creado con éxito");
		response.put("cliente", newClient);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> updateClient(
			@Valid @RequestBody ClientUpdateRequest clientUpdateRequest,  BindingResult result){
		
		Map<String, Object> response = new HashMap<>();
		
		ClientInfoResponse currentClient = clientServices.findById(clientUpdateRequest.getClient().getIdperson());
		
		ClientUpdateRequest client = new ClientUpdateRequest();
		
		ClientInfoResponse updateClient = null;
				
		if(result.hasErrors()) {
			List<String> errrors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errrors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(currentClient == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID:  "
					.concat(clientUpdateRequest.getClient().getIdperson())
					.concat(" No existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			
			BeanUtils.copyProperties(currentClient, client);
			updateClient = clientServices.updateClient(client);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente a sido actualizado con éxito");
		response.put("cliente", updateClient);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/delete")
	public  ResponseEntity<?> deleteClient(@PathVariable String id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			clientServices.deleteClient(id);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		response.put("mensaje", "El cliente a sido eliminado con éxito");
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	
	}
	 

}

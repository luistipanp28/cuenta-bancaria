package com.ec.pichincha.services.imp;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ec.pichincha.dto.Client;
import com.ec.pichincha.dto.request.ClientCreateRequest;
import com.ec.pichincha.dto.request.ClientUpdateRequest;
import com.ec.pichincha.dto.response.ClientCreateResponse;
import com.ec.pichincha.dto.response.ClientInfoResponse;
import com.ec.pichincha.exception.ClientNotFoundException;
import com.ec.pichincha.model.ClientEntity;
import com.ec.pichincha.repository.IClientRepository;
import com.ec.pichincha.services.IClientServices;

@Service
public class ClientServicesImp implements IClientServices {
	
	@Autowired
	private IClientRepository clientRepository;
	

	@Override
	public ClientInfoResponse findById(String id) {
		
		Optional<ClientEntity> optionalClient = clientRepository.findById(id);
		
		if(optionalClient.isEmpty()) {
			throw new ClientNotFoundException();
		}
		
		ClientEntity clientEntity = optionalClient.get();
		Client client = new Client();
		BeanUtils.copyProperties(clientEntity, client);
		
		return ClientInfoResponse.builder().client(client).build();
			}

	@Override
	public ClientCreateResponse createClient(ClientCreateRequest request) {
		
		ClientEntity clientEntity = new ClientEntity();
		BeanUtils.copyProperties(request.getClient(), clientEntity);
		ClientEntity newCliente = clientRepository.save(clientEntity);
		
		ClientCreateResponse clientResponse = new ClientCreateResponse();
		
		BeanUtils.copyProperties(newCliente, clientResponse.getClient());
		
		return clientResponse;
				
				
	}
	
	
	public ClientInfoResponse updateClient(ClientUpdateRequest request ) {
		
		Client clientRequest = request.getClient();
		
		Optional<ClientEntity> clientRegister = clientRepository.findById(clientRequest.getIdperson());
		
		if(clientRegister.isEmpty()) {
			throw new ClientNotFoundException();
		}
		
		ClientEntity clientUpdate = clientRegister.get();
		
		clientUpdate.setName(clientRequest.getName());
		clientUpdate.setGender(clientRequest.getGender());
		clientUpdate.setAge(clientRequest.getAge());
		clientUpdate.setAddress(clientRequest.getAddress());
		clientUpdate.setPhoneNumber(clientRequest.getPhoneNumber());
		clientUpdate.setPassword(clientRequest.getPassword());
		clientUpdate.setStatus(Boolean.TRUE);	
		
		ClientEntity updateClient = clientRepository.save(clientUpdate);
		
		ClientInfoResponse updateClientResponse = new ClientInfoResponse();

		BeanUtils.copyProperties(updateClient, updateClientResponse);
			
		
		return updateClientResponse; 
	}

	public void deleteClient(String id) {
		
		Optional<ClientEntity> clientEntity = clientRepository.findById(id);
		
		if(clientEntity.isEmpty()) {
			throw new ClientNotFoundException();
		}		
		clientRepository.deleteById(id);
			
	}
}

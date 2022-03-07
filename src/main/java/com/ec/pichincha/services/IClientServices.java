package com.ec.pichincha.services;

import com.ec.pichincha.dto.request.ClientCreateRequest;
import com.ec.pichincha.dto.request.ClientUpdateRequest;
import com.ec.pichincha.dto.response.ClientCreateResponse;
import com.ec.pichincha.dto.response.ClientInfoResponse;

public interface IClientServices {
	
	public ClientInfoResponse findById(String id);
	
	public ClientCreateResponse createClient(ClientCreateRequest request);
	
	public ClientInfoResponse updateClient(ClientUpdateRequest request );
	
	public void deleteClient(String id);

	

}

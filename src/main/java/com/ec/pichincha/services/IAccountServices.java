package com.ec.pichincha.services;

import com.ec.pichincha.dto.request.AccountCreateRequest;
import com.ec.pichincha.dto.request.AccountUpdateRequest;
import com.ec.pichincha.dto.response.AccountCreateResponse;
import com.ec.pichincha.dto.response.AccountInfoResponse;

public interface IAccountServices {
	
	public AccountInfoResponse findById(Long id);
	
	public AccountCreateResponse createAccount(AccountCreateRequest request);
	
	public AccountInfoResponse updateAccount(AccountUpdateRequest request );
	
	public void deleteAccount(Long id);
	

}

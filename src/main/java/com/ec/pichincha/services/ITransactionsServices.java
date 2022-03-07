package com.ec.pichincha.services;

import com.ec.pichincha.dto.request.TransactionCreateRequest;
import com.ec.pichincha.dto.request.TransactionUpdateRequest;
import com.ec.pichincha.dto.response.ClientInfoResponse;
import com.ec.pichincha.dto.response.TransactionCreateResponse;
import com.ec.pichincha.dto.response.TransactionInfoResponse;

public interface ITransactionsServices {
	
	public TransactionInfoResponse findById(Long id);
	
	public TransactionCreateResponse createTransaction(TransactionCreateRequest request);
	
	public TransactionInfoResponse updateTransaction(TransactionUpdateRequest request );
	
	public void deleteTransaction(Long id);
	
	public ClientInfoResponse idClient(String id);

}

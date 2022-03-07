package com.ec.pichincha.services;

import org.springframework.data.jpa.repository.Query;

import com.ec.pichincha.dto.request.TransactionCreateRequest;
import com.ec.pichincha.dto.request.TransactionUpdateRequest;
import com.ec.pichincha.dto.response.TransactionCreateResponse;
import com.ec.pichincha.dto.response.TransactionInfoResponse;

public interface ITransactionsServices {
	
	public TransactionInfoResponse findById(Long id);
	
	public TransactionCreateResponse createTransaction(TransactionCreateRequest request);
	
	public TransactionInfoResponse updateTransaction(TransactionUpdateRequest request );
	
	public void deleteTransaction(Long id);
	
	@Query(nativeQuery = true, value = "select sum(tra.value)  from account ac\r\n"
			+ "inner join transaction tra on ac.idaccount = tra.idaccount\r\n"
			+ "where ac.accountnumber = :numCount \r\n"
			+ "and tra.datetransaction = GETDATE();")
	public int movimientosFecha (Integer numCount);
	

}

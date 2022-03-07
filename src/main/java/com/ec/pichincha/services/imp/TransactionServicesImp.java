package com.ec.pichincha.services.imp;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ec.pichincha.dto.Transaction;
import com.ec.pichincha.dto.request.TransactionCreateRequest;
import com.ec.pichincha.dto.request.TransactionUpdateRequest;
import com.ec.pichincha.dto.response.TransactionCreateResponse;
import com.ec.pichincha.dto.response.TransactionInfoResponse;
import com.ec.pichincha.exception.TransactionNotFoundException;
import com.ec.pichincha.model.TransactionsEntity;
import com.ec.pichincha.repository.ITransactionsRepository;
import com.ec.pichincha.services.IClientServices;
import com.ec.pichincha.services.ITransactionsServices;

@Service
public class TransactionServicesImp implements ITransactionsServices{
	
	ITransactionsRepository transactionsRepository;
	IClientServices clientServices;
	
	private int limiteDiario = 1000;
	

	@Override
	public TransactionInfoResponse findById(Long id) {
		
		Optional<TransactionsEntity> optionalClient = transactionsRepository.findById(id);
		
		if(optionalClient.isEmpty()) {
			throw new TransactionNotFoundException();
		}
		
		TransactionsEntity transactionsEntity = optionalClient.get();
		Transaction transaction = new Transaction();
		BeanUtils.copyProperties(transactionsEntity, transaction);
		
		return TransactionInfoResponse.builder().transaction(transaction).build();
	}
	

	@Override
	public TransactionCreateResponse createTransaction(TransactionCreateRequest request) {
		
		
		
		TransactionsEntity transactionsEntity = new TransactionsEntity();
		BeanUtils.copyProperties(request.getTransaction(), transactionsEntity);
		
		if(request.getTransaction().getValue() > 0 && request.getTransaction().getBalance() >=0) {
			
			request.getTransaction().setBalance(request.getTransaction().getValue() + request.getTransaction().getBalance());
			
		}else if(request.getTransaction().getBalance() == 0) {
			     return TransactionCreateResponse.builder().
			    		 mensaje("Saldo no disponibe").build();
			     
			    }if((request.getTransaction().getBalance() > request.getTransaction().getValue()) && request.getTransaction().getValue() < 0) {
			    	request.getTransaction().setBalance(request.getTransaction().getValue() - request.getTransaction().getBalance());
			    }
		
		TransactionsEntity newTransaction = transactionsRepository.save(transactionsEntity);
		
		TransactionCreateResponse transactionCreateResponse = new TransactionCreateResponse();
		
		BeanUtils.copyProperties(newTransaction, transactionCreateResponse.getTransaction());
		
		return transactionCreateResponse;
		
	}

	@Override
	public TransactionInfoResponse updateTransaction(TransactionUpdateRequest request) {
		
		Transaction transactionRequest = request.getTransaction();
		Optional<TransactionsEntity> optionalClient = transactionsRepository.findById(transactionRequest.getId());
		
		if(optionalClient.isEmpty()) {
			throw new TransactionNotFoundException();
		}
		
		TransactionsEntity updateTransaction = new TransactionsEntity();
		 updateTransaction.setDate(request.getTransaction().getDate());
		 updateTransaction.setTypeTransactions(request.getTransaction().getTypeTransactions());
		 updateTransaction.setValue(request.getTransaction().getValue());
		 updateTransaction.setBalance(request.getTransaction().getBalance());
		 
		 TransactionsEntity transactionUpdate = transactionsRepository.save(updateTransaction);
		 
		 TransactionInfoResponse updateTransactionResponse = new TransactionInfoResponse();
		 
		 BeanUtils.copyProperties(transactionUpdate, updateTransactionResponse);
		 
		return updateTransactionResponse;
		
	}

	@Override
	public void deleteTransaction(Long id) {
		
		Optional<TransactionsEntity> optionalTransaction = transactionsRepository.findById(id);
		
		if(optionalTransaction.isEmpty()) {
			throw new TransactionNotFoundException();
		}		
		transactionsRepository.deleteById(id);
		
	}
	

	@Override
	public int movimientosFecha(Integer numCount) {
		
		return 0;
	}

}

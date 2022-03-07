package com.ec.pichincha.services.imp;

import java.util.Optional;

import org.springframework.beans.BeanUtils;

import com.ec.pichincha.dto.Account;
import com.ec.pichincha.dto.request.AccountCreateRequest;
import com.ec.pichincha.dto.request.AccountUpdateRequest;
import com.ec.pichincha.dto.response.AccountCreateResponse;
import com.ec.pichincha.dto.response.AccountInfoResponse;
import com.ec.pichincha.exception.AccountNotFoundException;
import com.ec.pichincha.model.AccountEntity;
import com.ec.pichincha.repository.IAccountRepository;
import com.ec.pichincha.services.IAccountServices;

public class AccountServicesImp implements IAccountServices {
	
	IAccountRepository accountRepository;

	@Override
	public AccountInfoResponse findById(Long id) {
		
		Optional<AccountEntity> optionalAccount = accountRepository.findById(id);
		
		if(optionalAccount.isEmpty()) {
			throw new AccountNotFoundException();
		}
		
		AccountEntity accountEntity = optionalAccount.get();
		Account account = new Account();
		BeanUtils.copyProperties(accountEntity, account);
		
		return AccountInfoResponse.builder().account(account).build();
	}

	@Override
	public AccountCreateResponse createAccount(AccountCreateRequest request) {
		
		AccountEntity accountEntity = new AccountEntity();
		BeanUtils.copyProperties(request.getAccount(), accountEntity);
		AccountEntity newAccount = accountRepository.save(accountEntity);
		
		AccountCreateResponse accountCreateResponse = new AccountCreateResponse();
		
		BeanUtils.copyProperties(newAccount, accountCreateResponse.getAccount());
		
		return accountCreateResponse;
		
	}

	@Override
	public AccountInfoResponse updateAccount(AccountUpdateRequest request) {
		
		Account accountRequest = request.getAccount();
		Optional<AccountEntity> optionalAccount = accountRepository.findById(accountRequest.getId());
		
		if(optionalAccount.isEmpty()) {
			throw new AccountNotFoundException();
		}
		
		AccountEntity updateAccount = new AccountEntity();
		
		updateAccount.setAccountNumber(request.getAccount().getAccountNumber());
		updateAccount.setTypeAccount(request.getAccount().getTypeAccount());
		updateAccount.setInitialBalance(request.getAccount().getInitialBalance());
		updateAccount.setStatus(Boolean.TRUE);	
		 
		 AccountEntity accountUpdate = accountRepository.save(updateAccount);
		 
		 AccountInfoResponse updateAccountResponse = new AccountInfoResponse();
		 
		 BeanUtils.copyProperties(accountUpdate, updateAccountResponse);
		 
		return updateAccountResponse;
		
	}

	@Override
	public void deleteAccount(Long id) {
		
		Optional<AccountEntity> optionalAccount = accountRepository.findById(id);
		
		if(optionalAccount.isEmpty()) {
			throw new AccountNotFoundException();
		}		
		accountRepository.deleteById(id);
		
	}

}

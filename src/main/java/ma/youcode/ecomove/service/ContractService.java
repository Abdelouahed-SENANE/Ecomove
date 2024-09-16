package ma.youcode.ecomove.service;

import java.util.List;

import ma.youcode.ecomove.entity.Contract;

public interface ContractService {
    Contract addContract(Contract contract);
    Contract updateContract(Contract contract);
    List<Contract> getAllContracts();
    Contract deleteContract(String contractId);
    Contract getContract(String contractId) ;
    List<Contract> getValidatedContracts();
    Contract getContractWithSpecialOffers(String contractId);
}

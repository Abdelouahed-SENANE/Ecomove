package ma.youcode.ecomove.service.implementations;

import java.util.List;

import ma.youcode.ecomove.entity.Contract;
import ma.youcode.ecomove.repository.ContractRepository;
import ma.youcode.ecomove.repository.implementations.ContractRepositoryImp;
import ma.youcode.ecomove.service.ContractService;

public class ContractServiceImp implements ContractService {
    private final ContractRepository repository;

    public ContractServiceImp()  {
        this.repository = new ContractRepositoryImp();
    }

    @Override
    public Contract addContract(Contract contract)  {
        return repository.save(contract);
    }

    @Override
    public Contract updateContract(Contract contract)  {

        Contract updatedContract = repository.update(contract);
        if (updatedContract != null) {
            return  updatedContract;
        }else {
            return null;
        }
    }

    @Override
    public List<Contract> getAllContracts() {
        return repository.findAllContracts();
    }

    @Override
    public Contract deleteContract(String contractId)  {
        Contract contract = repository.findContractById(contractId);
        if (contract != null) {
            repository.delete(contract);
        }
        return contract;
    }

    @Override
    public Contract getContract(String contractId)  {
        return repository.findContractById(contractId);
    }
    public Contract getContractWithSpecialOffers(String contractId)  {
        return repository.findContractWithSpecialOffers(contractId);
    }

    @Override
    public List<Contract> getValidatedContracts() {
        return repository.findValidatedContract();
    }
}

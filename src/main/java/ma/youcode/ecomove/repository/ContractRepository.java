package ma.youcode.ecomove.repository;

import java.util.List;

import ma.youcode.ecomove.entity.Contract;

public interface ContractRepository {

    Contract save(Contract contract);

    Contract update(Contract contract);

    Contract delete(Contract contract);

    Contract findContractById(String contractId);
    Contract findContractWithSpecialOffers(String contractId);

    List<Contract> findAllContracts();

    List<Contract> findValidatedContract();
}

package ma.youcode.transport.ui.admin;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import ma.youcode.transport.entity.Contract;
import ma.youcode.transport.entity.Partner;
import ma.youcode.transport.enums.ContractStatus;
import ma.youcode.transport.enums.PartnershipStatus;
import ma.youcode.transport.repository.PartnerRepository;
import ma.youcode.transport.repository.implementations.PartnerRepositoryImp;
import ma.youcode.transport.service.ContractService;
import ma.youcode.transport.service.implementations.ContractServiceImp;
import ma.youcode.transport.ui.Menu;
import ma.youcode.transport.utils.DisplayUtil;
import ma.youcode.transport.validator.Validator;

public class ContractUI {
    private int choice;
    private final ContractService service;
    private final Scanner sc;
    private final Validator validator;
    private final PartnerRepository partnerRepo;
    public ContractUI()  {
        this.sc = new Scanner(System.in);
        this.service = new ContractServiceImp();
        this.validator = new Validator();
        this.partnerRepo = new PartnerRepositoryImp();
    }

    public int getChoice() {
        while (true) {
            if (sc.hasNextInt()) {
                this.choice = sc.nextInt();
                if (this.choice >= 0 && this.choice <= 4) {
                    return this.choice;
                } else {
                    System.out.println("Number out of range. Please enter a number between 0 - 4.");
                }
            } else {
                System.out.println("Invalid input. Please enter a correct number.");
                sc.next();
            }
        }
    }

    public Menu start(Menu menu)  {
        do {
            System.out.println("\n================ Manage Contracts ================");
            System.out.println("1. Add new contract");
            System.out.println("2. Edit contract");
            System.out.println("3. Delete contract");
            System.out.println("4. Show Contracts");
            System.out.println("0. Go back");
            System.out.println("================ Manage Contracts ================\n");
            System.out.print("Choose an option: ");

            this.choice = getChoice();

            switch (this.choice) {
                case 1:
                    System.out.println("You chose option 1: Create new contract\n");
                    Contract newContract = new Contract();
                    newContract.setContractId(UUID.randomUUID().toString());

                    newContract.setStartingDate(validator.getValidTimestampInput("Enter Starting Date (YYYY-MM-DD HH:MM:SS): "));
                    newContract.setEndDate(validator.getValidTimestampInput("Enter End Date (YYYY-MM-DD HH:MM:SS): "));
                    newContract.setSpecialRate(validator.getValidDoubleInput("Enter Special Rate: ",
                            "Please enter a valid rate for the Special Rate."));
                    newContract.setAgreementConditions(validator.getValidStringInput("Enter Agreement Conditions: ",
                            "Please enter a correct value for the Agreement Conditions."));
                    newContract.setRenewable(validator.getValidBooleanInput("Is the contract renewable? (true/false): ",
                            "Please enter a valid boolean value for Renewable."));
                    newContract.setContractStatus(validator.choiceOption(ContractStatus.class));
                    PartnerUI partnerUi = new PartnerUI();
                    partnerUi.displayPartners();
                    sc.nextLine();
                    while (true) {
                        System.out.println("Enter Partner ID for this contract: ");
                        String partnerId = sc.nextLine().trim();
                        if (!partnerId.isEmpty()) {
                            Partner partner = partnerRepo.findPartnerById(partnerId);
                            if (partner != null) {
                                if (partner.getPartnerStatus() == PartnershipStatus.ACTIVE) {
                                    newContract.setPartner(partner);
                                    break;
                                }else {
                                    System.out.println("Cannot Create contract with this partner because his Status is not " + partner.getPartnerStatus());
                                }
                            }
                        }else {
                            System.out.println("Invalid input. Please enter a valid partner ID.");
                        }
                    }


                    Contract addedContract = service.addContract(newContract);
                    if (addedContract != null) {
                        System.out.println("New contract " + addedContract.getContractId() + " added successfully.");
                    }
                    break;

                case 2:
                    System.out.println("You chose option 2: Edit contract \n");
                    this.displayContracts();
                    sc.nextLine();
                    Contract existingContract = null;

                    while (true) {
                        System.out.println("Enter the Contract ID you wish to update: ");
                        String editContractId = sc.nextLine().trim();
                
                        if (editContractId.isEmpty()) {
                            System.out.println("Contract ID cannot be empty. Please enter a valid Contract ID.");
                            continue; 
                        }

                        existingContract = service.getContract(editContractId);
                        if (existingContract != null) {
                            break;
                        } else {
                            System.out.println("Contract with ID " + editContractId + " does not exist. Please check the Contract ID and try again.");
                        }
                    }
                
                    existingContract.setStartingDate(validator.getValidTimestampInput("Enter Starting Date (YYYY-MM-DD HH:MM:SS): "));
                    existingContract.setEndDate(validator.getValidTimestampInput("Enter End Date (YYYY-MM-DD HH:MM:SS): "));
                    existingContract.setSpecialRate(validator.getValidDoubleInput("Enter Special Rate: ",
                            "Please enter a valid rate for the Special Rate."));
                    existingContract.setAgreementConditions(validator.getValidStringInput("Enter Agreement Conditions: ",
                            "Please enter a correct value for the Agreement Conditions."));
                    existingContract.setRenewable(validator.getValidBooleanInput("Is the contract renewable? (true/false): ",
                            "Please enter a valid boolean value for Renewable."));
                    existingContract.setContractStatus(validator.choiceOption(ContractStatus.class));
                
                    Contract updatedContract = service.updateContract(existingContract);
                    if (updatedContract != null) {
                        System.out.println("Contract " + updatedContract.getContractId() + " updated successfully.");
                    } else {
                        System.out.println("Cannot update this contract.");
                    }
                    
                    break;

                case 3:
                    System.out.println("You chose option 3: Delete contract \n");
                    String contractId;
                    while (true) {
                        this.displayContracts();
                        System.out.println("Enter contract ID you want to delete: ");
                        contractId = sc.nextLine().trim();
                        if (contractId.isEmpty()) {
                            System.out.println("Contract ID cannot be empty. Please check the Contract ID and try again.");
                            continue;
                        }
                        existingContract = service.getContract(contractId);
                        if (existingContract != null) {
                            break;
                        } else {
                            System.out.println("Contract with ID " + contractId + " does not exist. Please check the Contract ID and try again.");
                        }
                    }

                    Contract contract = service.deleteContract(contractId);
                    if (contract != null) {
                        System.out.println("Contract " + contract.getContractId() + " deleted successfully.");
                    } else {
                        System.out.println("Cannot delete this contract.");
                    }

                    break;

                case 4: // Show contracts
                    System.out.println("You chose option 4: Show contracts \n");
                    this.displayContracts();
                    break;

                case 0: // Go back
                    System.out.println("Back to main menu \n");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (this.choice != 0);

        return menu;
    }

    public void displayContracts() {
        List<Contract> contractsList = service.getAllContracts();
                        
        String[] headers = {
            "ContractId", "StartingDate", "EndDate", "SpecialRate",
            "AgreementConditions", "Renewable", "ContractStatus", "partner.partnerId" , "partner.companyName"
        };
        DisplayUtil.displayTable(headers, contractsList);
    }
    public void displayValidatedContract()  {
        List<Contract> contractsList = service.getValidatedContracts();
        for (Contract contract : contractsList) {
            System.out.println(contract.getContractStatus());
        }
        String[] headers = {
                "ContractId", "StartingDate", "EndDate", "SpecialRate",
                "AgreementConditions", "Renewable", "ContractStatus", "partner.partnerId" , "partner.companyName"
        };
        DisplayUtil.displayTable(headers, contractsList);
        for (Contract contract : contractsList) {
            this.displaySpecialOffers(contract.getSpecialOffers());
        }
    }
    public static void displaySpecialOffers(List<?> offers) {
        if (offers == null || offers.isEmpty()) {
            System.out.println("No special offers available.");
            return;
        }
        System.out.println("Special offers available : ");
        for (Object offer : offers) {
            Field[] fields = offer.getClass().getDeclaredFields();
            StringBuilder offerDetails = new StringBuilder("  - ");
            for (Field field : fields) {
                field.setAccessible(true);  // Access private fields
                try {
                    Object fieldValue = field.get(offer);
                    if (fieldValue != null) {
                        offerDetails.append(field.getName())
                                .append(": ")
                                .append(fieldValue)
                                .append(", ");
                    }
                } catch (IllegalAccessException e) {
                    System.err.println("Error accessing field " + field.getName() + ": " + e.getMessage());
                }
            }
            System.out.println(offerDetails.toString().replaceAll(", $", ""));
        }
    }
}

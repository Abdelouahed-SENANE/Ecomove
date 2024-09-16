package ma.youcode.ecomove.ui.admin;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import ma.youcode.ecomove.entity.Contract;
import ma.youcode.ecomove.entity.SpecialOffer;
import ma.youcode.ecomove.enums.ContractStatus;
import ma.youcode.ecomove.enums.DiscountType;
import ma.youcode.ecomove.enums.OfferStatus;
import ma.youcode.ecomove.repository.ContractRepository;
import ma.youcode.ecomove.repository.implementations.ContractRepositoryImp;
import ma.youcode.ecomove.service.SpecialOfferService;
import ma.youcode.ecomove.service.implementations.SpecialOfferServiceImp;
import ma.youcode.ecomove.ui.Menu;
import ma.youcode.ecomove.utils.DisplayUtil;
import ma.youcode.ecomove.validator.Validator;

public class SpecialOfferUI {
    private int choice;
    private final SpecialOfferService service;
    private final Scanner sc;
    private final Validator validator;
    private final ContractUI contractUI;
    private final ContractRepository contractRepository;

    public SpecialOfferUI()  {
        sc = new Scanner(System.in);
        service = new SpecialOfferServiceImp();
        contractUI = new ContractUI();
        validator = new Validator();
        this.contractRepository = new ContractRepositoryImp();
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

    public Menu start(Menu menu) {
        do {
            System.out.println("\n================ Manage Special Offers ================");
            System.out.println("1. Add new special offer");
            System.out.println("2. Edit special offer");
            System.out.println("3. Delete special offer");
            System.out.println("4. Show Special Offers");
            System.out.println("0. Go back");
            System.out.println("================ Manage Special Offers ================\n");
            System.out.print("Choose an option: ");

            this.choice = getChoice();

            switch (this.choice) {
                case 1: 
                    System.out.println("You chose option 1: Create new special offer\n");
                    SpecialOffer newSpecialOffer = new SpecialOffer();
                    newSpecialOffer.setOfferId(UUID.randomUUID().toString());

                    newSpecialOffer.setOfferName(validator.getValidStringInput("Enter Offer Name: ",
                            "Please enter a correct value for the Offer Name."));
                    newSpecialOffer.setOfferDescription(validator.getValidStringInput("Enter Offer Description: ",
                            "Please enter a correct value for the Offer Description."));
                    newSpecialOffer.setStartingDate(validator.getValidLocalDate("Special offer starting date "));
                    newSpecialOffer.setEndDate(validator.getValidLocalDate("Special offer ending date "));
                    newSpecialOffer.setDiscountType(validator.choiceOption(DiscountType.class));
                    sc.nextLine();

                    newSpecialOffer.setDiscountValue(validator.getValidDoubleInput("Enter Discount Value: ",
                            "Please enter a valid discount value."));
                    newSpecialOffer.setConditions(validator.getValidStringInput("Enter Conditions: ",
                            "Please enter a correct value for the Conditions."));
                    newSpecialOffer.setOfferStatus(validator.choiceOption(OfferStatus.class));
                    contractUI.displayContracts();

                    String contractId;
                    while (true) {
                        System.out.println("Enter Contract ID for this special offer: ");
                        contractId = sc.nextLine().trim();
                        if (!contractId.isEmpty()) {
                            Contract contract = contractRepository.findContractById(contractId);
                            if (contract !=  null) {
                                if (contract.getContractStatus() == ContractStatus.ONGOING) {
                                    newSpecialOffer.setContract(contract);
                                    System.out.println("Contract successfully assigned to the special offer.");
                                    break;
                                } else {
                                    System.out.println("Contract cannot be assigned because this contract is " + contract.getContractStatus() + ".");
                                }
                            }
                        }else {
                            System.out.println("Please provide correct contract id, Try again : ");
                        }
                    }
                    SpecialOffer addedSpecialOffer = service.addSpecialOffer(newSpecialOffer);
                            if (addedSpecialOffer != null) {
                                System.out.println("New special offer " + addedSpecialOffer.getOfferName() + " added successfully.");
                            }
                            break;
                case 2: 
                    System.out.println("You chose option 2: Edit special offer \n");
                    this.displaySpecialOffers();
                    sc.nextLine();
                    SpecialOffer existingSpecialOffer = null;
                    String editSpecialOfferId = "";
                
                    while (true) {
                        System.out.println("Enter the Special Offer ID you wish to update: ");
                        editSpecialOfferId = sc.nextLine().trim();
                
                        if (editSpecialOfferId.isEmpty()) {
                            System.out.println("Special Offer ID cannot be empty. Please enter a valid Special Offer ID.");
                            continue; 
                        }
                
                        existingSpecialOffer = service.getSpecialOffer(editSpecialOfferId);
                
                        if (existingSpecialOffer != null) {
                            break;
                        } else {
                            System.out.println("Special Offer with ID " + editSpecialOfferId + " does not exist. Please check the Special Offer ID and try again.");
                        }
                    }
                
                    existingSpecialOffer.setOfferName(validator.getValidStringInput("Enter Offer Name: ",
                            "Please enter a correct value for the Offer Name."));
                    existingSpecialOffer.setOfferDescription(validator.getValidStringInput("Enter Offer Description: ",
                            "Please enter a correct value for the Offer Description."));
                    existingSpecialOffer.setStartingDate(validator.getValidLocalDate("Special offer updated Starting date"));
                    existingSpecialOffer.setEndDate(validator.getValidLocalDate("Special offer updated Ending date"));
                    existingSpecialOffer.setDiscountType(validator.choiceOption(DiscountType.class));
                    existingSpecialOffer.setDiscountValue(validator.getValidDoubleInput("Enter Discount Value: ",
                            "Please enter a valid discount value."));
                    existingSpecialOffer.setConditions(validator.getValidStringInput("Enter Conditions: ",
                            "Please enter a correct value for the Conditions."));
                    existingSpecialOffer.setOfferStatus(validator.choiceOption(OfferStatus.class));
                    
                    SpecialOffer updatedSpecialOffer = service.updateSpecialOffer(existingSpecialOffer);
                
                    if (updatedSpecialOffer != null) {
                        System.out.println("Special Offer " + updatedSpecialOffer.getOfferId() + " updated successfully.");
                    } else {
                        System.out.println("Cannot update this special offer.");
                    }
                    
                    break;

                case 3: 
                    System.out.println("You chose option 3: Delete special offer \n");
                    String offerId;
                    while (true) {
                        this.displaySpecialOffers();
                        System.out.println("Enter special offer ID you want to delete: ");
                        offerId = sc.nextLine().trim();
                        if (offerId.isEmpty()) {
                            System.out.println("Special Offer ID cannot be empty. Please check the Special Offer ID and try again.");
                            continue;
                        }
                        existingSpecialOffer = service.getSpecialOffer(offerId);
                        if (existingSpecialOffer != null) {
                            break;
                        } else {
                            System.out.println("Special Offer with ID " + offerId + " does not exist. Please check the Special Offer ID and try again.");
                        }
                    }

                    SpecialOffer specialOffer = service.deleteSpecialOffer(offerId);
                    if (specialOffer != null) {
                        System.out.println("Special Offer " + specialOffer.getOfferId() + " deleted successfully.");
                    } else {
                        System.out.println("Cannot delete this special offer.");
                    }

                    break;

                case 4:
                    System.out.println("You chose option 4: Show special offers \n");
                    this.displaySpecialOffers();
                    break;

                case 0:
                    System.out.println("Back to main menu \n");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (this.choice != 0);

        return menu;
    }

    private void displaySpecialOffers()  {
        List<SpecialOffer> specialOffersList = service.getAllSpecialOffers();
                    
        String[] headers = {
            "OfferId", "OfferName", "OfferDescription", "StartingDate",
            "EndDate", "DiscountType", "DiscountValue", "Conditions", "OfferStatus", "Contract.contractId"
        };

        DisplayUtil.displayTable(headers, specialOffersList);
    }
}

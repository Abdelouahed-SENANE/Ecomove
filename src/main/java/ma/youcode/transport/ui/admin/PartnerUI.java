package ma.youcode.transport.ui.admin;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import ma.youcode.transport.entity.Partner;
import ma.youcode.transport.enums.PartnershipStatus;
import ma.youcode.transport.enums.TransportationType;
import ma.youcode.transport.service.PartenerService;
import ma.youcode.transport.service.implementations.PartnersServiceImp;
import ma.youcode.transport.ui.Menu;
import ma.youcode.transport.utils.DisplayUtil;
import ma.youcode.transport.validator.Validator;

public class PartnerUI {
    private int choice;
    private final PartenerService partnerService;
    private final Scanner sc;
    private final Validator validator;

    public PartnerUI()  {
        this.sc = new Scanner(System.in);
        this.partnerService = new PartnersServiceImp();
        this.validator = new Validator();
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
            System.out.println("\n================ Manage Partners ================");
            System.out.println("1. Add new partner");
            System.out.println("2. Edit partner");
            System.out.println("3. Delete partner");
            System.out.println("4. Show Partners");
            System.out.println("0. Go back");
            System.out.println("================ Manage Partners ================\n");
            System.out.print("Choose an option: ");

            this.choice = getChoice();

            switch (this.choice) {
                case 1: // Add new partner
                    System.out.println("You chose option 1: Create new partner\n");
                    Partner newPartner = new Partner();
                    newPartner.setPartnerId(UUID.randomUUID().toString());

                    // Collecting details for new partner
                    newPartner.setCompanyName(validator.getValidStringInput("Enter Company Name: ",
                            "Please enter a correct value for the company name."));
                    newPartner.setCommercialContact(validator.getValidStringInput("Enter Commercial Contact: ",
                            "Please enter a correct value for the commercial contact."));
                    newPartner.setTransportationType(validator.choiceOption(TransportationType.class));
                    newPartner.setGeographicZone(validator.getValidStringInput("Enter Geographic Zone: ",
                            "Please enter a correct value for the Geographic Zone."));
                    newPartner.setSpecialConditions(validator.getValidStringInput("Enter Special Conditions: ",
                            "Please enter a correct value for the Special Conditions."));
                    newPartner.setPartnerStatus(validator.choiceOption(PartnershipStatus.class));

                    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                    newPartner.setCreationDate(currentTimestamp);

                    Partner addedPartner = partnerService.addPartner(newPartner);
                    if (addedPartner != null) {
                        System.out.println("New partner " + addedPartner.getCompanyName() + " added successfully.");
                    }
                    break;

                    case 2:
                    System.out.println("You chose option 2: Edit partner \n");
                    this.displayPartners(); 
                    sc.nextLine();
                    Partner existingPartner = null;
                    String editPartnerId = "";
                
                    while (true) {
                        System.out.println("Enter the Partner ID you wish to update: ");
                        editPartnerId = sc.nextLine().trim();
                
                        if (editPartnerId.isEmpty()) {
                            System.out.println("Partner ID cannot be empty. Please enter a valid Partner ID.");
                            continue; 
                        }
                
                        existingPartner = partnerService.getPartner(editPartnerId);
                
                        if (existingPartner != null) {
                            break;
                        } else {
                            System.out.println("Partner with ID " + editPartnerId + " does not exist. Please check the Partner ID and try again.");
                        }
                    }
                
                    existingPartner.setCompanyName(validator.getValidStringInput("Enter Company Name: ",
                            "Please enter a correct value for the company name."));
                    existingPartner.setCommercialContact(validator.getValidStringInput("Enter Commercial Contact: ",
                            "Please enter a correct value for the commercial contact."));
                    existingPartner.setTransportationType(validator.choiceOption(TransportationType.class));
                    existingPartner.setGeographicZone(validator.getValidStringInput("Enter Geographic Zone: ",
                            "Please enter a correct value for the Geographic Zone."));
                    existingPartner.setSpecialConditions(validator.getValidStringInput("Enter Special Conditions: ",
                            "Please enter a correct value for the Special Conditions."));
                    existingPartner.setPartnerStatus(validator.choiceOption(PartnershipStatus.class));
                
                    Partner updatedPartner = partnerService.updatePartner(existingPartner);
                
                    if (updatedPartner != null) {
                        System.out.println("Partner " + updatedPartner.getCompanyName() + " updated successfully.");
                    } else {
                        System.out.println("Cannot update this partner.");
                    }
                    
                    break;
                

                case 3: 
                    System.out.println("You chose option 3: Delete partner \n");
                    String partnerId;
                    while (true) {
                        this.displayPartners();
                        System.out.println("Enter partner you want to delete");
                         partnerId = sc.nextLine().trim();
                         if (partnerId.isEmpty()) {
                            System.out.println("Partner ID cannot be emptym Please check the Partner ID and try again.");
                            continue;
                         }
                        existingPartner = partnerService.getPartner(partnerId);
                        if (existingPartner != null) {
                            break;
                        }else{
                            System.out.println("Partner with ID " + partnerId + " does not exist. Please check the Partner ID and try again.");
                        }
                    }

                    Partner partner = partnerService.deletePartner(partnerId);
                    if (partner != null) {
                        System.out.println("Partner " + partner.getCompanyName() + " Deleted successfully.");

                    }else{
                        System.out.println("Cannot delete this partner.");

                    }

                    break;

                case 4: // Show partners
                    System.out.println("You chose option 4: Show partners \n");
                    this.displayPartners();
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

    public void displayPartners()  {
        List<Partner> partnersList = partnerService.getAllPartners();
    
        String[] headers = {
            "PartnerId", "CompanyName", "CommercialContact", "TransportationType",
            "GeographicZone", "SpecialConditions", "PartnerStatus", "CreationDate"
        };
    
        DisplayUtil.displayTable(headers, partnersList );
    }
}

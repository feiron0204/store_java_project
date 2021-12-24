package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.AddressController;
import controller.CustomerController;
import controller.PaymentController;
import controller.RentalController;
import controller.StaffController;
import controller.StoreController;
import model.CustomerDTO;
import model.StoreDTO;
import util.ScannerUtil;

public class CustomerViewer {
    private Scanner scanner;
    private CustomerDTO customer;
    private StoreDTO store;

    public CustomerViewer(CustomerDTO customer, StoreDTO store, Scanner scanner) {
        this.customer = customer;
        this.scanner = scanner;
        this.store = store;
    }

    public int printList(int storeId) {
        CustomerController customerController = new CustomerController(new MySqlConnector());
        boolean customerSwitch = true;
        CustomerDTO customer = new CustomerDTO();
        int page = 1;
        String message;
        while (customerSwitch) {
            ArrayList<CustomerDTO> list = customerController.selectAllByStoreId(storeId);
            int pageMax = list.size() / 10;
            if (list.size() % 10 > 0) {
                pageMax = list.size() / 10 + 1;
            }
            int size=0;
            if(list.size()%10==0) {
                size=10;
            }
            if (list.isEmpty()) {
                System.out.println("해당 조건을 만족하는 회원이 없습니다.");
                customerSwitch = false;
            } else {
                if (page == pageMax) {
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                        System.out.printf("회원번호: %d 회원명: %s %s\n", list.get(i).getCustomerId(),
                                list.get(i).getFirstName(), list.get(i).getLastName());
                    }
                } else {
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                        System.out.printf("회원번호: %d 회원명: %s %s\n", list.get(i).getCustomerId(),
                                list.get(i).getFirstName(), list.get(i).getLastName());
                    }
                }

                System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n", pageMax, page);

                message = new String("회원번호를 선택하시거나 돌아가시려면 0 을 눌러주세요.");
                Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                if (userChoice instanceof Integer) {

                    for (CustomerDTO c : list) {
                        if (c.getCustomerId() == (Integer) userChoice || (Integer) userChoice == 0) {
                            if ((Integer) userChoice != 0) {

                                customer = customerController.selectOne((Integer) userChoice);
                            }
                            customerSwitch = false;
                        }
                    }
                    if (customerSwitch) {
                        System.out.println("리스트에 없는 번호입니다.\n");
                    }
                } else {
                    if ((Character) userChoice == 'n') {
                        if (page == pageMax) {
                            System.out.println("마지막 페이지입니다.\n");
                        } else {
                            page++;
                        }
                    } else if ((Character) userChoice == 'p') {
                        if (page == 1) {
                            System.out.println("처음 페이지 입니다.\n");
                        } else {
                            page--;
                        }
                    }else if((Character) userChoice=='s') {
                        message = new String("원하는 페이지번호를 입력해주세요");
                        page=ScannerUtil.nextInt(scanner, message,1,pageMax);
                    }
                }
            }
        }
        return customer.getCustomerId();
    }

    public CustomerDTO logIn() {
        CustomerDTO customer = null;
        CustomerController customerController = new CustomerController(new MySqlConnector());
        String message = new String("first_name을 입력해주세요.");
        String firstName = ScannerUtil.nextLine(scanner, message);
        message = new String("last_name을 입력해주세요.");
        String lastName = ScannerUtil.nextLine(scanner, message);

        customer = customerController.auth(firstName, lastName);
        if(customer!=null) {
            
            if(customer.getActive()==0) {
                System.out.println("오랫동안 방문하지않아 휴면회원이 되셨습니다. 매니져에게 문의 후 로그인해주세요.");
                return null;
            }
        }
        
        return customer;
    }

    public void register() {
        CustomerDTO customer = new CustomerDTO();
        CustomerController customerController = new CustomerController(new MySqlConnector());
        customer.setStoreId(store.getStoreId());
        String message = new String("first_name을 입력해주세요.");
        String firstName = ScannerUtil.nextLine(scanner, message);
        message = new String("last_name을 입력해주세요.");
        String lastName = ScannerUtil.nextLine(scanner, message);
        if (customerController.auth(firstName, lastName) != null) {
            System.out.println("이미 존재하는 회원입니다. 로그인해주세요.");
        } else {
            message = new String("email을 입력해주세요.");
            String regEx = "^[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z]+.[a-zA-Z]+$";
            String email = ScannerUtil.nextLine(scanner, message);
            while (!email.matches(regEx)) {
                message = new String("형식에 맞는 email을 입력해주세요.(~~@~~.~~)");
                email = ScannerUtil.nextLine(scanner, message);
            }
            customer.setEmail(email);
        System.out.println("주소번호를 검색합니다.");
        AddressViewer addressViewer = new AddressViewer(scanner);
        int addressId = addressViewer.selectAddress();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddressId(addressId);
        customerController.insert(customer);
        System.out.println("회원등록이 완료되었습니다. 로그인해주세요\n");
        }
    }

    public void logInMenu() {
        CustomerController customerController = new CustomerController(new MySqlConnector());
        while (customerController.selectOne(customer.getCustomerId())!=null) {

            String message = new String("1. 비디오 대여/반납 2. 내 정보 보기 3. 로그아웃");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            if (userChoice == 1) {
                RentalViewer rentalViewer = new RentalViewer(customer,store,scanner);
                rentalViewer.rentalMenu();
            } else if (userChoice == 2) {
                customerInfo(customer.getCustomerId());
            } else if (userChoice == 3) {
                System.out.println("대여점 메뉴로 돌아갑니다.\n");
                break;
            }
        }
    }

    private void customerInfo(int customerId) {
        StoreController storeController = new StoreController(new MySqlConnector());
        AddressController addressController = new AddressController(new MySqlConnector());
        StaffController staffController = new StaffController(new MySqlConnector());
        RentalController rentalController = new RentalController(new MySqlConnector());
        PaymentController paymentController = new PaymentController(new MySqlConnector());
        CustomerController customerController = new CustomerController(new MySqlConnector());
        while (customerController.selectOne(customerId)!=null) {

            System.out.println("====================================================");
            System.out.println("회원 번호: " + customerId);
            System.out.println("회원등록한 대여점: "
                    + staffController.selectOne(storeController
                            .selectOne(customerController.selectOne(customerId).getStoreId()).getManagerStaffId())
                            .getFirstName()
                    + " "
                    + staffController.selectOne(storeController
                            .selectOne(customerController.selectOne(customerId).getStoreId()).getManagerStaffId())
                            .getLastName()
                    + "의 대여점");
            System.out.println("회원 이름: " + customerController.selectOne(customerId).getFirstName() + " "
                    + customerController.selectOne(customerId).getLastName());
            System.out.println("회원 이메일: " + customerController.selectOne(customerId).getEmail());
            System.out.println("주소: " + addressController
                    .selectOne(customerController.selectOne(customerId).getAddressId()).getAddress());
            System.out.println("전화번호: "
                    + addressController.selectOne(customerController.selectOne(customerId).getAddressId()).getPhone());
            System.out.println("----------------------------------------------------");
            System.out.println("현재까지 대여한 횟수: " + rentalController.countByCustomerId(customerId) + "회");
            System.out.printf("현재까지 지불한 금액: %.2f$\n" , paymentController.sumByCustomerId(customerId) );
            String message = new String("1. 나의 대여 목록 보기 2. 나의 지불 내역 보기 3. 회원 정보 수정 4. 회원 탈퇴 5. 뒤로가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 5);
            if (userChoice == 1) {
                RentalViewer rentalViewer = new RentalViewer(customer,store,scanner);
                rentalViewer.printList();
            } else if (userChoice == 3) {
                update(customerId);
            } else if (userChoice == 4) {
                delete(customerId);
            } else if (userChoice == 5) {
                System.out.println("로그인메뉴로 돌아갑니다.");
                break;
            } else if(userChoice==2) {
                PaymentViewer paymentViewer = new PaymentViewer(null,customer,scanner);
                paymentViewer.printList();
            }
        }
    }
    
    private void update(int customerId) {
        CustomerController customerController = new CustomerController(new MySqlConnector());
        CustomerDTO customer = customerController.selectOne(customerId);
        String message = new String("변경할 email을 입력해주세요.");
        String regEx = "^[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z]+.[a-zA-Z]+$";
        String email = ScannerUtil.nextLine(scanner, message);
        while (!email.matches(regEx)) {
            message = new String("형식에 맞는 email을 입력해주세요.(~~@~~.~~)");
            email = ScannerUtil.nextLine(scanner, message);
        }
        System.out.println("주소번호를 검색합니다.");
        AddressViewer addressViewer = new AddressViewer(scanner);
        int addressId = addressViewer.selectAddress();
        
        message = new String("정말 변경하시겠습니까? Y/N");
        String yesNo=ScannerUtil.nextLine(scanner, message);
        if(yesNo.equalsIgnoreCase("y")) {
            customer.setEmail(email);
            customer.setAddressId(addressId);
            customerController.update(customer);
            System.out.println("수정이 완료되었습니다.\n");
        }else {
            System.out.println("수정이 취소되었습니다.\n");
        }
    }
    
    private void delete(int customerId) {
        CustomerController customerController = new CustomerController(new MySqlConnector());
        AddressController addressController = new AddressController(new MySqlConnector());
        String message = new String("본인확인을 위해 본인이 거주하는 집의 우편번호를 입력해주세요.");
        String postalCode = ScannerUtil.nextLine(scanner, message);
        if(postalCode.equals(addressController.selectOne(customerController.selectOne(customerId).getAddressId()).getPostalCode())) {
            message = new String("정말 삭제하시겠습니까? Y/N");
            String yesNo=ScannerUtil.nextLine(scanner, message);
            if(yesNo.equalsIgnoreCase("y")) {
                CustomerDTO c=new CustomerDTO();
                c.setCustomerId(customerId);
                customerController.delete(c);
                System.out.println("삭제가 완료되었습니다.\n");
            }else {
                System.out.println("삭제가 취소되었습니다.\n");
            }
        }else {
            System.out.println("우편번호가 다릅니다.\n 삭제가 취소되었습니다.\n");
        }
        
        
    }
}

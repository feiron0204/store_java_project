package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.StaffController;
import controller.StoreController;
import model.CustomerDTO;
import model.StaffDTO;
import model.StoreDTO;
import util.ScannerUtil;

public class StoreViewer {
    private CustomerDTO customer;
    private StaffDTO staff;
    private Scanner scanner;

    public StoreViewer(CustomerDTO customer, StaffDTO staff, Scanner scanner) {
        this.customer = customer;
        this.staff = staff;
        this.scanner = scanner;
    }

    public void showIndex() {
        while (true) {
            int userChoice = 0;
            String message = new String("1. 대여점선택 2. 매니저로그인 3. 종료");
            if (staff != null) {
                message = new String("1. 대여점선택 2. 새 대여점 열기 3. 종료 4. 내 정보 보기 5. 영화추가 등등..");
                userChoice = ScannerUtil.nextInt(scanner, message, 1, 5);
            } else {
                userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            }
            StaffViewer staffViewer = new StaffViewer(staff, null, scanner);
            if (userChoice == 1) {
                searchStore();
            } else if (userChoice == 2) {
                if (staff == null) {
                    staff = staffViewer.logIn();
                } else {
                    openNewStore();
                }
            } else if (userChoice == 4) {
                staffViewer.staffInfo();
            } else if (userChoice == 3) {
                System.out.println("사용해주셔서 감사합니다.");
                break;
            } else if (userChoice==5) {
                FilmViewer filmViewer=new FilmViewer(scanner);
                filmViewer.filmMenu();
            }
        }
    }

    private void searchStore() {
        String message = new String("1. 대여점 전체리스트 출력 2. 위치별 매장 확인 3. 돌아가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);

        if (userChoice == 1) {
            StoreController storeController = new StoreController(new MySqlConnector());
            ArrayList<StoreDTO> list = storeController.selectAll();
            printAll(list);
        } else if (userChoice == 2) {
            AddressViewer addressViewer = new AddressViewer(scanner);
            int addressId = addressViewer.selectAddress();
            StoreController storeController = new StoreController(new MySqlConnector());
            ArrayList<StoreDTO> list = storeController.selectAllByAddressId(addressId);
            printAll(list);
        } else if (userChoice == 3) {
            System.out.println("대여점 선택 메뉴로 돌아갑니다.");
        }
    }

    private void printAll(ArrayList<StoreDTO> list) {
        if (list.isEmpty()) {
            System.out.println("검색 조건에 맞는 대여점의 정보가 없습니다.");
        } else {
            int pageMax = list.size() / 10;
            if (list.size() % 10 > 0) {
                pageMax = list.size() / 10 + 1;
            }
            int size=0;
            if(list.size()%10==0) {
                size=10;
            }
            int page = 1;
            while (true) {
                if (page == pageMax) {

                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                        StaffController staffController = new StaffController(new MySqlConnector());
                        System.out.printf("대여점 번호: %d - %s의 대여점\n", list.get(i).getStoreId(),
                                staffController.selectOne(list.get(i).getManagerStaffId()).getFirstName() + " "
                                        + staffController.selectOne(list.get(i).getManagerStaffId()).getLastName());
                    }
                } else {
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                        StaffController staffController = new StaffController(new MySqlConnector());
                        System.out.printf("대여점 번호: %d - %s의 대여점\n", list.get(i).getStoreId(),
                                staffController.selectOne(list.get(i).getManagerStaffId()).getFirstName() + " "
                                        + staffController.selectOne(list.get(i).getManagerStaffId()).getLastName());

                    }
                }
                System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n",
                        pageMax, page);
                String message = new String("해당하는 대여점 번호를 입력하시거나 뒤로 돌아가시려면 0을 입력해주세요.");
                Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                if (userChoice instanceof Integer) {
                    if ((Integer) userChoice == 0) {
                        break;
                    } else {
                        StoreController storeController = new StoreController(new MySqlConnector());
                        storeMain(storeController.selectOne((Integer) userChoice));
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
                    } else if ((Character) userChoice == 's') {
                        message = new String("원하는 페이지번호를 입력해주세요");
                        page = ScannerUtil.nextInt(scanner, message, 1, pageMax);
                    }
                }
            }
        }
    }

    public void storeMain(StoreDTO s) {
        StaffController staffcontroller = new StaffController(new MySqlConnector());
        if (s == null) {
            System.out.println("존재하지 않는 매장의 번호입니다.");
        } else {
            System.out.printf("%s %s 의 대여점에 오신걸 환영합니다.\n",
                    staffcontroller.selectOne(s.getManagerStaffId()).getFirstName(),
                    staffcontroller.selectOne(s.getManagerStaffId()).getLastName());
            while (true) {
                int userChoice = 0;
                String message = new String("1. 일반회원로그인 2. 회원등록 3. 뒤로가기");
                if (staff != null) {
                    if (s.getManagerStaffId() == staff.getStaffId()) {
                        message += " 4. 매장관리메뉴";
                        userChoice = ScannerUtil.nextInt(scanner, message, 1, 6);
                    } else {
                        userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
                    }
                } else {

                    userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
                }

                if (userChoice == 1) {
                    CustomerViewer customerViewer = new CustomerViewer(customer, s, scanner);
                    customer = customerViewer.logIn();
                    if (customer == null) {
                        System.out.println("회원정보가 존재하지 않습니다.");
                    } else {
                        customerViewer = new CustomerViewer(customer, s, scanner);
                        customerViewer.logInMenu();
                    }
                } else if (userChoice == 2) {
                    CustomerViewer customerViewer = new CustomerViewer(customer, s, scanner);
                    customerViewer.register();
                } else if (userChoice == 3) {
                    System.out.println("대여점 목록으로 돌아갑니다.");
                    break;
                } else if (userChoice == 4) {
                    managementStore(s);

                }
            }
        }
    }

    private void openNewStore() {
        StoreDTO store = new StoreDTO();
        store.setManagerStaffId(staff.getStaffId());

        AddressViewer addressViewer = new AddressViewer(scanner);
        System.out.println("매장을 오픈하실 위치의 주소를 선택해주세요.");
        store.setAddressId(addressViewer.selectAddress());

        StoreController storeController = new StoreController(new MySqlConnector());
        storeController.insert(store);

        System.out.println("가게 오픈이 완료되었습니다.");

    }

    private void managementStore(StoreDTO s) {
        while (true) {
            String message = new String("1. 일반회원 매니져로 승격시키기 2. 매장 매출 확인하기 3. 비디오 재고 관리하기 4. 돌아가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);
            if (userChoice == 1) {
                StaffViewer staffViewer = new StaffViewer(staff, s, scanner);
                staffViewer.levelUp();
            } else if (userChoice == 2) {
                PaymentViewer paymentViewer = new PaymentViewer(staff, null, scanner);
                paymentViewer.printList();
            } else if (userChoice == 3) {
                InventoryViewer inventoryViewer = new InventoryViewer(s, scanner);
                inventoryViewer.manageInventory();
            } else if (userChoice == 4) {
                System.out.println("대여점 메뉴로 돌아갑니다.\n");
                break;
            }
        }
    }

    public int selectId() {
        int storeId = 0;

        String message = new String("1. 대여점 전체리스트 출력 2. 위치별 매장 확인 3. 돌아가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);

        if (userChoice == 1) {
            StoreController storeController = new StoreController(new MySqlConnector());
            ArrayList<StoreDTO> list = storeController.selectAll();
            storeId = selectIdAll(list);
        } else if (userChoice == 2) {
            AddressViewer addressViewer = new AddressViewer(scanner);
            int addressId = addressViewer.selectAddress();
            StoreController storeController = new StoreController(new MySqlConnector());
            ArrayList<StoreDTO> list = storeController.selectAllByAddressId(addressId);
            storeId = selectIdAll(list);
        } else if (userChoice == 3) {

        }

        return storeId;
    }

    private int selectIdAll(ArrayList<StoreDTO> list) {
        if (list.isEmpty()) {
            System.out.println("검색 조건에 맞는 대여점의 정보가 없습니다.");
        } else {
            int pageMax = list.size() / 10;
            if (list.size() % 10 > 0) {
                pageMax = list.size() / 10 + 1;
            }
            int size=0;
            if(list.size()%10==0) {
                size=10;
            }
            int page = 1;
            while (true) {
                if (page == pageMax) {

                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                        StaffController staffController = new StaffController(new MySqlConnector());
                        System.out.printf("대여점 번호: %d - %s의 대여점\n", list.get(i).getStoreId(),
                                staffController.selectOne(list.get(i).getManagerStaffId()).getFirstName() + " "
                                        + staffController.selectOne(list.get(i).getManagerStaffId()).getLastName());
                    }
                } else {
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                        StaffController staffController = new StaffController(new MySqlConnector());
                        System.out.printf("대여점 번호: %d - %s의 대여점\n", list.get(i).getStoreId(),
                                staffController.selectOne(list.get(i).getManagerStaffId()).getFirstName() + " "
                                        + staffController.selectOne(list.get(i).getManagerStaffId()).getLastName());

                    }
                }
                System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n",
                        pageMax, page);
                String message = new String("해당하는 대여점 번호를 입력하시거나 뒤로 돌아가시려면 0을 입력해주세요.");
                Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                if (userChoice instanceof Integer) {
                    if ((Integer) userChoice == 0) {
                        break;
                    } else {
                        for (StoreDTO s : list) {
                            if (s.getStoreId() == (Integer) userChoice) {
                                return (Integer) userChoice;
                            }
                        }
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
                    } else if ((Character) userChoice == 's') {
                        message = new String("원하는 페이지번호를 입력해주세요");
                        page = ScannerUtil.nextInt(scanner, message, 1, pageMax);
                    }
                }
            }
        }
        return 0;
    }
}

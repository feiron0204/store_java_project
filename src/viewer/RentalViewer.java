package viewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.FilmController;
import controller.InventoryController;
import controller.RentalController;
import model.CustomerDTO;
import model.RentalDTO;
import model.StoreDTO;
import util.ScannerUtil;

public class RentalViewer {
    private CustomerDTO customer;
    private StoreDTO store;
    private Scanner scanner;

    public RentalViewer(CustomerDTO customer, StoreDTO store, Scanner scanner) {
        this.customer = customer;
        this.store = store;
        this.scanner = scanner;
    }

    public void printList() {
        SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");
        RentalController rentalController = new RentalController(new MySqlConnector());
        InventoryController inventoryController = new InventoryController(new MySqlConnector());
        FilmController filmController = new FilmController(new MySqlConnector());
        int page = 1;
        while (true) {
            ArrayList<RentalDTO> list = rentalController.selectAllByCustomerId(customer.getCustomerId(),
                    store.getStoreId());
            if (list.isEmpty()) {
                System.out.println("대여 기록이 없습니다.");
                break;
            } else {
                int pageMax = list.size() / 10;
                if (list.size() % 10 > 0) {
                    pageMax = list.size() / 10 + 1;
                }
                int size=0;
                if(list.size()%10==0) {
                    size=10;
                }
                System.out.printf("%s %s 님의 대여리스트\n", customer.getFirstName(), customer.getLastName());
                System.out.println("---------------------------------------------------");
                if (page == pageMax) {
                    String filmName=new String("비디오가 폐기되어 정보가 없습니다");
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                        if(inventoryController.selectOne(list.get(i).getInventoryId())!=null) {
                            filmName=filmController.selectOne(inventoryController.selectOne(list.get(i).getInventoryId()).getFilmId()).getTitle();
                        }
                        System.out.printf("대여기록번호: %d 영화제목: %s 대여일: %s\n", list.get(i).getRentalId(),
                                filmName,
                                sdf.format(list.get(i).getRentalDate().getTime()));
                    }
                } else {
                    String filmName=new String("비디오가 폐기되어 정보가 없습니다");
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                        if(inventoryController.selectOne(list.get(i).getInventoryId())!=null) {
                            filmName=filmController.selectOne(inventoryController.selectOne(list.get(i).getInventoryId()).getFilmId()).getTitle();
                        }
                        System.out.printf("대여기록번호: %d 영화제목: %s 대여일: %s\n", list.get(i).getRentalId(),
                                filmName,
                                sdf.format(list.get(i).getRentalDate().getTime()));
                    }
                }
                System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n",
                        pageMax, page);
                String message = new String("지불내역을 확인하시려면 대여기록번호를, 뒤로 돌아가시려면 0을 입력해주세요.");
                Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                if (userChoice instanceof Integer) {
                    if ((Integer) userChoice == 0) {
                        break;
                    } else {
                        for(RentalDTO r:list) {
                            if(r.getRentalId()==(Integer)userChoice) {
                                RentalDTO rental = rentalController.selectOne((Integer) userChoice);
                                PaymentViewer paymentViewer = new PaymentViewer(null, customer, scanner);
                                paymentViewer.printDetail(0, rental.getRentalId());
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
    }

    public void rentalMenu() {
        while (true) {
            String message = new String("1. 비디오 대여 2. 비디오 반납 3. 뒤로가기");
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                videoRental();
            } else if (userChoice == 2) {
                videoReturn();
            } else if (userChoice == 3) {
                System.out.println("이전화면으로 돌아갑니다.\n");
                break;
            }
        }
    }

    private void videoRental() {
        InventoryController inventoryController = new InventoryController(new MySqlConnector());
        RentalController rentalController = new RentalController(new MySqlConnector());
        FilmController filmController = new FilmController(new MySqlConnector());
        InventoryViewer inventoryViewer = new InventoryViewer(store, scanner);
        int inventoryId = inventoryViewer.selectInventoryId();
        if (inventoryId == 0) {
            System.out.println("대여/반납 메뉴로 돌아갑니다.\n");
        } else {
            RentalDTO rental = new RentalDTO();

            System.out.printf("선택하신 영화의 대여료는 %.2f$ 대여기간은 %d일 입니다.\n",
                    filmController.selectOne(inventoryController.selectOne(inventoryId).getFilmId()).getRentalRate(),
                    filmController.selectOne(inventoryController.selectOne(inventoryId).getFilmId())
                            .getRentalDuration());
            String message = new String("대여하시겠습니까?Y/N");
            String yesNo = ScannerUtil.nextLine(scanner, message);
            if (yesNo.equalsIgnoreCase("y")) {
                rental.setInventoryId(inventoryId);
                rental.setCustormerId(customer.getCustomerId());
                rental.setStaffId(store.getManagerStaffId());
                int rentalId = rentalController.insert(rental);

                PaymentViewer paymentViewer = new PaymentViewer(null, customer, scanner);
                // paymentViewer.rentalPay(rentalController.lastUpdateId(),store.getManagerStaffId(),filmController.selectOne(inventoryController.selectOne(inventoryId).getFilmId()).getRentalRate());
                paymentViewer.rentalPay(rentalId, store.getManagerStaffId(), filmController
                        .selectOne(inventoryController.selectOne(inventoryId).getFilmId()).getRentalRate());
                System.out.println("대여가 완료되었습니다.\n");
            } else {
                System.out.println("대여가 취소되었습니다.\n");
            }
        }

    }

    private void videoReturn() {
        SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");
        RentalController rentalController = new RentalController(new MySqlConnector());
        InventoryController inventoryController = new InventoryController(new MySqlConnector());
        FilmController filmController = new FilmController(new MySqlConnector());
        int page = 1;
        while (true) {

            ArrayList<RentalDTO> list = rentalController.returnByCustomerId(customer.getCustomerId(),
                    store.getStoreId());
            if (list.isEmpty()) {
                System.out.println("현재 대여점에 반납하실 비디오가 없습니다.\n");
                break;
            } else {
                int pageMax = list.size() / 10;
                if (list.size() % 10 > 0) {
                    pageMax = list.size() / 10 + 1;
                }
                int size=0;
                if(list.size()%10==0) {
                    size=10;
                }
                if (page == pageMax) {
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                        System.out.printf("대여기록번호: %d 영화제목: %s 대여일: %s\n", list.get(i).getRentalId(),
                                filmController
                                        .selectOne(
                                                inventoryController.selectOne(list.get(i).getInventoryId()).getFilmId())
                                        .getTitle(),
                                sdf.format(list.get(i).getRentalDate().getTime()));
                    }
                } else {
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                        System.out.printf("대여기록번호: %d 영화제목: %s 대여일: %s\n", list.get(i).getRentalId(),
                                filmController
                                        .selectOne(
                                                inventoryController.selectOne(list.get(i).getInventoryId()).getFilmId())
                                        .getTitle(),
                                sdf.format(list.get(i).getRentalDate().getTime()));
                    }
                }
                System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n",
                        pageMax, page);
                String message = new String("반납하실 대여기록번호를 입력하시거나 뒤로 돌아가시려면 0을 입력해주세요.");
                Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                if (userChoice instanceof Integer) {
                    if ((Integer) userChoice == 0) {
                        break;
                    } else {
                        for(RentalDTO r:list) {
                            if(r.getRentalId()==(Integer)userChoice) {
                                returnDetail((Integer) userChoice);
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
    }

    private void returnDetail(int rentalId) {
        RentalController rentalController = new RentalController(new MySqlConnector());

        // 대충 연체료 계산하는 메소드
        int latePay = 0;
        String message = new String("연체료는 " + latePay + "입니다. 반납하시겠습니까? Y/N");
        String yesNo = ScannerUtil.nextLine(scanner, message);
        if (yesNo.equalsIgnoreCase("y")) {
            RentalDTO rental = rentalController.selectOne(rentalId);
            rentalController.update(rental);
            PaymentViewer paymentViewer = new PaymentViewer(null, customer, scanner);
            paymentViewer.returnPay(rentalId, latePay);
            System.out.println("반납이 완료되었습니다.\n");

        } else {
            System.out.println("반납이 취소되었습니다.\n");
        }
    }
}

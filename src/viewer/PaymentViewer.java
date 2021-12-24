package viewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.CustomerController;
import controller.FilmController;
import controller.InventoryController;
import controller.PaymentController;
import controller.RentalController;
import model.CustomerDTO;
import model.PaymentDTO;
import model.StaffDTO;
import util.ScannerUtil;

public class PaymentViewer {
    private StaffDTO staff;
    private CustomerDTO customer;
    private Scanner scanner;

    public PaymentViewer(StaffDTO staff, CustomerDTO customer, Scanner scanner) {
        this.staff = staff;
        this.customer = customer;
        this.scanner = scanner;
    }

    public void printDetail(int paymentId, int rentalId) {
        while (true) {

            SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");
            PaymentDTO payment = null;
            FilmController filmController = new FilmController(new MySqlConnector());
            PaymentController paymentController = new PaymentController(new MySqlConnector());
            CustomerController customerController = new CustomerController(new MySqlConnector());
            InventoryController inventoryController = new InventoryController(new MySqlConnector());
            RentalController rentalController = new RentalController(new MySqlConnector());
            String message = "";
            int userChoice = 3;
            if (paymentId == 0) {
                payment = paymentController.selectAllByRentalId(rentalId);
            }
            if (rentalId == 0) {
                payment = paymentController.selectOne(paymentId);
            }

            if (payment == null) {
                System.out.println("해당 대여정보의 지불내역이 없습니다.\n");
                break;
            } else {
                System.out.println("지불번호: " + payment.getPaymentId());
                String customerName=new String("탈퇴하여 정보가 없습니다.");
                if(customerController.selectOne(payment.getCustomerId())!=null) {
                    customerName=customerController.selectOne(payment.getCustomerId()).getFirstName()+" "+customerController.selectOne(payment.getCustomerId()).getLastName();
                }
                System.out.println("지불회원이름: " + customerName);
                System.out.println("지불날짜: " + sdf.format(payment.getPaymentDate().getTime()));
                if(inventoryController.selectOne(rentalController.selectOne(payment.getRentalId()).getInventoryId())==null) {
                     System.out.println("영화정보가 삭제되어 대여료, 연체료를 확인할 수 없습니다.");
                }else {
                System.out.printf("대여료: %.2f$ 연체료: %.2f$\n",
                        filmController
                                .selectOne(inventoryController
                                        .selectOne(rentalController.selectOne(payment.getRentalId()).getInventoryId()).getFilmId())
                                .getRentalRate(),
                        payment.getAmount() - filmController
                                .selectOne(inventoryController
                                        .selectOne(rentalController.selectOne(payment.getRentalId()).getInventoryId()).getFilmId())
                                .getRentalRate());
                }
                System.out.printf("총 지불금액: %.2f$\n\n", payment.getAmount() );
                if (staff != null) {
                    message += "1. 지불내역 수정하기 2. 지불내역 삭제하기 3. 돌아가기";
                    userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
                } else {
                    message += "돌아가시려면 0을 입력해주세요.";
                    ScannerUtil.nextInt(scanner, message, 0, 0);
                }

                if (userChoice == 1) {
                    update(payment.getPaymentId());
                } else if (userChoice == 2) {
                    delete(payment.getPaymentId());
                } else if (userChoice == 3) {
                    System.out.println();
                    break;
                }

            }
        }
    }

    private void update(int paymentId) {
        String message = new String("매니저 비밀번호를 입력해주세요.");
        String password = ScannerUtil.nextLine(scanner, message);
        PaymentController paymentController = new PaymentController(new MySqlConnector());
        if (password.equals(staff.getPassword())) {
            PaymentDTO p = paymentController.selectOne(paymentId);
            message = new String("수정할 지불 금액을 입력해주세요.");
            double amount = ScannerUtil.nextDouble(scanner, message);

            message = new String("정말 수정하시겠습니까? Y/N");
            String yesNo = ScannerUtil.nextLine(scanner, message);
            if (yesNo.equalsIgnoreCase("y")) {
                p.setAmount(amount);
                paymentController.update(p);
                System.out.println("수정이 완료되었습니다.\n");

            } else {
                System.out.println("수정이 취소되었습니다.\n");
            }
        } else {
            System.out.println("비밀번호가 다릅니다.\n");
        }
    }

    private void delete(int paymentId) {
        String message = new String("매니저 비밀번호를 입력해주세요.");
        String password = ScannerUtil.nextLine(scanner, message);
        PaymentController paymentController = new PaymentController(new MySqlConnector());
        if (password.equals(staff.getPassword())) {
            PaymentDTO p = paymentController.selectOne(paymentId);
            message = new String("정말 삭제하시겠습니까? Y/N");
            String yesNo = ScannerUtil.nextLine(scanner, message);
            if (yesNo.equalsIgnoreCase("y")) {

                paymentController.delete(p);
                System.out.println("삭제가 완료되었습니다.\n");

            } else {
                System.out.println("삭제가 취소되었습니다.\n");
            }
        } else {
            System.out.println("비밀번호가 다릅니다.\n");
        }
    }

    public void printList() {
        SimpleDateFormat sdf=new SimpleDateFormat("y-M-d");
        PaymentController paymentController = new PaymentController(new MySqlConnector());
        FilmController filmController = new FilmController(new MySqlConnector());
        InventoryController inventoryController = new InventoryController(new MySqlConnector());
        RentalController rentalController = new RentalController(new MySqlConnector());
        CustomerController customerController = new CustomerController(new MySqlConnector());
        if (staff != null) {
            int page = 1;
            while (true) {
                System.out.println("현재까지 총 수익: " + paymentController.sumByStoreId(staff.getStoreId())+"$");
                ArrayList<PaymentDTO> list = paymentController.selectAllByStoreId(staff.getStoreId());
                if (list.isEmpty()) {
                    System.out.println("검색 조건에 맞는 지불 정보가 없습니다.");
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
                            String customerName = new String("탈퇴하여 정보가 없습니다.");
                            if(customerController.selectOne(list.get(i).getCustomerId())!=null) {
                                customerName = customerController.selectOne(list.get(i).getCustomerId()).getFirstName()+" "+customerController.selectOne(list.get(i).getCustomerId()).getLastName();
                            }
                            System.out.printf("지불번호: %d 지불한 회원 이름: %s \t지불금액: %.2f$ 지불날짜: %s\n", 
                                    list.get(i).getPaymentId(),
                                    customerName,
                                    list.get(i).getAmount(),
                                    sdf.format(list.get(i).getPaymentDate().getTime()));
                        }
                    } else {
                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                            String customerName = new String("탈퇴하여 정보가 없습니다.");

                            if(customerController.selectOne(list.get(i).getCustomerId())!=null) {
                                customerName = customerController.selectOne(list.get(i).getCustomerId()).getFirstName()+" "+customerController.selectOne(list.get(i).getCustomerId()).getLastName();
                            }
                            System.out.printf("지불번호: %d 지불한 회원 이름: %s \t지불금액: %.2f$ 지불날짜: %s\n",
                                    list.get(i).getPaymentId(),
                                    customerName,
                                    list.get(i).getAmount(),
                                    sdf.format(list.get(i).getPaymentDate().getTime()));
                        }
                    }
                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n", pageMax, page);
                    String message = new String("해당하는 지불 번호를 입력하시거나 뒤로 돌아가시려면 0을 입력해주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if (userChoice instanceof Integer) {
                        if ((Integer) userChoice == 0) {
                            break;
                        } else {
                            for(PaymentDTO p:list) {
                                if(p.getPaymentId()==(Integer)userChoice) {
                                    printDetail((Integer)userChoice,0);
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
                        }else if((Character) userChoice=='s') {
                            message = new String("원하는 페이지번호를 입력해주세요");
                            page=ScannerUtil.nextInt(scanner, message,1,pageMax);
                        }
                    }
                }
            }
        } else {
            int page = 1;
            while (true) {
                System.out.printf("현재까지 총 지불금액: %.2f$\n" ,paymentController.sumByCustomerId(customer.getCustomerId()));
                ArrayList<PaymentDTO> list = paymentController.selectAllByCustomerId(customer.getCustomerId());
                if (list.isEmpty()) {
                    System.out.println("검색 조건에 맞는 지불 정보가 없습니다.");
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
                        String filmName=new String("폐기된 비디오입니다.");
                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                            if(inventoryController.selectOne(rentalController.selectOne(list.get(i).getRentalId()).getInventoryId())!=null) {
                                filmName=filmController.selectOne(inventoryController.selectOne(rentalController.selectOne(list.get(i).getRentalId()).getInventoryId()).getFilmId()).getTitle();
                            }
                            System.out.printf("지불번호: %d 대여한 영화 제목: %s 지불금액: %.2f$ 지불날짜: %s\n",
                                    list.get(i).getPaymentId(),
                                    filmName,
                                    list.get(i).getAmount(),
                                    sdf.format(list.get(i).getPaymentDate().getTime()));
                        }
                    } else {
                        String filmName=new String("폐기된 비디오입니다.");
                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                            if(inventoryController.selectOne(rentalController.selectOne(list.get(i).getRentalId()).getInventoryId())!=null) {
                                filmName=filmController.selectOne(inventoryController.selectOne(rentalController.selectOne(list.get(i).getRentalId()).getInventoryId()).getFilmId()).getTitle();
                            }
                            System.out.printf("지불번호: %d 대여한 영화 제목: %s 지불날짜: %s\n",
                                    list.get(i).getPaymentId(),
                                    filmName,
                                    list.get(i).getAmount(),
                                    sdf.format(list.get(i).getPaymentDate().getTime()));
                        }
                    }
                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n", page, pageMax);
                    String message = new String("해당하는 지불 번호를 입력하시거나 뒤로 돌아가시려면 0을 입력해주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if (userChoice instanceof Integer) {
                        if ((Integer) userChoice == 0) {
                            break;
                        } else {
                            for(PaymentDTO p:list) {
                                if(p.getPaymentId()==(Integer)userChoice) {
                                    printDetail((Integer)userChoice,0);
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
                        }else if((Character) userChoice=='s') {
                            message = new String("원하는 페이지번호를 입력해주세요");
                            page=ScannerUtil.nextInt(scanner, message,1,pageMax);
                        }
                    }
                }
            }
        }
    }
    public void returnPay(int rentalId,int latePay) {
        PaymentController paymentController=new PaymentController(new MySqlConnector());
        PaymentDTO payment=paymentController.selectAllByRentalId(rentalId);
        payment.setAmount(payment.getAmount()+latePay);
        paymentController.update(payment);
    }
    public void rentalPay(int rentalId,int managerStaffId,double pay) {
        PaymentController paymentController=new PaymentController(new MySqlConnector());
        PaymentDTO payment =new PaymentDTO();
        payment.setAmount(pay);
        payment.setCustomerId(customer.getCustomerId());
        payment.setStaffId(managerStaffId);
        payment.setRentalId(rentalId);
        paymentController.insert(payment);
    }
}

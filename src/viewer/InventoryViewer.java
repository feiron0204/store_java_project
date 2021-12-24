package viewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.FilmController;
import controller.InventoryController;
import controller.StaffController;
import controller.StoreController;
import model.InventoryDTO;
import model.StoreDTO;
import util.ScannerUtil;

public class InventoryViewer {
    private StoreDTO store;
    private Scanner scanner;

    public InventoryViewer(StoreDTO store, Scanner scanner) {
        this.scanner = scanner;
        this.store = store;
    }

    public int selectInventoryId() {
        int inventoryId = 0;
        while (true) {
            String message = new String("1. 비디오 전체목록 확인 2. 영화별 비디오 목록 확인 3. 돌아가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            if (userChoice == 1) {
                inventoryId = selectAllInventoryId();
                if(inventoryId!=0) {
                    break;
                }
            } else if (userChoice == 2) {
                inventoryId = selectSearchInventoryId();
                if(inventoryId!=0) {
                    break;
                }
            } else if(userChoice==3) {
                break;
            }
        }
        return inventoryId;
    }

    private int selectSearchInventoryId() {
        InventoryDTO inventory = new InventoryDTO();
        boolean inventorySwitch = true;
        FilmController filmController = new FilmController(new MySqlConnector());
        FilmViewer filmViewer = new FilmViewer(scanner);
        int filmId = filmViewer.selectFilmId();
        while (inventory.getInventoryId() == 0&&filmId!=0) {
            
            InventoryController inventoryController = new InventoryController(new MySqlConnector());
            if (inventory.getInventoryId() == 0) {
                inventorySwitch = true;
            }
            int page = 1;
            while (inventorySwitch) {
                ArrayList<InventoryDTO> list = inventoryController.selectAllByFilmId2(filmId, store.getStoreId());
                
                int pageMax = list.size() / 10;
                if (list.size() % 10 > 0) {
                    pageMax = list.size() / 10 + 1;
                }
                int size=0;
                if(list.size()%10==0) {
                    size=10;
                }
                if (list.isEmpty()) {
                    System.out.println("해당 조건을 만족하는 비디오가 없습니다.");
                    filmId=0;
                    inventorySwitch = false;
                } else {
                    if (page == pageMax) {
                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                            System.out.printf("비디오번호: %d 비디오제목: %s\n", list.get(i).getInventoryId(),
                                    filmController.selectOne(list.get(i).getFilmId()).getTitle());
                        }
                    } else {
                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                            System.out.printf("비디오번호: %d 비디오제목: %s\n", list.get(i).getInventoryId(),
                                    filmController.selectOne(list.get(i).getFilmId()).getTitle());
                        }
                    }

                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n", pageMax, page);

                    String message = new String("비디오 번호를 선택하시거나 \n다른 영화를 검색하시려면 0 을 눌러주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if (userChoice instanceof Integer) {

                        for (InventoryDTO i : list) {
                            if (i.getInventoryId() == (Integer) userChoice || (Integer) userChoice == 0) {
                                if ((Integer) userChoice != 0) {
                                    inventory = inventoryController.selectOne((Integer) userChoice);
                                }
                                inventorySwitch = false;
                                if((Integer) userChoice==0) {
                                    filmId=0;
                                    
                                }
                            }
                        }
                        if (inventorySwitch) {
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
        return inventory.getInventoryId();
    }

    private int selectAllInventoryId() {
        InventoryDTO inventory = new InventoryDTO();
        boolean inventorySwitch = true;
        FilmController filmController = new FilmController(new MySqlConnector());

        InventoryController inventoryController = new InventoryController(new MySqlConnector());
        if (inventory.getInventoryId() == 0) {
            inventorySwitch = true;
        }
        int page = 1;
        while (inventorySwitch) {

            ArrayList<InventoryDTO> list = inventoryController.selectAllByStoreId(store.getStoreId());
            int pageMax = list.size() / 10;
            if (list.size() % 10 > 0) {
                pageMax = list.size() / 10 + 1;
            }
            int size=0;
            if(list.size()%10==0) {
                size=10;
            }
            if (list.isEmpty()) {
                System.out.println("해당 조건을 만족하는 비디오가 없습니다.");
                inventorySwitch = false;
            } else {
                if (page == pageMax) {
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                        System.out.printf("비디오번호: %d 비디오제목: %s\n", list.get(i).getInventoryId(),
                                filmController.selectOne(list.get(i).getFilmId()).getTitle());
                    }
                } else {
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                        System.out.printf("비디오번호: %d 비디오제목: %s\n", list.get(i).getInventoryId(),
                                filmController.selectOne(list.get(i).getFilmId()).getTitle());
                    }
                }

                System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n", pageMax, page);

                String message = new String("비디오 번호를 선택하시거나 \n비디오 검색 메뉴로 돌아가시려면 0 을 눌러주세요.");
                Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                if (userChoice instanceof Integer) {

                    for (InventoryDTO i : list) {
                        if (i.getInventoryId() == (Integer) userChoice || (Integer) userChoice == 0) {
                            if ((Integer) userChoice != 0) {
                                inventory = inventoryController.selectOne((Integer) userChoice);
                            }
                            inventorySwitch = false;
                        }
                    }
                    if (inventorySwitch) {
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

        return inventory.getInventoryId();
    }
    
    public void manageInventory() {
        while(true) {
            String message = new String("1. 보유한 비디오 목록 출력하기 2. 비디오 추가하기 3. 돌아가기");
            int userChoice=ScannerUtil.nextInt(scanner, message,1,3);
            if(userChoice==1) {
                int inventory=selectInventoryId();
                if(inventory!=0) {
                    inventoryDetail(inventory);
                }
            }else if(userChoice==2) {
                insertInventory();
            }else if(userChoice==3) {
                System.out.println("대여점 관리메뉴로 돌아갑니다.\n");
                break;
            }
        }
    }
    
    private void insertInventory() {

        InventoryController inventoryController = new InventoryController(new MySqlConnector());
        InventoryDTO inventory=new InventoryDTO();
        FilmViewer filmViewer = new FilmViewer(scanner);
        int filmId=filmViewer.selectFilmId();
        String message = new String("해당영화의 비디오를 몇개 추가하시겠습니까?\n 취소하시려면 0 혹은 음수를 입력해주세요.");
        int userChoice = ScannerUtil.nextInt(scanner, message);
        inventory.setFilmId(filmId);
        inventory.setStoreId(store.getStoreId());
        if(userChoice<=0) {
            System.out.println("비디오 추가가 취소되었습니다.\n");
        }else {
            for(int i=0;i<userChoice;i++) {
                inventoryController.insert(inventory);
            }
            System.out.println("비디오 "+userChoice+"개가 추가되었습니다.\n");
        }
    }
    
    private void inventoryDetail(int inventoryId) {
        SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");
        InventoryController inventoryController = new InventoryController(new MySqlConnector());
        FilmController filmController = new FilmController(new MySqlConnector());
        StoreController storeController = new StoreController(new MySqlConnector());
        StaffController staffController = new StaffController(new MySqlConnector());
        InventoryDTO i=inventoryController.selectOne(inventoryId);
        System.out.println("비디오 번호: "+i.getInventoryId());
        System.out.println("비디오 보유 매장이름: "+staffController.selectOne(storeController.selectOne(i.getStoreId()).getManagerStaffId()).getFirstName()+" "+staffController.selectOne(storeController.selectOne(i.getStoreId()).getManagerStaffId()).getLastName()+"의 가게");
        System.out.println("비디오 영화 제목: "+filmController.selectOne(i.getFilmId()).getTitle());
        System.out.println("비디오 구매 날짜: "+sdf.format(i.getLastUpdate().getTime()));
        
        String message = new String("1. 다른 매장에 전달 2. 비디오 폐기 3. 돌아가기");
        int userChoice = ScannerUtil.nextInt(scanner, message,1,3);
        if(userChoice==1) {
            update(inventoryId);
        }else if(userChoice==2) {
            delete(inventoryId);
        }else if(userChoice==3) {
            
        }
        
    }
    
    private void update(int inventoryId) {
        StaffController staffController=new StaffController(new MySqlConnector());
        InventoryController inventoryContoller = new InventoryController(new MySqlConnector());
        StoreController storeController = new StoreController(new MySqlConnector());
        String message = new String("담당 매니져의 비밀번호를 입력해주세요.");
        String password=ScannerUtil.nextLine(scanner, message);
        if(password.equals(staffController.selectOne(store.getManagerStaffId()).getPassword())){
            StoreViewer storeViewer = new StoreViewer(null,staffController.selectOne(store.getManagerStaffId()),scanner); 
            int storeId=storeViewer.selectId();
            if(storeId==0) {
                System.out.println("매장을 선택하지 않아 전달이 취소되었습니다.\n");
            }else {
                System.out.printf("%d 번 비디오를 %s %s의 ",inventoryId,staffController.selectOne(storeController.selectOne(storeId).getManagerStaffId()).getFirstName(),staffController.selectOne(storeController.selectOne(storeId).getManagerStaffId()).getLastName());
                message = new String("매장으로 전달하시겠습니까? Y/N");
                String yesNo=ScannerUtil.nextLine(scanner, message);
                if(yesNo.equalsIgnoreCase("y")) {
                    InventoryDTO i = new InventoryDTO();
                    i.setStoreId(storeId);
                    i.setInventoryId(inventoryId);
                    inventoryContoller.update(i);
                    System.out.println("전달이 완료되었습니다.\n");
                }else {
                    System.out.println("전달이 취소되었습니다.\n"); 
                }
            }
        }else {
            System.out.println("비밀번호가 달라 전달이 취소되었습니다.\n");
        }
    }
    
    private void delete(int inventoryId) {
        boolean inventorySwitch=false;
        StaffController staffController = new StaffController(new MySqlConnector());
        InventoryController inventoryController = new InventoryController(new MySqlConnector());
        ArrayList<InventoryDTO> list=inventoryController.selectAllByFilmId(inventoryController.selectOne(inventoryId).getFilmId(), store.getStoreId());
        for(InventoryDTO i:list) {
            if(i.getInventoryId()==inventoryId) {
                inventorySwitch=true;
            }
        }
        if(inventorySwitch) {
        String message = new String("담당 매니져의 비밀번호를 입력해주세요.");
        String password=ScannerUtil.nextLine(scanner, message);
        if(password.equals(staffController.selectOne(store.getManagerStaffId()).getPassword())){
            message = new String("정말로 삭제하시겠습니까? Y/N");
            String yesNo=ScannerUtil.nextLine(scanner, message);
            if(yesNo.equalsIgnoreCase("y")) {
                InventoryDTO i = new InventoryDTO();
                i.setInventoryId(inventoryId);
                inventoryController.delete(i);
                System.out.println("폐기가 완료되었습니다.\n");
            }else {
                System.out.println("폐기가 취소되었습니다.\n"); 
            }
            
        }else {
            System.out.println("비밀번호가 달라 폐기가 취소되었습니다.\n");
        }
        }else {
            System.out.println("현재 대여중인 비디오는 폐기할 수 없습니다.\n");
        }
    }
}

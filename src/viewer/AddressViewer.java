package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.AddressController;
import model.AddressDTO;
import util.ScannerUtil;

public class AddressViewer {
    private Scanner scanner;

    public AddressViewer(Scanner scanner) {
        this.scanner = scanner;
    }

    public int selectAddress() {
        AddressDTO address = new AddressDTO();
        boolean addressSwitch = true;
        CityViewer cityViewer = new CityViewer(scanner);
        int cityId = cityViewer.selectCity();
        while (address.getAddress() == null) {
            AddressController addressController = new AddressController(new MySqlConnector());
            if(address.getAddressId()==0) {
                addressSwitch = true;
            }
            String message = new String("검색할 주소를 입력해주세요.");
            String addressPart = ScannerUtil.nextLine(scanner, message);
            int page =1;
            while (addressSwitch) {
                ArrayList<AddressDTO> list = addressController.selectAll(addressPart, cityId);
                int pageMax=list.size()/10;
                if(list.size()%10>0) {
                    pageMax=list.size()/10+1;
                }
                int size=0;
                if(list.size()%10==0) {
                    size=10;
                }
                if (list.isEmpty()) {
                    System.out.println("해당 조건을 만족하는 주소가 없습니다.");
                    message=new String("해당 도시에 주소를 추가하시겠습니까? Y/N");
                    String yesNo=ScannerUtil.nextLine(scanner, message);
                    if(yesNo.equalsIgnoreCase("y")) {
                        insertAddress(cityId);
                    }
                    addressSwitch=false;
                } else {
                    if(page==pageMax) {
                        for (int i = (page - 1)*10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                            System.out.printf("주소번호: %d 주소명: %s\n", list.get(i).getAddressId(), list.get(i).getAddress());
                        }
                    }else {
                        for (int i = (page - 1)*10; i < (page - 1) * 10+10; i++) {
                            System.out.printf("주소번호: %d 주소명: %s\n", list.get(i).getAddressId(), list.get(i).getAddress());
                        }
                    }
                    
                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n", pageMax, page);

                    message = new String("주소를 선택하시거나 다시 검색하시려면 0 을 눌러주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if(userChoice instanceof Integer) {
                        
                        for (AddressDTO a : list) {
                            if (a.getAddressId() == (Integer)userChoice||(Integer)userChoice==0) {
                                if((Integer)userChoice!=0) {
                                    
                                    address = addressController.selectOne((Integer)userChoice);
                                }
                                addressSwitch = false;
                            }
                        }
                        if (addressSwitch) {
                            System.out.println("리스트에 없는 번호입니다.\n");
                        }
                    }else {
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
        return address.getAddressId();
    }
    
    private void insertAddress(int cityId) {
        AddressController addressController = new AddressController(new MySqlConnector());
        AddressDTO a=new AddressDTO();
        int c = cityId;
        String message = new String("추가하실 주소를 입력해주세요.");
        String address = ScannerUtil.nextLine(scanner, message);
        message = new String("상세주소를 입력해주세요.");
        String address2=ScannerUtil.nextLine(scanner, message);
        message = new String("구역을 입력해주세요.");
        String district=ScannerUtil.nextLine(scanner, message);
        message = new String("우편번호를 입력해주세요. 추후 회원 탈퇴시 이용되니 정확히 입력해주세요.");
        String postalCode=ScannerUtil.nextLine(scanner, message);
        message = new String("전화번호를 입력해주세요.");
        String phone=ScannerUtil.nextLine(scanner, message);
        System.out.println("새로운 주소: "+address+" 우편번호: "+postalCode+" 전화번호: "+phone);
        
        message = new String("해당 정보를 추가하는데 동의하십니까? Y/N");
        String yesNo=ScannerUtil.nextLine(scanner, message);
        if(yesNo.equalsIgnoreCase("y")) {
            a.setAddress(address);
            a.setAddress2(address2);
            a.setCityId(c);
            a.setDistrict(district);
            a.setPostalCode(postalCode);
            a.setPhone(phone);
            
            addressController.insert(a);
            System.out.println("주소 추가가 완료되었습니다. 다시 검색해주세요.\n");
            
        }else {
            System.out.println("주소 추가가 취소되었습니다. 다시 검색해주세요.\n");
        }
    }
}

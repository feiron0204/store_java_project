package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.CityController;
import model.CityDTO;
import util.ScannerUtil;

public class CityViewer {
    private Scanner scanner;
    public CityViewer(Scanner scanner) {
        this.scanner=scanner;
    }
    public int selectCity() {
        CityDTO city = new CityDTO();
        boolean citySwitch = true;
        CountryViewer countryViewer = new CountryViewer(scanner);
        int countryId = countryViewer.selectCountry();
        while (city.getCity() == null) {
            CityController cityController = new CityController(new MySqlConnector());
            if(city.getCityId()==0) {
                citySwitch = true;
            }
            String message = new String("검색하실 도시이름를 입력해주세요.");
            String cityPart = ScannerUtil.nextLine(scanner, message);
            int page =1;
            while (citySwitch) {
                ArrayList<CityDTO> list = cityController.selectAll(cityPart, countryId);
                
                int pageMax=list.size()/10;
                if(list.size()%10>0) {
                    pageMax=list.size()/10+1;
                }
                int size=0;
                if(list.size()%10==0) {
                    size=10;
                }
                if (list.isEmpty()) {
                    System.out.println("해당 조건을 만족하는 도시가 없습니다.");
                    citySwitch=false;
                } else {
                    if(page==pageMax) {
                        for (int i = (page - 1)*10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                            System.out.printf("도시번호: %d 도시명: %s\n", list.get(i).getCityId(), list.get(i).getCity());
                        }
                    }else {
                        for (int i = (page - 1)*10; i < (page - 1) * 10+10; i++) {
                            System.out.printf("도시번호: %d 도시명: %s\n", list.get(i).getCityId(), list.get(i).getCity());
                        }
                    }
                    
                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n", pageMax, page);

                    message = new String("도시를 선택하시거나 다시 검색하시려면 0 을 눌러주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if(userChoice instanceof Integer) {
                        
                        for (CityDTO c : list) {
                            if (c.getCityId() == (Integer)userChoice||(Integer)userChoice==0) {
                                if((Integer)userChoice!=0) {
                                    
                                    city = cityController.selectOne((Integer)userChoice);
                                }
                                citySwitch = false;
                            }
                        }
                        if (citySwitch) {
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
        return city.getCityId();
    }
}

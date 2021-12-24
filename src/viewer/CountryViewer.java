package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.CountryController;
import model.CountryDTO;
import util.ScannerUtil;

public class CountryViewer {
    private Scanner scanner;
    public CountryViewer(Scanner scanner) {
        this.scanner=scanner;
    }
    public int selectCountry() {
        CountryDTO country = new CountryDTO();
        boolean countrySwitch=true;
        while (country.getCountry() == null) {
            CountryController countryController = new CountryController(new MySqlConnector());
            if(country.getCountryId()==0) {
                countrySwitch = true;
            }
            String message = new String("검색하실 나라이름를 입력해주세요.");
            String countryPart = ScannerUtil.nextLine(scanner, message);
            int page=1;
            while (countrySwitch) {
                ArrayList<CountryDTO> list = countryController.selectAll(countryPart);
                int pageMax=list.size()/10;
                if(list.size()%10>0) {
                    pageMax=list.size()/10+1;
                }
                int size=0;
                if(list.size()%10==0) {
                    size=10;
                }
                if (list.isEmpty()) {
                    System.out.println("해당 조건을 만족하는 나라이 없습니다.");
                    countrySwitch=false;
                } else {
                    if(page==pageMax) {
                        for (int i = (page - 1)*10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                            System.out.printf("나라번호: %d 지역명: %s\n", list.get(i).getCountryId(), list.get(i).getCountry());
                        }
                    }else {
                        for (int i = (page - 1)*10; i < (page - 1) * 10+10; i++) {
                            System.out.printf("나라번호: %d 지역명: %s\n", list.get(i).getCountryId(), list.get(i).getCountry());
                        }
                    }
                    
                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n", pageMax, page);

                    message = new String("나라를 선택하시거나 다시 검색하시려면 0 을 눌러주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if(userChoice instanceof Integer) {
                        
                        for (CountryDTO c : list) {
                            if (c.getCountryId() == (Integer)userChoice||(Integer)userChoice==0) {
                                if((Integer)userChoice!=0) {
                                    
                                    country = countryController.selectOne((Integer)userChoice);
                                }
                                countrySwitch = false;
                            }
                        }
                        if (countrySwitch) {
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
        return country.getCountryId();
    }
}

package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.LanguageController;
import model.LanguageDTO;
import util.ScannerUtil;

public class LanguageViewer {

    private Scanner scanner;
    public LanguageViewer(Scanner scanner) {
        this.scanner=scanner;
    }
    public int selectId() {
        
        LanguageController languageController = new LanguageController(new MySqlConnector());
        ArrayList<LanguageDTO> list = languageController.selectAll();
        for(LanguageDTO l : list) {
            System.out.printf("언어번호: %d 언어명: %s\n",l.getLanguageId(),l.getName());
        }
        String message = new String("원하는 언어의 번호를 입력하시거나 뒤로 가시려면 0을 입력해주세요.");
        int userChoice=ScannerUtil.nextInt(scanner, message);
        while(userChoice!=0&&languageController.selectOne(userChoice)==null) {
            message = new String("존재하지않는 언어의 번호입니다.\n 다시입력해주세요. 뒤로 가시려면 0을 입력해주세요.");
            userChoice=ScannerUtil.nextInt(scanner, message);
        }
        
        
        return userChoice;
    }
    
    public void insertLanguage() {
        LanguageController languageController = new LanguageController(new MySqlConnector());
        LanguageDTO l=new LanguageDTO();
        String message = new String("새로운 언어의 이름을 적어주세요.");
        String name=ScannerUtil.nextLine(scanner, message);
        message = new String("정말 추가하시겠습니까? Y/N");
        String yesNo=ScannerUtil.nextLine(scanner, message);
        if(yesNo.equalsIgnoreCase("y")) {
            System.out.println(name+"을 추가합니다.");
            l.setName(name);
            languageController.insert(l);
        }else {
            System.out.println("언어정보의 추가를 취소합니다.\n");
        }
        
    }
}

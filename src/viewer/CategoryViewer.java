package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.CategoryController;
import model.CategoryDTO;
import util.ScannerUtil;

public class CategoryViewer {
    private Scanner scanner;
    public CategoryViewer(Scanner scanner) {
        this.scanner=scanner;
    }
    public int selectId() {
        
        CategoryController categoryController = new CategoryController(new MySqlConnector());
        ArrayList<CategoryDTO> list = categoryController.selectAll();
        for(CategoryDTO l : list) {
            System.out.printf("장르번호: %d 장르명: %s\n",l.getCategoryId(),l.getName());
        }
        String message = new String("원하는 장르의 번호를 입력하시거나 뒤로 가시려면 0을 입력해주세요.");
        int userChoice=ScannerUtil.nextInt(scanner, message);
        while(userChoice!=0&&categoryController.selectOne(userChoice)==null) {
            message = new String("존재하지않는 언어의 번호입니다.\n다시 입력해주세요 뒤로 가시려면 0을 입력해주세요.");
            userChoice=ScannerUtil.nextInt(scanner, message);
        }
        
        
        return userChoice;
    }
}

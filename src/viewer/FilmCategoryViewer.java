package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.CategoryController;
import controller.FilmCategoryController;
import controller.FilmController;
import model.FilmCategoryDTO;
import util.ScannerUtil;

public class FilmCategoryViewer {
    private Scanner scanner;
    public FilmCategoryViewer(Scanner scanner) {
        this.scanner=scanner;
    }

    
    public int printAllByCategory() {
        FilmController filmController = new FilmController(new MySqlConnector());
        FilmCategoryController filmCategoryController = new FilmCategoryController(new MySqlConnector());
        int filmId = 0;
        while (filmId == 0) {
            CategoryViewer categoryViewer = new CategoryViewer(scanner);
            int categoryId = categoryViewer.selectId();
            if (categoryId==0) {
                break;
            }
            ArrayList<FilmCategoryDTO> list = filmCategoryController.selectAllByCategoryId(categoryId);
            if (list.isEmpty()) {
                System.out.println("영화정보가 없습니다.\n");
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
                while (filmId==0) {
                    if (page == pageMax) {

                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                            System.out.printf("영화번호: %d 영화제목: %s\n", list.get(i).getFilmId(), filmController.selectOne(list.get(i).getFilmId()).getTitle());
                        }
                    } else {
                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                            System.out.printf("영화번호: %d 영화제목: %s\n", list.get(i).getFilmId(), filmController.selectOne(list.get(i).getFilmId()).getTitle());

                        }
                    }
                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n", pageMax, page);
                    String message = new String("상세히 보실 영화의 번호를 입력하시거나 뒤로 돌아가시려면 0을 입력해주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if (userChoice instanceof Integer) {
                        if ((Integer) userChoice == 0) {
                            break;
                        } else {
                            FilmViewer filmViewer = new FilmViewer(scanner);
                            for (FilmCategoryDTO f : list) {
                                if (f.getFilmId() == (Integer) userChoice) {
                                    filmId = filmViewer.printDetail((Integer) userChoice);
                                }
                            }
                            if (filmId == 0) {
                                System.out.println("리스트에 존재하지 않는 영화번호입니다.");
                            }
                            
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
        return filmId;
    }
    
    public void insert(int filmId) {
        FilmCategoryController filmCategoryController = new FilmCategoryController(new MySqlConnector());
        FilmController filmController = new FilmController(new MySqlConnector());
        CategoryController categoryController = new CategoryController(new MySqlConnector());
        CategoryViewer categoryViewer = new CategoryViewer(scanner);
        System.out.println("영화의 카테고리정보를 등록합니다.");
        int categoryId = categoryViewer.selectId();
        while(categoryController.selectOne(categoryId)==null) {
            System.out.println("카테고리를 반드시 선택해야합니다.");
            categoryId = categoryViewer.selectId();
        }
        FilmCategoryDTO f=new FilmCategoryDTO();
        System.out.println("영화 "+filmController.selectOne(filmId).getTitle()+" 를 "+categoryController.selectOne(categoryId).getName()+" 카테고리로 등록합니다.");
        f.setCategoryId(categoryId);
        f.setFilmId(filmId);
        filmCategoryController.insert(f);
        
    }
}

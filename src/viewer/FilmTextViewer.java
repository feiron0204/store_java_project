package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.FilmController;
import controller.FilmTextController;
import model.FilmTextDTO;
import util.ScannerUtil;

public class FilmTextViewer {
    private Scanner scanner;
    public FilmTextViewer(Scanner scanner) {
        this.scanner=scanner;
    }
    public int selectFilmId() {
        FilmTextController filmTextController = new FilmTextController(new MySqlConnector());
        int filmId=0;
        ArrayList<FilmTextDTO> list = filmTextController.selectAll();
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
                       System.out.printf("영화번호: %d 영화제목: %s\n", list.get(i).getFilmId(),list.get(i).getTitle());
                    }
                } else {
                    for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                        System.out.printf("영화번호: %d 영화제목: %s\n", list.get(i).getFilmId(),list.get(i).getTitle());  

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
                        for (FilmTextDTO f : list) {
                            if (f.getFilmId() == (Integer) userChoice) {
                                filmId=filmViewer.printDetail((Integer)userChoice);
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
        return filmId;
    }
    
    public void insert(int filmId) {
        FilmController filmController = new FilmController(new MySqlConnector());
        FilmTextController filmTextController = new FilmTextController(new MySqlConnector());
        FilmTextDTO f=new FilmTextDTO();
        f.setDescription(filmController.selectOne(filmId).getDescription());
        f.setFilmId(filmId);
        f.setTitle(filmController.selectOne(filmId).getTitle());
        filmTextController.insert(f);
        
    }
}

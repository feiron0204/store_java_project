package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.FilmController;
import controller.LanguageController;
import model.FilmDTO;
import util.ScannerUtil;

public class FilmViewer {
    private Scanner scanner;

    public FilmViewer(Scanner scanner) {
        this.scanner = scanner;
    }

    public int selectFilmId() {
        int filmId = 0;
        while (filmId == 0) {
            String message = new String("1. 전체영화목록보기 2. 검색 후 목록보기 3. 돌아가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            if (userChoice == 1) {
                FilmTextViewer filmTextViewer = new FilmTextViewer(scanner);
                filmId = filmTextViewer.selectFilmId();
            } else if (userChoice == 2) {
                filmId = searchMenu();
            } else if (userChoice == 3) {
                break;
            }

        }

        return filmId;
    }

    public int printDetail(int filmId) {
        FilmController filmController = new FilmController(new MySqlConnector());
        LanguageController languageController = new LanguageController(new MySqlConnector());
        FilmDTO film = filmController.selectOne(filmId);
        System.out.println("영화번호: " + film.getFilmId());
        System.out.println("영화제목: " + film.getTitle());
        System.out.println("영화제작년도: " + film.getReleaseYear() + "년");
        System.out.printf("언어: %s 영상길이: %d 분 영상등급: %s\n", languageController.selectOne(film.getLanguageId()).getName(),
                film.getLength(), film.getRating());
        System.out.println("특별추가영상: " + film.getSpecialFeatures());
        System.out.println("영화줄거리: " + film.getDescription());

        String message = new String("이 영화로 선택하시겠습니까? Y/N");
        String yesNo = ScannerUtil.nextLine(scanner, message);
        if (yesNo.equalsIgnoreCase("y")) {

        } else {
            film.setFilmId(0);
        }
        return film.getFilmId();
    }

    private int searchMenu() {
        int filmId = 0;
        while (filmId == 0) {
            String message = new String("1. 제목으로 검색 2.내용으로검색 3. 언어로검색 4. 카테고리로검색 5. 배우이름으로검색 6. 돌아가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 6);
            if (userChoice == 1) {
                filmId = printAllByTitle();
            } else if (userChoice == 2) {
                filmId = printAllByDescription();
            } else if (userChoice == 3) {
                filmId = printAllByLanguage();
            } else if (userChoice == 4) {
                FilmCategoryViewer filmCategoryViewer = new FilmCategoryViewer(scanner);
                filmId = filmCategoryViewer.printAllByCategory();
            } else if (userChoice == 5) {
                FilmActorViewer filmActorViewer = new FilmActorViewer(scanner);
                filmId = filmActorViewer.printAllByActor();
            } else if (userChoice == 6) {
                break;
            }

        }

        return filmId;
    }

    private int printAllByTitle() {
        FilmController filmController = new FilmController(new MySqlConnector());

        int filmId = 0;
        while (filmId == 0) {
            String message = new String("검색하실 제목의 일부를 입력하시거나, 돌아가시려면 0을 입력해주세요.");
            String titlePart = ScannerUtil.nextLine(scanner, message);
            if (titlePart.equals("0")) {
                break;
            }
            ArrayList<FilmDTO> list = filmController.selectAllSearchByTitle(titlePart);
            if (list.isEmpty()) {
                System.out.println("영화정보가 없습니다.\n");
            } else {
                int pageMax = list.size() / 10;
                if (list.size() % 10 > 0) {
                    pageMax = list.size() / 10 + 1;
                }
                int size = 0;
                if (list.size() % 10 == 0) {
                    size = 10;
                }
                int page = 1;
                while (filmId == 0) {
                    if (page == pageMax) {

                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10 + size; i++) {
                            System.out.printf("영화번호: %d 영화제목: %s\n", list.get(i).getFilmId(), list.get(i).getTitle());
                        }
                    } else {
                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                            System.out.printf("영화번호: %d 영화제목: %s\n", list.get(i).getFilmId(), list.get(i).getTitle());

                        }
                    }
                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n",
                            pageMax, page);
                    message = new String("상세히 보실 영화의 번호를 입력하시거나 뒤로 돌아가시려면 0을 입력해주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if (userChoice instanceof Integer) {
                        if ((Integer) userChoice == 0) {
                            break;
                        } else {
                            for (FilmDTO f : list) {
                                if (f.getFilmId() == (Integer) userChoice) {
                                    filmId = printDetail((Integer) userChoice);
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
                        } else if ((Character) userChoice == 's') {
                            message = new String("원하는 페이지번호를 입력해주세요");
                            page = ScannerUtil.nextInt(scanner, message, 1, pageMax);
                        }
                    }
                }
            }
        }
        return filmId;
    }

    private int printAllByDescription() {
        FilmController filmController = new FilmController(new MySqlConnector());

        int filmId = 0;
        while (filmId == 0) {
            String message = new String("검색하실 내용의 일부를 입력하시거나, 돌아가시려면 0을 입력해주세요.");
            String descriptionPart = ScannerUtil.nextLine(scanner, message);
            if (descriptionPart.equals("0")) {
                break;
            }
            ArrayList<FilmDTO> list = filmController.selectAllSearchByDescription(descriptionPart);
            if (list.isEmpty()) {
                System.out.println("영화정보가 없습니다.\n");
            } else {
                int pageMax = list.size() / 10;
                if (list.size() % 10 > 0) {
                    pageMax = list.size() / 10 + 1;
                }
                int size = 0;
                if (list.size() % 10 == 0) {
                    size = 10;
                }
                int page = 1;
                while (filmId == 0) {
                    if (page == pageMax) {

                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10 + size; i++) {
                            System.out.printf("영화번호: %d 영화제목: %s\n", list.get(i).getFilmId(), list.get(i).getTitle());
                        }
                    } else {
                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                            System.out.printf("영화번호: %d 영화제목: %s\n", list.get(i).getFilmId(), list.get(i).getTitle());

                        }
                    }
                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n",
                            pageMax, page);
                    message = new String("상세히 보실 영화의 번호를 입력하시거나 뒤로 돌아가시려면 0을 입력해주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if (userChoice instanceof Integer) {
                        if ((Integer) userChoice == 0) {
                            break;
                        } else {
                            for (FilmDTO f : list) {
                                if (f.getFilmId() == (Integer) userChoice) {
                                    filmId = printDetail((Integer) userChoice);
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
                        } else if ((Character) userChoice == 's') {
                            message = new String("원하는 페이지번호를 입력해주세요");
                            page = ScannerUtil.nextInt(scanner, message, 1, pageMax);
                        }
                    }
                }
            }
        }
        return filmId;
    }

    private int printAllByLanguage() {
        FilmController filmController = new FilmController(new MySqlConnector());

        int filmId = 0;
        while (filmId == 0) {
            LanguageViewer languageViewer = new LanguageViewer(scanner);
            int languageId = languageViewer.selectId();
            if (languageId == 0) {
                break;
            }
            ArrayList<FilmDTO> list = filmController.selectAllByLanguageId(languageId);
            if (list.isEmpty()) {
                System.out.println("영화정보가 없습니다.\n");
            } else {
                int pageMax = list.size() / 10;
                if (list.size() % 10 > 0) {
                    pageMax = list.size() / 10 + 1;
                }
                int size = 0;
                if (list.size() % 10 == 0) {
                    size = 10;
                }
                int page = 1;
                while (filmId == 0) {
                    if (page == pageMax) {

                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + list.size() % 10 + size; i++) {
                            System.out.printf("영화번호: %d 영화제목: %s\n", list.get(i).getFilmId(), list.get(i).getTitle());
                        }
                    } else {
                        for (int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
                            System.out.printf("영화번호: %d 영화제목: %s\n", list.get(i).getFilmId(), list.get(i).getTitle());

                        }
                    }
                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n",
                            pageMax, page);
                    String message = new String("상세히 보실 영화의 번호를 입력하시거나 뒤로 돌아가시려면 0을 입력해주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if (userChoice instanceof Integer) {
                        if ((Integer) userChoice == 0) {
                            break;
                        } else {
                            for (FilmDTO f : list) {
                                if (f.getFilmId() == (Integer) userChoice) {
                                    filmId = printDetail((Integer) userChoice);
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
                        } else if ((Character) userChoice == 's') {
                            message = new String("원하는 페이지번호를 입력해주세요");
                            page = ScannerUtil.nextInt(scanner, message, 1, pageMax);
                        }
                    }
                }
            }
        }
        return filmId;
    }

    public void filmMenu() {
        while (true) {
            String message = new String("1. 신작영화 추가하기 2. 언어정보 추가하기 3. 돌아가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            if (userChoice == 1) {
                insertFilm();
            } else if (userChoice == 2) {
                LanguageViewer languageViewer = new LanguageViewer(scanner);
                languageViewer.insertLanguage();
            } else if (userChoice == 3) {
                break;
            }
        }
    }

    private void insertFilm() {
        FilmController filmController = new FilmController(new MySqlConnector());
        FilmDTO f = new FilmDTO();
        String message = new String("새로운 영화의 제목을 입력해주세요.");
        String title = ScannerUtil.nextLine(scanner, message);
        message = new String(title + "의 줄거리를 입력해주세요.");
        String description = ScannerUtil.nextLine(scanner, message);
        message = new String(title + "의 제작 년도를  입력해주세요.\n(1901~2155사이의 년도만 입력 가능합니다.)");
        int releaseYear = ScannerUtil.nextInt(scanner, message,1901,2155);
        
        LanguageViewer languageViewer = new LanguageViewer(scanner);
        int languageId = languageViewer.selectId();
        message = new String("대여가능일수를 입력해주세요");
        int rentalDuration = ScannerUtil.nextInt(scanner, message);
        while (rentalDuration < 0) {
            message = new String("0보다 작을순 없습니다. 다시 입력해주세요.");
            rentalDuration = ScannerUtil.nextInt(scanner, message);
        }
        message = new String("대여비용을 입력해주세요.");
        double rentalRate = ScannerUtil.nextDouble(scanner, message,0,100);
        
        message = new String("영화의 상영길이를 입력해주세요.");
        int length = ScannerUtil.nextInt(scanner, message);
        while (length < 0) {
            message = new String("0보다 작을순 없습니다. 다시 입력해주세요.");
            length = ScannerUtil.nextInt(scanner, message);
        }
        message = new String("교체비용을 입력해주세요.");
        double replacementCost = ScannerUtil.nextDouble(scanner, message,0,1000);
        message = new String("영화의 등급을 입력해주세요.\n [G] [PG] [PG-13] [R] [NC-17]");
        boolean ratingSwitch = true;
        String rating="G";
        while (ratingSwitch) {
            rating = ScannerUtil.nextLine(scanner, message);
            
            switch (rating) {
            case "G":
            case "PG":
            case "PG-13":
            case "R":
            case "NC-17":
                ratingSwitch = false;
                break;
            default:
                System.out.println("존재하지 않는 등급입니다.");
                break;
            }
        }
        System.out.println("특별영상은 추후에 추가해주세요.");
        
        LanguageController languageController = new LanguageController(new MySqlConnector());
        System.out.println("영화제목: " + title);
        System.out.println("영화제작년도: " + releaseYear + "년");
        System.out.printf("언어: %s 영상길이: %d 분 영상등급: %s\n", languageController.selectOne(languageId).getName(),
                length, rating);
        System.out.println("영화줄거리: " + description);
        message = new String("추가하실 영화의 정보가 확실합니까?? Y/N");
        String yesNo = ScannerUtil.nextLine(scanner, message);
        if (yesNo.equalsIgnoreCase("y")) {
            f.setTitle(title);
            f.setDescription(description);
            f.setReleaseYear(releaseYear);
            f.setLanguageId(languageId);
            f.setOriginalLanguageId(languageId);
            f.setRentalDuration(rentalDuration);
            f.setReplacementCost(replacementCost);
            f.setRentalRate(rentalRate);
            f.setLength(length);
            f.setRating(rating);
            int filmId=filmController.insert(f);
            System.out.println("영화의 대한 추가정보를 입력받겠습니다.");
//            FilmTextViewer filmTextViewer = new FilmTextViewer(scanner);
//            filmTextViewer.insert(filmId);
            FilmActorViewer filmActorViewer = new FilmActorViewer(scanner);
            filmActorViewer.insert(filmId);
            FilmCategoryViewer filmCategoryViewer = new FilmCategoryViewer(scanner);
            filmCategoryViewer.insert(filmId);
            System.out.println("영화 추가가 완료되었습니다.");
            
        } else {
            
        }

    }

}
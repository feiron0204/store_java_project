package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.MySqlConnector;
import controller.ActorController;
import model.ActorDTO;
import util.ScannerUtil;

public class ActorViewer {
    private Scanner scanner;
    public ActorViewer(Scanner scanner) {
        this.scanner=scanner;
    }
    public int selectId() {
        int actorId=0;
        boolean actorSwitch=true;
        while (actorId==0) {
            ActorController actorController = new ActorController(new MySqlConnector());
            if(actorId==0) {
                actorSwitch = true;
            }
            String message = new String("검색하실 배우이름를 입력해주시거나, 뒤로가시려면 0을 입력해주세요.");
            String actorPart = ScannerUtil.nextLine(scanner, message);
            if(actorPart.equals("0")) {
                break;
            }
            int page=1;
            while (actorSwitch) {
                ArrayList<ActorDTO> list = actorController.selectAll(actorPart);
                int pageMax=list.size()/10;
                if(list.size()%10>0) {
                    pageMax=list.size()/10+1;
                }
                int size=0;
                if(list.size()%10==0) {
                    size=10;
                }
                if (list.isEmpty()) {
                    System.out.println("해당 조건을 만족하는 배우가 없습니다.");
                    actorSwitch=false;
                } else {
                    if(page==pageMax) {
                        for (int i = (page - 1)*10; i < (page - 1) * 10 + list.size() % 10+size; i++) {
                            System.out.printf("배우번호: %d 배우명: %s %s\n", list.get(i).getActorId(), list.get(i).getFirstName(),list.get(i).getLastName());
                        }
                    }else {
                        for (int i = (page - 1)*10; i < (page - 1) * 10+10; i++) {
                            System.out.printf("배우번호: %d 배우명: %s %s\n", list.get(i).getActorId(), list.get(i).getFirstName(),list.get(i).getLastName());
                        }
                    }
                    
                    System.out.printf("[이전페이지 보기 p] (총 %d 페이지 중 %d) [다음페이지 보기 n]\n              [원하는페이지를 보시려면 s]\n", pageMax, page);

                    message = new String("배우를 선택하시거나 다시 검색하시려면 0 을 눌러주세요.");
                    Object userChoice = ScannerUtil.nextBasetype(scanner, message);
                    if(userChoice instanceof Integer) {
                        
                        for (ActorDTO c : list) {
                            if (c.getActorId() == (Integer)userChoice||(Integer)userChoice==0) {
                                if((Integer)userChoice!=0) {
                                    
                                    actorId = (Integer)userChoice;
                                }
                                actorSwitch = false;
                            }
                        }
                        if (actorSwitch) {
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
        return actorId;
    }
}

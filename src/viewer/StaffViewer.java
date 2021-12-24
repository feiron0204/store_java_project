package viewer;

import java.util.Scanner;

import connector.MySqlConnector;
import controller.AddressController;
import controller.CustomerController;
import controller.StaffController;
import controller.StoreController;
import model.StaffDTO;
import model.StoreDTO;
import util.ScannerUtil;

public class StaffViewer {
    private Scanner scanner;
    private StaffDTO staff;
    private StoreDTO store;
    public StaffViewer(StaffDTO staff,StoreDTO store,Scanner scanner) {
        this.scanner=scanner;
        this.staff=staff;
        this.store=store;
    }
    
    public StaffDTO logIn() {
        while(true) {
            String message = new String("1. 매니저 아이디로 로그인하기 2. 뒤로가기");
            int userChoice=ScannerUtil.nextInt(scanner, message,1,2);
            if(userChoice==1) {
                message = new String("아이디를 입력해주세요.");
                String username=ScannerUtil.nextLine(scanner, message);
                message = new String("비밀번호를 입력해주세요.");
                String password=ScannerUtil.nextLine(scanner, message);
                StaffController staffController = new StaffController(new MySqlConnector());
                staff=staffController.auth(username, password);
                if(staff!=null) {
                    break;
                }
            }else if(userChoice==2) {
                break;
            }
            
        }
        return staff;
    }
    
    public void levelUp() {
        CustomerViewer customerViewer = new CustomerViewer(null,store,scanner);
        int userChoice=customerViewer.printList(store.getStoreId());
        
        CustomerController customerController = new CustomerController(new MySqlConnector());
        if(customerController.selectOne(userChoice)!=null) {
            if(customerController.selectOne(userChoice).getStoreId()==store.getStoreId()) {
                StaffController staffController=new StaffController(new MySqlConnector());
                String message=new String("사용하실 username을 입력해주세요.");
                String username=ScannerUtil.nextLine(scanner, message);
                while(staffController.selectOne(username)!=null) {
                    message=new String("이미 사용중인 username입니다. 다시 입력해주세요.");
                    username=ScannerUtil.nextLine(scanner, message);
                }
                message=new String("사용하실 password를 입력해주세요.");
                String password=ScannerUtil.nextLine(scanner, message);
                
                StaffDTO s=new StaffDTO();
                s.setFirstName(customerController.selectOne(userChoice).getFirstName());
                s.setLastName(customerController.selectOne(userChoice).getLastName());
                s.setAddressId(customerController.selectOne(userChoice).getAddressId());
                s.setEmail(customerController.selectOne(userChoice).getEmail());
                s.setStoreId(customerController.selectOne(userChoice).getStoreId());
                s.setUsername(username);
                s.setPassword(password);
                
                staffController.insert(s);
                System.out.println("승격이 완료되었습니다.");
                
            }else {
                System.out.println("다른 대여점의 회원은 승격시키실 수 없습니다.");
            }
        }else {
            System.out.println("존재하지 않는 회원입니다.");
        }
    }
    public void staffInfo() {
        AddressController addressController = new AddressController(new MySqlConnector());
        StoreController storeController = new StoreController(new MySqlConnector());
        StaffController staffController = new StaffController(new MySqlConnector());
       System.out.println("=========================================");
       System.out.println("스텝번호: "+staffController.selectOne(staff.getStaffId()).getStaffId());
       System.out.println("스텝이름: "+staffController.selectOne(staff.getStaffId()).getFirstName()+" "+staffController.selectOne(staff.getStaffId()).getLastName());
       System.out.println("주소: "+addressController.selectOne(staffController.selectOne(staff.getStaffId()).getAddressId()).getAddress());
       System.out.println("전화번호: "+addressController.selectOne(staffController.selectOne(staff.getStaffId()).getAddressId()).getPhone());
       int myShop = 0; 
       if(storeController.selectAllByManagerStaffId(staff.getStaffId())!=null) {
        myShop=storeController.selectAllByManagerStaffId(staff.getStaffId()).size();   
       }
       System.out.println("현재 보유중인 대여점: "+myShop+"개 지점");
       System.out.println("현재 일하는 대여점: "+staffController.selectOne(storeController.selectOne(staffController.selectOne(staff.getStaffId()).getStoreId()).getManagerStaffId()).getFirstName()+" "+staffController.selectOne(storeController.selectOne(staffController.selectOne(staff.getStaffId()).getStoreId()).getManagerStaffId()).getLastName()+"의 가게");
       System.out.println("이메일: "+staffController.selectOne(staff.getStaffId()).getEmail());
       System.out.println("로그인 아이디: "+staffController.selectOne(staff.getStaffId()).getUsername());
       
       String message = new String("1. 정보수정 2. 일하는 매장 가기 3. 돌아가기");
       int userChoice=ScannerUtil.nextInt(scanner, message,1,3);
       if(userChoice == 1) {
           
           staffInfo();
       }else if(userChoice==2) {
           StoreViewer storeViewer = new StoreViewer(null,staff,scanner);
           storeViewer.storeMain(storeController.selectOne(staffController.selectOne(staff.getStaffId()).getStoreId()));
       }else if(userChoice==3) {
           
       }
    }
}

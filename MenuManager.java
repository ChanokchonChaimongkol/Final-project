package YummyList;

import java.io.*;
import java.util.*;

public class MenuManager {

    private ArrayList<Menu> menuList = new ArrayList<>();
    private final String FILE_NAME = "menu.txt";

    public MenuManager() {
        loadFromFile();
        if(menuList.isEmpty()){
            addDefaultMenus();
            saveToFile();
        }
    }

    // ================= DEFAULT 12 MENUS =================
    private void addDefaultMenus() {
        menuList.add(new Menu(1,"Fried Rice",50,10));
        menuList.add(new Menu(2,"Pad Thai",60,12));
        menuList.add(new Menu(3,"Tom Yum",80,15));
        menuList.add(new Menu(4,"Green Curry",70,18));
        menuList.add(new Menu(5,"Som Tam",40,8));
        menuList.add(new Menu(6,"Grilled Chicken",90,20));
        menuList.add(new Menu(7,"Burger",85,14));
        menuList.add(new Menu(8,"Pizza",120,25));
        menuList.add(new Menu(9,"Spaghetti",100,16));
        menuList.add(new Menu(10,"Steak",150,30));
        menuList.add(new Menu(11,"Ice Cream",35,5));
        menuList.add(new Menu(12,"Coffee",45,3));
    }

    // ================= DISPLAY MENU (Beautiful Format) =================
    public String getMenuDisplay() {

        StringBuilder sb = new StringBuilder();

        sb.append("=========== YUMMY LIST MENU ===========\n\n");
        sb.append(String.format("%-4s %-18s %-10s %-10s\n",
                "ID", "Name", "Price", "Time(min)"));
        sb.append("--------------------------------------------------\n");

        for (Menu m : menuList) {
            sb.append(String.format("%-4d %-18s %-10.2f %-10d\n",
                    m.getId(),
                    m.getName(),
                    m.getPrice(),
                    m.getCookTime()));
        }

        sb.append("\n----------------------------------------------");
        sb.append("\nPlease enter Menu ID to order.");

        return sb.toString();
    }

    // ================= GET MENU BY ID =================
    public Menu getMenuById(int id){
        for(Menu m : menuList){
            if(m.getId() == id)
                return m;
        }
        return null;
    }

    // ================= CHECK DUPLICATE ID =================
    private boolean isDuplicateId(int id){
        for(Menu m : menuList){
            if(m.getId() == id)
                return true;
        }
        return false;
    }

    // ================= ADD MENU =================
    public boolean addMenu(Menu menu){

        if(isDuplicateId(menu.getId())){
            return false; // ID ซ้ำ
        }

        menuList.add(menu);
        saveToFile();
        return true;
    }

    // ================= EDIT MENU =================
    public boolean editMenu(int id, String name, double price, int cookTime){

        Menu m = getMenuById(id);

        if(m != null){
            m.setName(name);
            m.setPrice(price);
            m.setCookTime(cookTime);
            saveToFile();
            return true;
        }

        return false;
    }

    // ================= DELETE MENU =================
    public boolean deleteMenu(int id){

        Iterator<Menu> iterator = menuList.iterator();

        while(iterator.hasNext()){
            Menu m = iterator.next();
            if(m.getId() == id){
                iterator.remove();
                saveToFile();
                return true;
            }
        }

        return false;
    }

    // ================= SAVE TO FILE =================
    public void saveToFile(){
        try(PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))){

            for(Menu m : menuList){
                pw.println(m.toString());
            }

        }catch(IOException e){
            System.out.println("Error saving menu file.");
        }
    }

    // ================= LOAD FROM FILE =================
    public void loadFromFile(){

        menuList.clear();

        try(Scanner sc = new Scanner(new File(FILE_NAME))){

            while(sc.hasNextLine()){

                String line = sc.nextLine();
                String[] data = line.split(",");

                // รองรับไฟล์เก่า (3 ค่า)
                if(data.length == 3){
                    menuList.add(new Menu(
                            Integer.parseInt(data[0]),
                            data[1],
                            Double.parseDouble(data[2]),
                            10   // default cookTime
                    ));
                }

                // ไฟล์ใหม่ (4 ค่า)
                else if(data.length == 4){
                    menuList.add(new Menu(
                            Integer.parseInt(data[0]),
                            data[1],
                            Double.parseDouble(data[2]),
                            Integer.parseInt(data[3])
                    ));
                }
            }

        }catch(FileNotFoundException e){
            System.out.println("Menu file not found. Creating default menu...");
        }
    }
}

package Run;

import Bean.Business;
import Bean.Customer;
import Bean.Movie;
import Bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MovieSystem {

    public static final List<User> ALL_USERS = new ArrayList<>();

    public static Map<Business, List<Movie>> ALL_MOVIES = new HashMap<>();

    public static final Scanner SYS_SC = new Scanner(System.in);

    public static  User loginUser;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/hh HH:mm:ss");

    public static final Logger LOGGER = LoggerFactory.getLogger("MovieSystem.class");

    static{
        Customer c = new Customer();
        c.setLoginName("zyf888");
        c.setPassword("123456");
        c.setUserName("liu");
        c.setSex('男');
        c.setMoney(2000);
        c.setPhone("111111");
        ALL_USERS.add(c);

        Customer c1 = new Customer();
        c1.setLoginName("gyf888");
        c1.setPassword("123456");
        c1.setUserName("qing");
        c1.setSex('女');
        c1.setMoney(20000);
        c1.setPhone("111111");
        ALL_USERS.add(c1);

        Business b = new Business();
        b.setLoginName("bzg888");
        b.setPassword("123456");
        b.setUserName("liu");
        b.setSex('男');
        b.setMoney(2000);
        b.setPhone("111111");
        b.setAddress("火星6号2B二层");
        b.setShopName("天天影院");
        ALL_USERS.add(b);

        List<Movie> movies = new ArrayList<>();
        ALL_MOVIES.put(b, movies);

        Business b1 = new Business();
        b1.setLoginName("bzp888");
        b1.setPassword("123456");
        b1.setUserName("chen");
        b1.setSex('女');
        b1.setMoney(2000);
        b1.setPhone("111111");
        b1.setAddress("火星8号2B二层");
        b1.setShopName("巧克力影院");
        ALL_USERS.add(b1);

        List<Movie> movies1 = new ArrayList<>();
        ALL_MOVIES.put(b1, movies1);
    }

    public static void main(String[] args) {
        showMain();
    }

    private static void showMain() {
        while (true) {
            System.out.println("Welcome to the Century Theater");
            System.out.println("1, Login");
            System.out.println("2, Sign Up");
            System.out.println("3, Owner Sign Up");
            System.out.println("Please enter order number:");
            String command = SYS_SC.nextLine();
            switch (command){
                case "1":
                    login();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                default:
                    System.out.println("Please double check with the order number");
            }
        }
    }

    private static void login(){
        System.out.println("Username: ");
        String loginName = SYS_SC.nextLine();
        System.out.println("Password: ");
        String passWord = SYS_SC.nextLine();

        User u = getUserByLoginName(loginName);
        if (u != null){
            if (u.getPassword().equals(passWord)){
                loginUser = u;
                LOGGER.info(u.getUserName() + "Logged In");
                if (u instanceof Customer){
                    showCustomerMain();
                }else {
                    showBussinessMain();
                }
                return;
            }else{
                System.out.println("Wrong Password");
            }
        }else {
            System.out.println("Wrong Username，Please try again");
        }

    }

    private static void showBussinessMain() {
        System.out.println("Welcome to the Century Theater");
        System.out.println(loginUser.getUserName() + (loginUser.getSex() == 'M'? "Sir":"Madam" + " Welcome"));
        System.out.println("Please select your action");
        System.out.println("1, Show Info");
        System.out.println("2, Upload Movie");
        System.out.println("3, Remove Movie");
        System.out.println("4, Change Movie");
        System.out.println("5, Exit");
        while (true) {
            System.out.println("Please enter order number：");
            String command = SYS_SC.nextLine();
            switch (command){
                case "1":
                    showBussinessInfo();
                    break;
                case "2":
                    addMovie();
                    break;
                case "3":
                    deleteMovie();
                    break;
                case "4":
                    updateMovie();
                    break;
                case "5":
                    System.out.println(loginUser.getUserName() + "Hope to see you again");
                    return;
                default:
                    System.out.println("Please double check with the order number");
            }
        }
    }

    private static void updateMovie() {
        System.out.println("Welcome to the Century Theater");
        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        if (movies.size() == 0){
            System.out.println("There is no movie to show");
            return;
        }
        while (true) {
            System.out.println("Please enter the movie name you would like to change ：" );
            String movieName = SYS_SC.nextLine();

            Movie movie = getMovieByName(movieName);
            if (movie != null){
                System.out.println("New Movie Name： ");
                String name = SYS_SC.nextLine();
                System.out.println("New Main Character： ");
                String actor = SYS_SC.nextLine();
                System.out.println("New Time Line： ");
                String time = SYS_SC.nextLine();
                System.out.println("New Price： ");
                String price = SYS_SC.nextLine();
                System.out.println("New InStock Tickets： ");
                String totalNumber = SYS_SC.nextLine();
                while (true) {
                    try {
                        System.out.println("New showtime date： ");
                        String stime = SYS_SC.nextLine();
                        movie.setName(name);
                        movie.setActor(actor);
                        movie.setPrice(Double.valueOf(price));
                        movie.setTime(Double.valueOf(time));
                        movie.setNumber(Integer.valueOf(totalNumber));
                        movie.setStartTime(sdf.parse(stime));
                        System.out.println("Congrats, You had successfully change the info");
                        showBussinessInfo();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("TimeLine Error");
                    }
                }
            }else {
                System.out.println("This Theater doesn't has this movie" + movie.getName());
                System.out.println("Still want to Change？");
                String command = SYS_SC.nextLine();
                switch (command){
                    case "y":
                        break;
                    default:
                        System.out.println("Ok");
                        return;
                }
            }
        }
    }

    private static void deleteMovie() {
        System.out.println("==============Remove Movie==========");
        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        if (movies.size() == 0){
            System.out.println("No movie is available to remove");
            return;
        }
        while (true) {
            System.out.println("Please Enter the remove movie name ：" );
            String movieName = SYS_SC.nextLine();

            Movie movie = getMovieByName(movieName);
            if (movie != null){
                movies.remove(movie);
                System.out.println("This Theater doesn't has this movie" + movie.getName());
                showBussinessInfo();
                return;
            }else {
                System.out.println("This Theater doesn't has this movie");
                System.out.println("Still want to remove？y/n");
                String command = SYS_SC.nextLine();
                switch (command){
                    case "y":
                        break;
                    default:
                        System.out.println("Ok");
                        return;
                }
            }
        }
    }

    public  static Movie getMovieByName(String movieName){
        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        for (Movie movie : movies) {
            if (movie.getName().contains(movieName)){
                return movie;
            }
        }
        return null;
    }


    private static void addMovie() {
        System.out.println("==============Upload Movie==========");

        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);

        System.out.println("New Movie name： ");
        String name = SYS_SC.nextLine();
        System.out.println("Main Character： ");
        String actor = SYS_SC.nextLine();
        System.out.println("Time： ");
        String time = SYS_SC.nextLine();
        System.out.println("Ticket Price： ");
        String price = SYS_SC.nextLine();
        System.out.println("Ticket InStock： ");
        String totalNumber = SYS_SC.nextLine();
        while (true) {
            try {
                System.out.println("ShowTime date： ");
            String stime = SYS_SC.nextLine();
                Movie movie = new Movie(name, actor, Double.valueOf(time), Double.valueOf(price), Integer.valueOf(totalNumber),
                        sdf.parse(stime));
                movies.add(movie);
                System.out.println("Upload Successfully： 《" + movie.getName() + "》");
                return;
            } catch (ParseException e) {
                e.printStackTrace();
                LOGGER.error("error");
            }
        }
    }

    private static void showBussinessInfo() {
        System.out.println("==============Owner Page==========");
        LOGGER.info(loginUser.getUserName() + "Owner is browsing ");
        Business business = (Business) loginUser;
        System.out.println(business.getShopName() + "\t\tPhone" + business.getPhone() + "\t\tAddress" + business.getAddress() + "\t\tBalance" + business.getMoney());
        List<Movie> movies = ALL_MOVIES.get(loginUser);
        System.out.println("Movie Name\t\t\tMain Character\t\t\tTime\t\t\tScore\t\t\tTicket Price\t\t\tTicket InStock\t\t\tShowtime Date");
        if (movies.size() > 0){
            for (Movie movie : movies) {
                System.out.println(movie.getName() + "\t\t\t" + movie.getActor() + "\t\t\t" + movie.getTime()
                        + "\t\t\t" + movie.getScore() + "\t\t\t" + movie.getPrice() + "\t\t\t" + movie.getNumber()
                        + "\t\t\t" + sdf.format(movie.getStartTime()));
            }
        }else {
            System.out.println("当前无片");
        }
    }


    private static void showCustomerMain() {
        while (true) {
        System.out.println("=========Customer Page============");
        System.out.println(loginUser.getUserName() + (loginUser.getSex() == 'M'? "Sir":"Madam" + " Welcome") +
                "\tBalance" + loginUser.getMoney());
        System.out.println("Please select the order number");
        System.out.println("1, Show all movie");
        System.out.println("2, Search by movie name");
        System.out.println("3, Score");
        System.out.println("4, Buy Ticket");
        System.out.println("5, Exit");
            System.out.println("Please enter command：");
        String command = SYS_SC.nextLine();
        switch (command){
            case "1":
                showAllMovie();
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                buyMovie();
                break;
            case "5":
                return;
            default:
                System.out.println("Wrong Command");
        }
        }

    }

    private static void buyMovie() {
        showAllMovie();
        System.out.println("=============Buy Ticket==========");
        while (true) {
            System.out.println("Please enter Theater name");
            String shopName = SYS_SC.nextLine();

            Business business = getBusinessByShopName(shopName);
            if (business == null){
                System.out.println("No Theater is found");
            }else {
                List<Movie> movies =ALL_MOVIES.get(business);

                if (movies.size() > 0){
                    while (true) {
                        System.out.println("Movie name： ");
                        String movieName = SYS_SC.nextLine();
                        Movie movie =getMovieByShopAndName(business, movieName);
                        if (movie != null){
                            while (true) {
                                System.out.println("请你输入购票数量： ");
                                String number = SYS_SC.nextLine();
                                int buyNumber = Integer.valueOf(number);
                                if (movie.getNumber() >= buyNumber){
                                        double money = BigDecimal.valueOf(movie.getPrice())
                                                .multiply(BigDecimal.valueOf(buyNumber)).doubleValue();
                                        if (loginUser.getMoney() >= money){
                                            System.out.println("成功购买" + movie.getName()+ buyNumber +
                                                    "张票，总金额" + money);
                                            loginUser.setMoney(loginUser.getMoney() - money);
                                            business.setMoney(business.getMoney() + money);
                                            movie.setNumber(movie.getNumber() - buyNumber);
                                            return;
                                        }else {
                                            System.out.println("是否继续");
                                            System.out.println("是否继续买票？y/n");
                                            String command = SYS_SC.nextLine();
                                            switch (command){
                                                case "y":
                                                    break;
                                                default:
                                                    System.out.println("好的");
                                                    return;
                                            }
                                        }
                                }else {
                                    System.out.println("当前电影票只剩： " + movie.getNumber());
                                    System.out.println("是否继续买票？y/n");
                                    String command = SYS_SC.nextLine();
                                    switch (command){
                                        case "y":
                                            break;
                                        default:
                                            System.out.println("好的");
                                            return;
                                    }
                                }
                            }
                        }else{
                            System.out.println("电影有问题");
                        }
                    }

                }else {
                    System.out.println("电影院已关门了");
                    System.out.println("是否继续买票？y/n");
                    String command = SYS_SC.nextLine();
                    switch (command){
                        case "y":
                            break;
                        default:
                            System.out.println("好的");
                            return;
                    }
                }
            }
        }
    }

    public static Movie getMovieByShopAndName(Business business, String name){
        List<Movie> movies = ALL_MOVIES.get(business);
        for (Movie movie : movies) {
            if (movie.getName().contains(name)){
                return movie;
            }
        }
        return null;
    }


    public static Business getBusinessByShopName(String shopName){
        Set<Business> businesses = ALL_MOVIES.keySet();
        for (Business business : businesses) {
            if (business.getShopName().equals(shopName)){
                return business;
            }
        }
        return null;
    }


    private static void showAllMovie() {
        System.out.println("=============展示影片==========");
        ALL_MOVIES.forEach((business, movies) ->  {
            System.out.println(business.getShopName() + "\t\t电话" + business.getPhone() + "\t\t地址" + business.getAddress());
                System.out.println("片名\t\t\t主演\t\t\t时长\t\t\t评分\t\t\t票价\t\t\t余票数量\t\t\t放映时间");
            for (Movie movie : movies) {
                System.out.println(movie.getName() + "\t\t\t" + movie.getActor() + "\t\t\t" + movie.getTime()
                        + "\t\t\t" + movie.getScore() + "\t\t\t" + movie.getPrice() + "\t\t\t" + movie.getNumber()
                        + "\t\t\t" + sdf.format(movie.getStartTime()));
            }
            });
    }

    public static  User getUserByLoginName(String loginName){
        for (User user : ALL_USERS) {
            if (user.getLoginName().equals(loginName)){
                return user;
            }
        }
        return null;
    }


}

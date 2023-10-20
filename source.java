import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import javax.swing.*;
import java.awt.*;


public class source extends JFrame {
	String url = "jdbc:postgresql://127.0.0.1:5432/minsu";
	String username = "minsu";
	String pswd = "minsu";
	public static void main(String[] args) {
		source db = new source();
		String SQL_CREATE1 = "CREATE TABLE USER_ENTITY (user_id integer NOT NULL PRIMARY KEY,name varchar(20) NOT NULL,pwd varchar(20) NOT NULL,email varchar(20) NOT NULL,reminder_default integer NOT NULL);";
		String SQL_CREATE2 = "CREATE TABLE EVENT1 (event_id integer NOT NULL PRIMARY KEY,host_id integer NOT NULL,ev_name varchar(100) NOT NULL,description varchar(200) NOT NULL,start_time TIMESTAMP NOT NULL,end_time TIMESTAMP NOT NULL,ev_location varchar(100) NOT NULL,FOREIGN KEY(host_id) REFERENCES USER_ENTITY(user_id));";
		String SQL_CREATE3 = "CREATE TABLE CALENDAR_EVENT (user_id integer NOT NULL,event_id integer NOT NULL,role char(1) NOT NULL,reminder integer NOT NULL,FOREIGN KEY(user_id) REFERENCES USER_ENTITY(user_id),FOREIGN KEY(event_id) REFERENCES EVENT1(event_id));";
		String SQL_CREATE4 = "CREATE TABLE NOTIFICATION_MODIFIED (msg_id integer NOT NULL,receiver_id integer NOT NULL,sender_id integer NOT NULL, event_id integer NOT NULL,FOREIGN KEY(event_id) REFERENCES EVENT1(event_id),FOREIGN KEY(receiver_id) REFERENCES USER_ENTITY(user_id),FOREIGN KEY(sender_id) REFERENCES USER_ENTITY(user_id),FOREIGN KEY(msg_id) REFERENCES MESSAGE_BOX1(msg_id));";
		String SQL_CREATE5 = "CREATE TABLE NOTIFICATION_CANCELED (msg_id integer NOT NULL,receiver_id integer NOT NULL,sender_id integer NOT NULL, ev_name varchar(100) NOT NULL,description varchar(200) NOT NULL,start_time TIMESTAMP NOT NULL,end_time TIMESTAMP NOT NULL,FOREIGN KEY(receiver_id) REFERENCES USER_ENTITY(user_id),FOREIGN KEY(sender_id) REFERENCES USER_ENTITY(user_id),FOREIGN KEY(msg_id) REFERENCES MESSAGE_BOX1(msg_id));";
		String SQL_CREATE6 = "CREATE TABLE INVITATION (msg_id integer NOT NULL,receiver_id integer NOT NULL,event_id integer NOT NULL,participants varchar(20)[] NOT NULL,FOREIGN KEY(receiver_id) REFERENCES USER_ENTITY(user_id),FOREIGN KEY(event_id) REFERENCES EVENT1(event_id),FOREIGN KEY(msg_id) REFERENCES MESSAGE_BOX1(msg_id));";
		String SQL_CREATE7 = "CREATE TABLE PERMISSION_CANCEL (msg_id integer NOT NULL,receiver_id integer NOT NULL,sender_id integer NOT NULL, event_id integer NOT NULL,FOREIGN KEY(event_id) REFERENCES EVENT1(event_id),FOREIGN KEY(receiver_id) REFERENCES USER_ENTITY(user_id),FOREIGN KEY(sender_id) REFERENCES USER_ENTITY(user_id),FOREIGN KEY(msg_id) REFERENCES MESSAGE_BOX1(msg_id));";
		String SQL_CREATE8 = "CREATE TABLE PERMISSION_MODIFY (msg_id integer NOT NULL,receiver_id integer NOT NULL,sender_id integer NOT NULL, event_id integer NOT NULL,host_id integer NOT NULL,ev_name varchar(100) NOT NULL,description varchar(200) NOT NULL,start_time TIMESTAMP NOT NULL,end_time TIMESTAMP NOT NULL,participants varchar(20)[] NOT NULL,FOREIGN KEY(event_id) REFERENCES EVENT1(event_id),FOREIGN KEY(receiver_id) REFERENCES USER_ENTITY(user_id),FOREIGN KEY(sender_id) REFERENCES USER_ENTITY(user_id),FOREIGN KEY(msg_id) REFERENCES MESSAGE_BOX1(msg_id));";
		String SQL_CREATE9 = "CREATE TABLE REMINDER (user_id integer NOT NULL,event_id integer NOT NULL,time_remaining integer NOT NULL,FOREIGN KEY(event_id) REFERENCES EVENT1(event_id),FOREIGN KEY(user_id) REFERENCES USER_ENTITY(user_id));";
		String SQL_CREATE10 = "CREATE TABLE MESSAGE_BOX1 (msg_id integer NOT NULL PRIMARY KEY,receiver_id integer NOT NULL,msg_type integer NOT NULL,FOREIGN KEY(receiver_id) REFERENCES USER_ENTITY(user_id));";
		System.out.println("First Connection!");
		/*db.createTable(SQL_CREATE1);
		db.createTable(SQL_CREATE2);
		db.createTable(SQL_CREATE3);
		db.createTable(SQL_CREATE10);
		db.createTable(SQL_CREATE4);
		db.createTable(SQL_CREATE5);
		db.createTable(SQL_CREATE6);
		db.createTable(SQL_CREATE7);
		db.createTable(SQL_CREATE8);
		db.createTable(SQL_CREATE9);*/
		System.out.println("Create Database Complete!");
		ScheduledExecutorService executorService;
        	executorService = Executors.newSingleThreadScheduledExecutor();
        	executorService.scheduleAtFixedRate(source::check_reminder, 0, 60, TimeUnit.SECONDS);
        	//Update the Time & Update REMIDER TABLE every minute
		while(true){
			int input_user_id;
			System.out.println();
			input_user_id = db.login();//Login
			db.newconsole();//Clear	
			db.mainpage(input_user_id);//Connect to MainPage
			db.newconsole();//Clear
		}
	}
	
	private static void check_reminder() {
		LocalDateTime now = LocalDateTime.now();
		//Save the current time in "now"
		
		//JOIN the table "CALENDAR_EVENT" & "EVENT"
		//INSERT reminders INTO REMINDER(Table)
    	}
    	
	void mainpage(int userID){
		//view calender UI
		Scanner in = new Scanner(System.in);
		source db = new source();
        	db.view_calendar(userID);
        	System.out.println("1. Get User Info");
        	System.out.println("2. View Events");
        	System.out.println("3. Message Box");
        	System.out.println("4. Create Event");
        	System.out.println("5. Update Event");
        	System.out.println("6. Delete Event");
        	System.out.println("7. Set defalut reminders time");
        	System.out.println("8. Reminders");
        	System.out.println("9. Quit & Return to Login Page");
        	while(true){
        		boolean flag = false;
        		System.out.print("Enter the Number what you want : ");
        		int inputnumber = in.nextInt();
        		switch (inputnumber) {
        			case 1: db.newconsole();
        				db.getUserInfo(userID);
        				break;
        			case 2: db.newconsole();
        				db.viewEvents(userID);
        				break;
        			case 3: db.newconsole();
        				db.messageBox(userID);
        				break;
        			case 4: db.newconsole();
        				db.createEvent(userID);
        				break;
        			case 5: db.newconsole();
        				db.updateEvent(userID);
        				break;
        			case 6: db.newconsole();
        				db.deleteEvent(userID);
        				break;
        			case 7: db.newconsole();
        				db.setDefaultRemindersTime(userID);
        				break;
        			case 8: db.newconsole();
        				db.reminders(userID);
        				break;
        			case 9: return;
        			default : System.out.println("Invalid Number");
        				flag = true;
        				break;
        		}
        		if(flag == false) {
        			break;
        		}
        	}
	}
	
	void getUserInfo(int userID){
		source db = new source();
		Scanner in = new Scanner(System.in);
		
		System.out.println("UserID : 1");
		System.out.println("Name : Minsu");
		System.out.println("Email : wjsalstn15@naver.com");
		//SELECT and View the user imformation except for password 
		
		while(true){
			System.out.print("Enter q to quit : ");
			char inputChar = in.nextLine().charAt(0);
			if(inputChar == 'q'){
				break;
			}
		}
		db.newconsole();
		db.mainpage(userID);
	}
	
	void viewEvents(int userID){
	
		source db = new source();
		Scanner in = new Scanner(System.in);
		Scanner inp = new Scanner(System.in);
	
		while(true){
			System.out.println("1. Monthly 2. Weekly 3. Daily");
			System.out.print("Enter the number : ");
			int inputnumber = in.nextInt();
			if(inputnumber == 1){
				//Select and View Event Details 
				System.out.println("Monthly");
				break;
			}
			if(inputnumber == 2){
				//Select and View Event Details 
				System.out.println("Weekly");
				break;
			}
			if(inputnumber == 3){
				//Select and View Event Details 
				System.out.println("Daily");
				break;
			}
			else{
				System.out.println("Invalid Number!");
			}
		}
		while(true){
			System.out.print("Enter q to quit : ");
			char inputChar = inp.nextLine().charAt(0);
			if(inputChar == 'q'){
				break;
			}
		}
		db.newconsole();
		db.mainpage(userID);
	}
	
	void messageBox(int userID){
	
		source db = new source();
		Scanner in = new Scanner(System.in);
		Scanner inp = new Scanner(System.in);
		
		System.out.println("Permission");
		//View Permision_modify & Permision_cancel
		
		System.out.println("Notification");
		//View Notification
		
		System.out.println("Invitation");
		//View Invitation
		
		while(true){
			System.out.print("Enter the message ID that you want to check : ");
			int inputNumber = in.nextInt();
			if(true/*Valid Number Exist*/){
				//Show details
				//Reply Yes or No to request
				//INSERT
				//DELETE ...
				//Perform all tasks
				break;
			}
		}
		
		while(true){
			System.out.print("Enter q to quit : ");
			char inputChar = inp.nextLine().charAt(0);
			if(inputChar == 'q'){
				break;
			}
		}
		
		db.newconsole();
		db.mainpage(userID);
		
	}
	
	void createEvent(int userID){
	
		source db = new source();
		Scanner in = new Scanner(System.in);
		Scanner inp = new Scanner(System.in);
		
		//Receive Event Details
		//INSERT INTO EVENT1
		//Send Invitation to participants (Insert into INVITATION Table) 
		
		while(true){
			System.out.print("Enter q to quit : ");
			char inputChar = inp.nextLine().charAt(0);
			if(inputChar == 'q'){
				break;
			}
		}
		
		db.newconsole();
		db.mainpage(userID);
	}
	void updateEvent(int userID){
	
		source db = new source();
		Scanner in = new Scanner(System.in);
		Scanner inp = new Scanner(System.in);
		
		//Receive Update details
		//If. Event HostID == userID -> Update EVENT1 Table & Inform to others(Insert into NOTIFICATION_MODIFIED)
		//Else. Send Permission to Host (Insert into PERMISSION_MODIFY Table) 
		
		while(true){
			System.out.print("Enter q to quit : ");
			char inputChar = inp.nextLine().charAt(0);
			if(inputChar == 'q'){
				break;
			}
		}
		
		db.newconsole();
		db.mainpage(userID);
		
	}
	
	void deleteEvent(int userID){
	
		source db = new source();
		Scanner in = new Scanner(System.in);
		Scanner inp = new Scanner(System.in);
		
		//Receive What to delete response
		//If. Event HostID == userID -> Delete EVENT1 Table & Inform to others(Insert into NOTIFICATION_CANCELED)
		//Else. Send Permission to Host (Insert into PERMISSION_DELETE Table) 
		
		while(true){
			System.out.print("Enter q to quit : ");
			char inputChar = inp.nextLine().charAt(0);
			if(inputChar == 'q'){
				break;
			}
		}
		
		db.newconsole();
		db.mainpage(userID);
	}
	
	void setDefaultRemindersTime(int userID){
	
		source db = new source();
		Scanner inp = new Scanner(System.in);
		
		//Receive the time
		//Insert into USER_ENTITY
		
		while(true){
			System.out.print("Enter q to quit : ");
			char inputChar = inp.nextLine().charAt(0);
			if(inputChar == 'q'){
				break;
			}
		}
		
		db.newconsole();
		db.mainpage(userID);
		
	}
	
	void reminders(int userID){
		
		source db = new source();
		Scanner inp = new Scanner(System.in);
		
		//Select & View REMINDER Table
		//Delete tuples
		
		while(true){
			System.out.print("Enter q to quit : ");
			char inputChar = inp.nextLine().charAt(0);
			if(inputChar == 'q'){
				break;
			}
		}
		
		db.newconsole();
		db.mainpage(userID);
		
	}
	void view_calendar(int userID){
		
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		int hour = now.getHour();
		int minute = now.getMinute();
		int second = now.getSecond();
		
		
		Calendar cal = Calendar.getInstance();
		setTitle("Calendar");
		setSize(500,250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel main_panel = new JPanel();
		GridLayout grid = new GridLayout(7,7);
		grid.setVgap(5);
		setLayout(grid);
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		
		String str1 = Integer.toString(month);
		JLabel lb_text1 = new JLabel();
		lb_text1.setText(str1);
		add(lb_text1);
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		
		add(new JLabel("SUN"));
		add(new JLabel("MON"));
		add(new JLabel("TUE"));
		add(new JLabel("WED"));
		add(new JLabel("THR"));
		add(new JLabel("FRI"));
		add(new JLabel("SAT"));
		cal.set(year, month-1, 1);
          	int start = cal.get(Calendar.DAY_OF_WEEK);
              
          	for(int i=1; i<start; i++) add(new JLabel(" "));
          	for(int i=1;i<=cal.getActualMaximum(Calendar.DATE);i++){
              		String t = Integer.toString(i);
              		add(new JLabel(t));
              		if(start%7 == 0) {
              			System.out.println();
              		}
              		start++;
              	}
		setVisible(true);
     		
         	
              	
              	for(int i=1;i<=cal.getActualMaximum(Calendar.DATE);i++){
              		System.out.printf("%d\t", i);
              		if(start%7 == 0) {
              			System.out.println();
              		}
              		start++;
              	}
              	System.out.println("\n\t\t\t\tNow : " + year + "-" + month + "-" + day + " " + hour + ":" + minute + "\n");
	}
	
	int login(){
		Scanner in = new Scanner(System.in);
		System.out.println("Who are you?");
		System.out.print("Name : ");
		String nameipt = in.nextLine();
		int t1 = 1;
		if(/*nameipt is stored in user_name*/t1 >= 1) {
			int t2 = 1;
			while(true){
				System.out.print("password : ");
				String pwdipt = in.nextLine();
				if(/*password is correct*/ t2>=1) {
					return 1;//return user_id;
				}
				System.out.println("Wrong Password!");
			}
		}
		else {
			System.out.println("New Face!");
			System.out.print("Set Password : ");
			String newpwd = in.nextLine();
			System.out.print("Enter your Email : ");
			String newemail = in.nextLine();
			//INSERT INTO USER_ENTITY
			return 1;//return user_id
		}
	}
	
	void createTable(String create){
		try (Connection conn = DriverManager.getConnection(url,username,pswd);
			PreparedStatement preparedStatement = conn.prepareStatement(create)) {
			preparedStatement.executeUpdate();
			System.out.println("Create Successfully");
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void newconsole(){
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
}

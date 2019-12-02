import java.sql.*;
import java.util.Scanner;
import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.io.IOException; 

public class Jnote {
	
	public static Connection conn = null;
	public static Statement stmt =  null;
	
	private static Scanner sc = new Scanner(System.in);

	private static void CRUD() {

		try {
			conn = DriverManager.getConnection("jdbc:sqlite:databases/jnote_data.db");
			if(conn != null){
				//System.out.println("=============== CONECTION SUCCESSFUL =================");
				stmt = (Statement) conn.createStatement();
			}
		} catch (SQLException e) {
			System.out.println("=============== ERROR OCCURED =====================");
			System.err.println(e);
			System.out.println("===================================================");
			System.exit(0);
		}
	}
	
	private static void help(){
		System.out.println("\n####################################################");
		System.out.println("JNOTE by github.com/sharadcodes");
		System.out.println("All Rights Reserved");
		System.out.println("####################################################\n");
		System.out.println("LIST OF COMMANDS\n");
		System.out.println("jnote -n   -  For creating a new note");
		System.out.println("jnote -l   -  For listing all the notes");
		System.out.println("jnote -d   -  For deleting a note with particular ID");
		System.out.println("jnote -da  -  For deleting all the notes");
		System.out.println("\n####################################################");	
		try{conn.close();}
		catch(SQLException e){System.out.println(e);}
	}
	
	public static void main(String args[]) throws IOException{
		CRUD();
		if(args.length == 1){
			switch(args[0]){
				case "-n": {
					InputStreamReader r = new InputStreamReader(System.in);    
					BufferedReader br = new BufferedReader(r);           					
					System.out.println("\n#######################################");
					System.out.println("Start typing & press Enter Key to finish\n");
					String note_buffer = br.readLine(); 
					System.out.println("\n#######################################");
					int tb_resp,resp = 0;
					try{
						tb_resp = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS notes_table (`id` INTEGER PRIMARY KEY AUTOINCREMENT,note_text TEXT)");
						resp = stmt.executeUpdate("INSERT INTO notes_table (`note_text`) VALUES('"+note_buffer+"')");
						conn.close();												
					}
					catch(SQLException e){System.out.println(e);}
					String result = resp > 0 ? "SAVED" : "ERROR IN SAVING";
					System.out.print(result);
					System.out.println("#######################################");
					break;
				}
				case "-d":{
					System.out.println("#######################################");
					System.out.print("ENTER NOTE ID : ");
					String temp_id = sc.next();
					try{
						if(stmt.executeUpdate("DELETE FROM notes_table WHERE id='"+temp_id+"'") > 0){
							System.out.println("\n#######################################");
							System.out.println("NOTE DELETED");
							System.out.println("#######################################");
						}
						conn.close();												
					}
					catch(SQLException e){System.out.println(e);}
					break;
				}
				case "-da":{
					try{
						stmt.executeUpdate("DROP TABLE IF EXISTS notes_table");
						System.out.println("\n#######################################");
						System.out.println("ALL NOTES DELETED");
						System.out.println("#######################################");
						conn.close();												
					}
					catch(SQLException e){System.out.println(e);}
					break;
				}				
				case "-l":{
					System.out.println("\n#######################################");
					System.out.println("ID\t\tNOTE");		
					System.out.println("#######################################\n");
					try{
						ResultSet rst = stmt.executeQuery("SELECT * FROM notes_table");
						while(rst.next()){
							System.out.println(rst.getString("id")+"\t\t"+rst.getString("note_text")+"\n");
						}
						conn.close();						
					}
					catch(SQLException e){System.out.println(e);}							
					break;
				}				
				default: {
					help();
					break;
				}
			}
		}
		else{
			help();
		}
	}
}
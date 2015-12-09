import com.jcraft.jsch.*;
import java.io.*;

public class Execute{
	public static void main(String[] args) {
	  
	   try{
	   	//instantiating jsch class for use
        JSch jsch = new JSch();
        
        // ssh credentials
        String host = "192.168.0.102";
        String user = "somnath";
        String passwd = "Pass@123";
        
        jsch.setConfig("StrictHostKeyChecking", "no");

        Session session = jsch.getSession(user, host);
        session.setPassword(passwd);
        session.connect();
        
        ChannelExec shell = (ChannelExec) session.openChannel("exec");
 
			//Gets an InputStream for this channel. All data arriving in as messages from the remote side can be read from this stream.
			InputStream in = shell.getInputStream();

  
            // execute the script command
			shell.setCommand("sh "+"hello.sh");

			// connect
			shell.connect();
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            //Read each line from the buffered reader and print it to the console
            while ((line = reader.readLine()) != null)
            {
            	System.out.println(line);
            }
            
			
			//retrieve the exit status of the remote command corresponding to this channel
         int exitStatus = shell.getExitStatus();

         //Safely disconnect channel and disconnect session. If not done then it may cause resource leak
         shell.disconnect();
         session.disconnect();

         if(exitStatus < 0){
            // System.out.println("Done, but exit status not set!");
         }
         else if(exitStatus > 0){
            // System.out.println("Done, but with error!");
         }
         else{
            // System.out.println("Done!");
         }


	   }catch(Exception e){
	     e.printStackTrace();
	   }
	  	  
	}
     
}